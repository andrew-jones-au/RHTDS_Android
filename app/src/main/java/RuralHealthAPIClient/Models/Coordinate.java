package RuralHealthAPIClient.Models;

import java.util.HashMap;

import RuralHealthAPIClient.HealthServiceDBContract;
import RuralHealthAPIClient.Interfaces.HealthServiceModelInterface;

/**
 * Created by John on 2/04/2016.
 */
public class Coordinate implements HealthServiceModelInterface
{
    int id;
    String longitude;
    String latitude;

    public HashMap<String, String> toHashMap()
    {
        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put(HealthServiceDBContract.Coordinate.COLUMN_NAME_LONGITUDE, longitude);
        hashMap.put(HealthServiceDBContract.Coordinate.COLUMN_NAME_LATITUDE, latitude);

        return hashMap;
    }
}
