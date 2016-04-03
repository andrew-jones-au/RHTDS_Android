package RuralHealthAPIClient;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;

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


}
