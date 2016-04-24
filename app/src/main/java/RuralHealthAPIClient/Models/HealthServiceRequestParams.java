package RuralHealthAPIClient.Models;

import java.net.URI;
import java.net.URL;

/**
 * Created by John on 3/04/2016.
 */
public class HealthServiceRequestParams {

    public String simpleQuery;
    private String endpoint;


    @Override
    public String toString()
    {
        String result = endpoint;

        if(simpleQuery != null)
            result += "simple-query" + "=" + simpleQuery;

        return result;
    }

    public String getSimpleQuery() {
        return simpleQuery;
    }

    public void setSimpleQuery(String simpleQuery) {
        this.simpleQuery = simpleQuery;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
