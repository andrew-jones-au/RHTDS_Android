package RuralHealthAPIClient;


import android.content.ContentValues;

import java.util.HashMap;

import RuralHealthAPIClient.Interfaces.TaskParamInterface;

/**
 * Created by John on 3/04/2016.
 */
public class HealthServiceDBOperationParams implements TaskParamInterface
{
    HealthServiceDBHelper db;
    String query;
    Object data;
}
