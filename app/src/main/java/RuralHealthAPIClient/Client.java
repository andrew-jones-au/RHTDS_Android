package RuralHealthAPIClient;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import RuralHealthAPIClient.Exceptions.ConfigurationManagerException;
import RuralHealthAPIClient.Exceptions.RuralHealthAPIClientException;
import RuralHealthAPIClient.Interfaces.ClientInterface;
import RuralHealthAPIClient.Models.HealthService;
import RuralHealthAPIClient.*;

/**
 * Created by John on 30/03/2016.
 */
public class Client implements ClientInterface
{
    Context context;
    ConfigurationManager configurationManager;
    HealthServiceDBHelper healthServiceDBHelper;

    private final static String TAG = "RuralHealthAPIClient";

    public Client(Context context) throws RuralHealthAPIClientException
    {
        this.context = context;
        configurationManager = new ConfigurationManager(context);
        healthServiceDBHelper = new HealthServiceDBHelper(context);

        try
        {
            configurationManager.updateConfig();
        }
        catch(ConfigurationManagerException e)
        {
            throw new RuralHealthAPIClientException("Could not load configuration", e);
        }
        catch(IOException e)
        {
            throw new RuralHealthAPIClientException("Could not load configuration", e);
        }
    }

    public void getAllHealthServices()
    {
        HealthService[] healthServices;
        HealthServiceDBOperationParams params = new HealthServiceDBOperationParams();
        params.db = healthServiceDBHelper;
        params.query = HealthServiceDBQueries.getAllHealthServicesQuery();

        HealthServiceDBOperationTask dbOperationTask = new HealthServiceDBOperationTask(){
            @Override
            protected void onPostExecute(HealthServiceDBOperationResult result)
            {
                super.onPostExecute(result);
                healthServiceDBOperationResponse(result);
            }
        };

        dbOperationTask.execute(params);
    }

    public void updateDB()
    {
        HealthService[] healthService;
        HealthServiceRequestTask healthServiceRequestTask = new HealthServiceRequestTask(){

                @Override
                protected void onPostExecute(HealthServiceRequestResult result)
                {
                    super.onPostExecute(result);
                    apiRequestResponseHandler(result);
                }
        };

        HealthServiceRequestParams healthServiceRequestParams = new HealthServiceRequestParams();

        try
        {
            healthServiceRequestParams.setURI(new URI(configurationManager.getConfig().getCategoryEndpoint()));

            healthServiceRequestTask.execute(healthServiceRequestParams);
        }catch(URISyntaxException e)
        {
            Log.e(TAG, "Config URI syntax is incorrect");
        }

    }

    private void apiRequestResponseHandler(HealthServiceRequestResult result)
    {

    }

    private void healthServiceDBOperationResponse(HealthServiceDBOperationResult result)
    {
        HealthService[] healthServices = new HealthService[result.getCursor().getCount()];

        Log.d(TAG, "Completed DB query - getAllHealthServices");

        if(healthServices.length == 0)
        {
            Log.d(TAG, "getAllHealthServices DB query returned 0 results, trying to request more data from web service");
            updateDB();
        }
        else
        {
            Log.d(TAG, "getAllHealthServices DB query returned " + healthServices.length + " results");

            int i = 0;
            while(result.getCursor().moveToNext())
            {
                healthServices[i] = HealthServiceDBHelper.healthServiceFromRow(result.getCursor());
            }

        }

    }

    @Override
    public void handleResult(Object result)
    {
        Log.d(TAG, "Handling result");
    }

}
