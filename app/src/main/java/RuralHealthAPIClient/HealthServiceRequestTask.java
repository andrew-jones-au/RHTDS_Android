package RuralHealthAPIClient;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.MalformedURLException;
import java.util.Observable;

import javax.net.ssl.HttpsURLConnection;

import RuralHealthAPIClient.Exceptions.RequestException;

/**
 * Created by John on 30/03/2016.
 */
public class HealthServiceRequestTask extends AsyncTask<HealthServiceRequestParams, Integer, HealthServiceRequestResult>
{
    private final static String TAG = "RequestTask";

    @Override
    protected HealthServiceRequestResult doInBackground(HealthServiceRequestParams... params) {

        HttpsURLConnection httpsURLConnection = null;
        HealthServiceRequestResult result = new HealthServiceRequestResult();
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringWriter out;

        try
        {
            for(int i = 0; i < params.length; i++)
            {
                httpsURLConnection = (HttpsURLConnection)params[i].getURI().toURL().openConnection();

                if(httpsURLConnection.getResponseCode() != 200)
                {
                    Log.e(TAG, "API Request response code was: " + httpsURLConnection.getResponseCode());
                    return null;
                }

                inputStream = new BufferedInputStream(httpsURLConnection.getInputStream());
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                out = new StringWriter();
                char[] datachunk = new char[1024 * 8];
                int charsRead = 0;

                while(-1 != (charsRead = bufferedReader.read(datachunk)))
                {
                    out.write(datachunk, 0, charsRead);
                }

                result.setJsonArray(new JSONArray(out.toString()));

                inputStream.close();
                bufferedReader.close();
            }
        }
        catch(MalformedURLException e)
        {
            Log.e(TAG, "URL Was malformed: " + e.getMessage());
            return null;
        }
        catch(IOException e)
        {
            Log.e(TAG, "Could not open connection: " + e.getMessage());
            return null;
        }
        catch(JSONException e)
        {
            Log.e(TAG, "Error parsing response JSON data " + e.getMessage());
            return null;
        }
        finally {
            if(httpsURLConnection != null)
            {
                httpsURLConnection.disconnect();
            }
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(HealthServiceRequestResult result) {
        super.onPostExecute(result);

        Log.d(TAG, result.getJsonArray().toString());
    }
}
