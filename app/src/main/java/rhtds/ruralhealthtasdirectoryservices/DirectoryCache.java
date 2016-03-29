package rhtds.ruralhealthtasdirectoryservices;

/**
 * Created by Andrew on 2/16/2016.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/** TODO
 *
 *When they have interwebs on startup I just dump everything and re add it to my SQLite db
 *
 *
 *
 *
 * In your android app
 * In your request headers
 * add Authorization
 * and the value should be  56c1aaae08d21700010001cddc36a73a12ac428b4aa7d0b5e668a015




    TODO: can make table with category id, name, description instead of saving as file. can then use the categories past in to query for the category id that contains something IN filters
 */
public class DirectoryCache
{
    public static final String DATABASE_NAME = "RHTDS";
    public static final int DATABASE_VERSION = 2;

    public static final String RELATIONS_TABLE = "Relations";
    public static final String RELATIONS_ID = "id";
    public static final String RELATIONS_CATEGORYID = "categoryId";
    public static final String RELATIONS_SERVICEID = "serviceId";
    private static final String RELATIONS_TABLE_CREATE = "CREATE TABLE "
            + RELATIONS_TABLE
            + " (" + RELATIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RELATIONS_CATEGORYID + " INTEGER NOT NULL, "
            + RELATIONS_SERVICEID + " INTEGER NOT NULL);";

    public static final String SERVICES_TABLE = "Services";
    public static final String SERVICES_ID = "id";
    public static final String SERVICES_TITLE = "title";
    public static final String SERVICES_DESCRIPTION = "description";
    public static final String SERVICES_PHONE = "phone";
    public static final String SERVICES_ALTPHONE = "altPhone";
    public static final String SERVICES_IMGURI = "imgURI";
    public static final String SERVICES_ADDRESSFULL = "address";//different to spec
    public static final String SERVICES_LONGITUDE = "longitude";//different to spec
    public static final String SERVICES_LATITUDE = "latitude";//different to spec
    public static final String SERVICES_WEBSITE = "website";
    public static final String SERVICES_EMAIL = "email";

    private static final String SERVICES_TABLE_CREATE = "CREATE TABLE "
            + SERVICES_TABLE
            + " (" + SERVICES_ID + " INTEGER PRIMARY KEY, "
            + SERVICES_TITLE + " TEXT NOT NULL, "
            + SERVICES_DESCRIPTION + " TEXT NOT NULL, "
            + SERVICES_PHONE + " TEXT NOT NULL, "
            + SERVICES_ALTPHONE + " TEXT NOT NULL, "
            + SERVICES_IMGURI + " TEXT NOT NULL, "
            + SERVICES_ADDRESSFULL + " TEXT NOT NULL, "
            + SERVICES_LONGITUDE + " REAL NOT NULL, " //real is an 8bit float
            + SERVICES_LATITUDE + " REAL NOT NULL, " //real is an 8bit float
            + SERVICES_WEBSITE + " TEXT NOT NULL, "
            + SERVICES_EMAIL + " TEXT NOT NULL);";

    private Helper helper;
    private SQLiteDatabase database;


    public DirectoryCache(Context context)
    {
        helper = new Helper(context);
        database = helper.getWritableDatabase();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void insert(String servicesJSON)
    {
        try
        {
            JSONArray services = new JSONArray(servicesJSON);

                try
                {
                    for (int i = 0; i < services.length(); i++)
                    {
                        JSONObject child = services.getJSONObject(i);

                        String title = child.getString("title");
                        int id = child.getInt("id");
                        String description = child.getString("description");
                        String phone = child.getString("phone");
                        String altPhone = child.getString("altPhone");
                        String imgURI = child.getString("imgURI");
                        String website = child.getString("website");
                        String email = child.getString("email");


                        JSONObject addressJSON = child.getJSONObject("address");
                        String address = addressJSON.getString("as_string");

                        JSONObject coordinatesJSON = addressJSON.getJSONObject("coordinates");
                        double latitude = coordinatesJSON.getDouble("latitude");
                        double longitude = coordinatesJSON.getDouble("longitude");

                        //TODO: make this into one massive string and insert all values at once, rather than one at a time
                        JSONArray categoriesArray = child.getJSONArray("categories");
                        for (int j = 0; j < categoriesArray.length(); j++)
                        {
                            JSONObject category = services.getJSONObject(i);
                            int categoryId = child.getInt("id");

                            ContentValues values = new ContentValues();
                            values.put(RELATIONS_CATEGORYID, categoryId); //TODO: check log.d for debug of this, lots of same categoryId so something isnt being inserted properly
                            values.put(RELATIONS_SERVICEID, id);

                            database.insert(RELATIONS_TABLE, null, values);


                            //String categoryName = child.getString("name");
                            //String categoryDescription = child.getString("description");
                            //all info for category exists, so could make a category table and insert here

                            //TODO Insert
                        }

                        ContentValues values = new ContentValues();


                        values.put(SERVICES_TITLE, title);
                        values.put(SERVICES_ID, id);
                        values.put(SERVICES_DESCRIPTION, description);
                        values.put(SERVICES_PHONE, phone);

                        values.put(SERVICES_ALTPHONE, altPhone);
                        values.put(SERVICES_IMGURI, imgURI);
                        values.put(SERVICES_WEBSITE, website);
                        values.put(SERVICES_EMAIL, email);
                        values.put(SERVICES_ADDRESSFULL, address);
                        values.put(SERVICES_LONGITUDE, longitude);
                        values.put(SERVICES_LATITUDE, latitude);

                        database.insert(SERVICES_TABLE, null, values);
                        //Log.v("RHTDS", "Successfully wrote service with id: " + id);
                    }
                    Log.v("RHTDS", "Successfully wrote all services to database");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        database.close();
    }

    public void reset()
    {
        database.delete(SERVICES_TABLE, null, null);
        database.delete(RELATIONS_TABLE, null, null);
        //database.close();
    }

    public void close() {
        database.close();
    }

    //http://stackoverflow.com/questions/1756296/android-writing-logs-to-text-file
    //yep this writes a file instead of db
    //if runOnce == false, then do it. set runOnce = true
    public static void writeCategories(String categoriesJSON, String servicesJSON, Context context)
    {
        try
        {
            //write the categories to a db for the moment
            FileOutputStream  outputStream = context.openFileOutput("categories.json", Context.MODE_PRIVATE);
            outputStream.write(categoriesJSON.getBytes());
            outputStream.close();
            Log.v("RHTDS", "Wrote categories to file");


            DirectoryCache db = new DirectoryCache(context);
            db.reset();//delete everything thats in it
            db.insert(servicesJSON);//write the services to a db
            db.close();

        }
        catch (Exception e) {
            e.printStackTrace();
            Log.v("RHTDS", "Unable to create/write to file");
        }


    }


    //TODO: currently this just works for a single categoryId, needs to be updated to implement a search
    public static JSONArray requestServices(ArrayList<String> categories, double longitude,
                                            double latitude, boolean useLocation, String address,
                                            String organisationName, int categoryId,
                                            Context context)//TODO: fill in this, and take in proper parameters
    {
        DirectoryCache db = new DirectoryCache(context);
        //SELECT * FROM services_table WHERE services_id IN (SELECT services_id FROM relations_table WHERE category_id = categoryId)
        String query = "SELECT * FROM " + SERVICES_TABLE + " WHERE "; //start of query
        query += SERVICES_ID + " IN (SELECT " + RELATIONS_SERVICEID + " FROM " + RELATIONS_TABLE + " WHERE " + RELATIONS_CATEGORYID + "=" + categoryId + ")";



        //test query here that overrides the others
        //query = "SELECT " + RELATIONS_SERVICEID + " FROM " + RELATIONS_TABLE + " WHERE " + RELATIONS_CATEGORYID + "=" + categoryId;
            //query = "SELECT * FROM " + RELATIONS_TABLE;
        Log.d("RHTDS", query);

        Cursor cursor = db.getDatabase().rawQuery(query, null);

        Log.d("RHTDS", "cursor is: " + Integer.toString(cursor.getCount()));

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {
            //String activity = cursor.getString(cursor.getColumnIndex(FitnessDB.KEY_ACTIVITY_TYPE));
            //float duration = c.getFloat(c.getColumnIndex(FitnessDB.KEY_DURATION));

            //Log.d("RHTDS", "categoryId = " + cursor.getInt(cursor.getColumnIndex(RELATIONS_SERVICEID)));

            Log.v("RHTDS", cursor.getString(cursor.getColumnIndex(SERVICES_TITLE)));

            cursor.moveToNext();
        }





        //start of stuff for search
        /*if(useLocation) {
            query += " longitude=" + longitude;
            query += " AND latitude=" + latitude;
            query += " AND ";
        }

        if(!address.equals("") && !address.equals("Current Location")) {
            query += " address=" + address + " AND ";
        }

        if(categories.size() > 0) //specific category query
        {

        }
        else //all categories
        {

        }*/





        //run the query here - x in (select y from z)
        //build json string same as from server
        return new JSONArray();

    }

    public static String requestAllCategories(Context context)
    {
        Log.v("rhtds", "reading categories from local cache");

        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = context.openFileInput("categories.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public Cursor selectAllServices()
    {

        Cursor cursor = database.rawQuery("SELECT * FROM " + SERVICES_TABLE, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor; // iterate to get each value.
    }


    public static JSONArray requestCategoriesAsJSONArray() {

        //Build a JSON array, but add certain parts, as address is an array in web copy, so add that that contains just full address, which is the only part of address thats used anyway
        return new JSONArray();//TODO
    }



    public class Helper extends SQLiteOpenHelper {

        public Helper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(SERVICES_TABLE_CREATE);
            database.execSQL(RELATIONS_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
            database.execSQL("DROP TABLE IF EXISTS " + SERVICES_TABLE);
            database.execSQL("DROP TABLE IF EXISTS " + RELATIONS_TABLE);
            onCreate(database);
        }
    }
}
