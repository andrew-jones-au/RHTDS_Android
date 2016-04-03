package RuralHealthAPIClient;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.net.URI;

/**
 * Created by John on 3/04/2016.
 */
public class HealthServiceDBOperationTask extends AsyncTask<HealthServiceDBOperationParams, Integer, HealthServiceDBOperationResult>
{
    @Override
    protected void onPostExecute(HealthServiceDBOperationResult result) {
        super.onPostExecute(result);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected HealthServiceDBOperationResult doInBackground(HealthServiceDBOperationParams... params) {
        HealthServiceDBOperationResult result;
        Cursor cursor;

        result = new HealthServiceDBOperationResult();

        for(int i = 0; i < params.length; i++)
        {
            String query = params[i].query;
            HealthServiceDBHelper db = params[i].db;

            cursor = db.executeQuery(query);

            result.setCursor(cursor);
        }

        return result;
    }
}
