package RuralHealthAPIClient.Models;

import java.util.HashMap;

import RuralHealthAPIClient.HealthServiceDBContract;
import RuralHealthAPIClient.Interfaces.HealthServiceModelInterface;

/**
 * Created by John on 2/04/2016.
 */
public class Category implements HealthServiceModelInterface
{
    int id;
    String name;
    String description;

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put(HealthServiceDBContract.Category.COLUMN_NAME_NAME, name);
        hashMap.put(HealthServiceDBContract.Category.COLUMN_NAME_DESCRIPTION, description);

        return hashMap;
    }
}
