package RuralHealthAPIClient;

import android.content.Context;
import android.util.Log;

import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import RuralHealthAPIClient.Exceptions.ConfigurationManagerException;
import rhtds.ruralhealthtasdirectoryservices.R;

/**
 * Created by John on 30/03/2016.
 */
public class ConfigurationManager
{
    private static final String TAG = "ConfigurationManager";
    private static final String CONFIG_FILE_PATH = "raw/config.xml";

    private Config config;
    private Context context;

    public ConfigurationManager(Context context)
    {
        this.context = context;

        try
        {
            updateConfig();
        }
        catch(IOException e)
        {
            Log.e(TAG, e.toString());
        }
        catch(ConfigurationManagerException e)
        {
            Log.e(TAG, e.toString());
        }
    }

    public void updateConfig() throws ConfigurationManagerException, IOException
    {
        Serializer serializer = new Persister();
        File file;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        Log.d(TAG, "About to start loading config file");

        try
        {
            file = new File(context.getFilesDir(),"tmp");
            inputStream = context.getResources().openRawResource(R.raw.config);
            outputStream = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] data = new byte[2048];

            while((bytesRead=inputStream.read(data)) > -1)
            {
                outputStream.write(data, 0, bytesRead);
            }

            config = serializer.read(Config.class, file);
        }catch(Exception e)
        {
            throw new ConfigurationManagerException("Could not open or parse configuration", e);
        }
        finally {
            if(outputStream != null)
            {
                outputStream.close();
            }
        }

        Log.d(TAG, "Loaded config file");
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
