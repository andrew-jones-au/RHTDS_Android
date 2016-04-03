package RuralHealthAPIClient;

import org.json.JSONArray;

import RuralHealthAPIClient.Interfaces.TaskResultInterface;

/**
 * Created by John on 3/04/2016.
 */
public class HealthServiceRequestResult implements TaskResultInterface
{
    private JSONArray jsonArray;

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
}
