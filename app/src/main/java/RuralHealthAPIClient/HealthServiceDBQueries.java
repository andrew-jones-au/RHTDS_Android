package RuralHealthAPIClient;

import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import RuralHealthAPIClient.Models.HealthService;

/**
 * Created by John on 3/04/2016.
 */
public class HealthServiceDBQueries
{
    private static final String TAG = "DBQueries";

    public static String getAllHealthServicesQuery()
    {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        String selection = "";
        String limit = "";
        String having = "";
        String groupBy = "";
        String sortOrder = HealthServiceDBContract.HealthService.COLUMN_NAME_TITLE + " DESC";
        String query;

        String[] projection = {
                HealthServiceDBContract.HealthService._ID,
                HealthServiceDBContract.HealthService.COLUMN_NAME_ID,
                HealthServiceDBContract.HealthService.COLUMN_NAME_TITLE,
                HealthServiceDBContract.HealthService.COLUMN_NAME_DESCRIPTION,
                HealthServiceDBContract.HealthService.COLUMN_NAME_ADDRESS,
                HealthServiceDBContract.HealthService.COLUMN_NAME_ALT_PHONE,
                HealthServiceDBContract.HealthService.COLUMN_NAME_IMGURI,
                HealthServiceDBContract.HealthService.COLUMN_NAME_PHONE,
                HealthServiceDBContract.HealthService.COLUMN_NAME_ALT_PHONE,
                HealthServiceDBContract.HealthService.COLUMN_NAME_EMAIL,
                HealthServiceDBContract.HealthService.COLUMN_NAME_WEBSITE
        };

        sqLiteQueryBuilder.setTables(HealthServiceDBContract.HealthService.TABLE_NAME);

        query = sqLiteQueryBuilder.buildQuery(
                projection,
                selection,
                groupBy,
                having,
                sortOrder,
                limit);

        Log.d(TAG, "Built query: " + query);

        return query;
    }
}
