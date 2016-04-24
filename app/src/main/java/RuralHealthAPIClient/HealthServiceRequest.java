package RuralHealthAPIClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.j256.ormlite.misc.IOUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import RuralHealthAPIClient.Models.HealthServiceRequestParams;

/**
 * Created by John on 23/04/2016.
 */
public class HealthServiceRequest extends AsyncTask<HealthServiceRequestParams, Integer, String> {

    private static final String TAG = HealthServiceRequest.class.getName();

    @Override
    protected String doInBackground(HealthServiceRequestParams... params) {
        HealthServiceRequestParams healthServiceParam = params[0];

        URLConnection connection;
        String data;

        try
        {
            Log.i(TAG, "Making GET Request to: " + healthServiceParam.toString());
            URL url = new URL(healthServiceParam.toString());
            connection = url.openConnection();

            data = org.apache.commons.io.IOUtils.toString(connection.getInputStream());

            Log.d(TAG, "Received the following data:");
            Log.d(TAG, data);

            return data;
        }
        catch(MalformedURLException e)
        {
            Log.e(TAG, e.toString());
        }
        catch (IOException e)
        {
            Log.e(TAG, e.toString());
        }

        return null;
    }

}
