package RuralHealthAPIClient;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import RuralHealthAPIClient.Exceptions.ConfigurationManagerException;
import RuralHealthAPIClient.Exceptions.RuralHealthAPIClientException;
import RuralHealthAPIClient.Models.HealthService;
import RuralHealthAPIClient.*;

/**
 * Created by John on 30/03/2016.
 */
public class Client
{
    Context context;
    ConfigurationManager configurationManager;
    HealthServiceDBHelper healthServiceDBHelper;

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

    public HealthService[] getAllHealthServices()
    {
        HealthService[] healthServices;

        healthServices = healthServiceDBHelper.getAllHealthServices();


        return  healthServices;
    }

}
