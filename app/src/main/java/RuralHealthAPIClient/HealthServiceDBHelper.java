package RuralHealthAPIClient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import RuralHealthAPIClient.Models.Category;
import RuralHealthAPIClient.Models.HealthService;

/**
 * Created by John on 2/04/2016.
 */
public class HealthServiceDBHelper extends SQLiteOpenHelper
{

    public static final String TAG = "HealthServiceDBHelper";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RuralHealthAPIClient.db";

    public HealthServiceDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d(TAG, "HealthServiceDBHelper Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        createAllTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        dropAllTables(db);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }

    void createAllTables(SQLiteDatabase db)
    {
        new HealthServiceDBOperationTask(); //TODO
        db.execSQL(HealthServiceDBContract.SQL_CREATE_TABLE_HEALTH_SERVICE);
        db.execSQL(HealthServiceDBContract.SQL_CREATE_TABLE_ADDRESS);
        db.execSQL(HealthServiceDBContract.SQL_CREATE_TABLE_CATEGORY);
        db.execSQL(HealthServiceDBContract.SQL_CREATE_TABLE_COORDINATE);
        db.execSQL(HealthServiceDBContract.SQL_CREATE_TABLE_CATEGORY_HEALTH_SERVICE);
    }

    void dropAllTables(SQLiteDatabase db)
    {
        db.execSQL(HealthServiceDBContract.SQL_DELETE_TABLE_HEALTH_SERVICE);
        db.execSQL(HealthServiceDBContract.SQL_DELETE_TABLE_ADDRESS);
        db.execSQL(HealthServiceDBContract.SQL_DELETE_TABLE_CATEGORY);
        db.execSQL(HealthServiceDBContract.SQL_DELETE_TABLE_COORDINATE);
        db.execSQL(HealthServiceDBContract.SQL_DELETE_TABLE_CATEGORY_HEALTH_SERVICE);
    }

    public long insertRow(HealthServiceDBHelper dbHelper, String table, HashMap<String, String> valuesHashMap)
    {
        ContentValues contentValues = new ContentValues();
        long rowId = -1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        for(Map.Entry<String, String> entry: valuesHashMap.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();

            contentValues.put(key, value);
        }

        rowId = db.insert(table, null, contentValues);

        return rowId;
    }

    public Cursor executeQuery(String query)
    {
        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(query, null);
    }

    public static HealthService healthServiceFromRow(Cursor cursor)
    {
        HealthService healthService = new HealthService();

        healthService.setId(cursor.getInt(cursor.getColumnIndexOrThrow(HealthServiceDBContract.HealthService.COLUMN_NAME_ID)));
        healthService.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(HealthServiceDBContract.HealthService.COLUMN_NAME_PHONE)));
        healthService.setAltPhone(cursor.getString(cursor.getColumnIndexOrThrow(HealthServiceDBContract.HealthService.COLUMN_NAME_ALT_PHONE)));
        healthService.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(HealthServiceDBContract.HealthService.COLUMN_NAME_DESCRIPTION)));
        healthService.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(HealthServiceDBContract.HealthService.COLUMN_NAME_TITLE)));
        healthService.setImgURI(cursor.getString(cursor.getColumnIndexOrThrow(HealthServiceDBContract.HealthService.COLUMN_NAME_IMGURI)));
        healthService.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(HealthServiceDBContract.HealthService.COLUMN_NAME_EMAIL)));
        healthService.setWebsite(cursor.getString(cursor.getColumnIndexOrThrow(HealthServiceDBContract.HealthService.COLUMN_NAME_WEBSITE)));

        return healthService;
    }

    public void insertHealthService(SQLiteDatabase db, HealthService healthService)
    {
        HealthServiceDBOperationParams params = new HealthServiceDBOperationParams();
        params.db = this;
        params.data = healthService;

        HealthServiceDBOperationTask task = new HealthServiceDBOperationTask()
        {
            @Override
            protected HealthServiceDBOperationResult doInBackground(HealthServiceDBOperationParams... params) {
                HealthServiceDBOperationResult result = new HealthServiceDBOperationResult();
                long healthServiceRowId, addressRowId, coordinateRowId;
                HealthService healthService = (HealthService)params[0].data;
                HealthServiceDBHelper db = params[0].db;
                HashMap<String, String> coordHashMap, addressHashMap, healthServiceHashMap;

                coordHashMap = healthService.getAddress().getCoordinate().toHashMap();
                addressHashMap = healthService.getAddress().toHashMap();
                healthServiceHashMap = healthService.toHashMap();

                coordinateRowId = db.insertRow(db, HealthServiceDBContract.Coordinate.TABLE_NAME, coordHashMap);

                addressHashMap.put(HealthServiceDBContract.Address.COLUMN_NAME_COORDINATE, String.valueOf(coordinateRowId));
                addressRowId = db.insertRow(db, HealthServiceDBContract.Address.TABLE_NAME, addressHashMap);

                healthServiceHashMap.put(HealthServiceDBContract.HealthService.COLUMN_NAME_ADDRESS, String.valueOf(addressRowId));
                healthServiceRowId = db.insertRow(db, HealthServiceDBContract.HealthService.TABLE_NAME, healthServiceHashMap);

                for(int i = 0; i < healthService.getCategories().size(); i++)
                {
                    Category category;
                    HashMap<String, String> categoryHashMap, categoryHealthServiceHashMap;
                    long categoryRowId;

                    category = healthService.getCategories().get(i);
                    categoryHashMap = category.toHashMap();
                    categoryRowId = db.insertRow(db, HealthServiceDBContract.Category.TABLE_NAME, categoryHashMap);

                    categoryHealthServiceHashMap = new HashMap<String, String>();
                    categoryHealthServiceHashMap.put(HealthServiceDBContract.CategoryHealthService.COLUMN_NAME_CATEGORY_ID, String.valueOf(category));
                    categoryHealthServiceHashMap.put(HealthServiceDBContract.CategoryHealthService.COLUMN_NAME_HEALTH_SERVICE_ID, String.valueOf(healthServiceRowId));

                    db.insertRow(db, HealthServiceDBContract.CategoryHealthService.TABLE_NAME, categoryHealthServiceHashMap);
                }



                return result;
            }
        };

        task.execute(params);
    }


}
