package RuralHealthAPIClient;

import org.json.JSONObject;

import java.util.Observable;

/**
 * Created by John on 30/03/2016.
 */
public class HealthServiceRequest extends Observable implements Runnable
{
    public JSONObject data;
    public String uriScheme;
    public String host;
    public int port;
    public String resource;

    public void run()
    {

    }
}
