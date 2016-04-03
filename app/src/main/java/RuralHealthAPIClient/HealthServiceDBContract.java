package RuralHealthAPIClient;

import android.provider.BaseColumns;

/**
 * Created by John on 2/04/2016.
 */
public class HealthServiceDBContract {
    public HealthServiceDBContract(){}

    static final String TEXT_TYPE = " TEXT";
    static final String COMMA_SEP = ", ";

    public static final String SQL_CREATE_TABLE_HEALTH_SERVICE =
            "CREATE TABLE " + HealthService.TABLE_NAME + " (" +
            HealthService._ID + " INTEGER PRIMARY KEY," +
            HealthService.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
            HealthService.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            HealthService.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
            HealthService.COLUMN_NAME_PHONE + TEXT_TYPE + COMMA_SEP +
            HealthService.COLUMN_NAME_ALT_PHONE + TEXT_TYPE + COMMA_SEP +
            HealthService.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
            HealthService.COLUMN_NAME_WEBSITE + TEXT_TYPE + COMMA_SEP +
            HealthService.COLUMN_NAME_IMGURI + TEXT_TYPE + COMMA_SEP +
            HealthService.COLUMN_NAME_ADDRESS + TEXT_TYPE +  " )";

    public static final String SQL_CREATE_TABLE_ADDRESS =
            "CREATE TABLE " + Address.TABLE_NAME + " (" +
            Address._ID + " INTEGER PRIMARY KEY," +
            Address.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
            Address.COLUMN_NAME_UNIT_NUM + TEXT_TYPE + COMMA_SEP +
            Address.COLUMN_NAME_STREET_NUM + TEXT_TYPE + COMMA_SEP +
            Address.COLUMN_NAME_STREET_NAME + TEXT_TYPE + COMMA_SEP +
            Address.COLUMN_NAME_TOWN_SUBURB_CITY + TEXT_TYPE + COMMA_SEP +
            Address.COLUMN_NAME_STATE + TEXT_TYPE + COMMA_SEP +
            Address.COLUMN_NAME_COUNTRY + TEXT_TYPE + COMMA_SEP +
            Address.COLUMN_NAME_POSTCODE + TEXT_TYPE + COMMA_SEP +
            Address.COLUMN_NAME_COORDINATE + TEXT_TYPE +  " )";

    public static final String SQL_CREATE_TABLE_COORDINATE =
            "CREATE TABLE " + Coordinate.TABLE_NAME + " (" +
            Coordinate._ID + " INTEGER PRIMARY KEY," +
            Coordinate.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
            Coordinate.COLUMN_NAME_LONGITUDE + TEXT_TYPE + COMMA_SEP +
            Coordinate.COLUMN_NAME_LATITUDE + TEXT_TYPE +  " )";

    public static final String SQL_CREATE_TABLE_CATEGORY =
            "CREATE TABLE " + Category.TABLE_NAME + " (" +
            Category._ID + " INTEGER PRIMARY KEY," +
            Category.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
            Category.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
            Category.COLUMN_NAME_DESCRIPTION + TEXT_TYPE +  " )";

    public static final String SQL_CREATE_TABLE_CATEGORY_HEALTH_SERVICE =
            "CREATE TABLE " + CategoryHealthService.TABLE_NAME + " (" +
            CategoryHealthService._ID + " INTEGER PRIMARY KEY," +
            CategoryHealthService.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
            CategoryHealthService.COLUMN_NAME_CATEGORY_ID + TEXT_TYPE + COMMA_SEP +
            CategoryHealthService.COLUMN_NAME_HEALTH_SERVICE_ID + TEXT_TYPE +  " )";

    public static final String SQL_DELETE_TABLE_HEALTH_SERVICE =
            "DROP TABLE IF EXISTS " + HealthService.TABLE_NAME;

    public static final String SQL_DELETE_TABLE_ADDRESS =
            "DROP TABLE IF EXISTS " + Address.TABLE_NAME;

    public static final String SQL_DELETE_TABLE_CATEGORY =
            "DROP TABLE IF EXISTS " + Category.TABLE_NAME;

    public static final String SQL_DELETE_TABLE_COORDINATE =
            "DROP TABLE IF EXISTS " + Coordinate.TABLE_NAME;

    public static final String SQL_DELETE_TABLE_CATEGORY_HEALTH_SERVICE =
            "DROP TABLE IF EXISTS " + CategoryHealthService.TABLE_NAME;


    public static abstract class HealthService implements BaseColumns
    {
        public static final String TABLE_NAME = "healthservice";

        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_WEBSITE = "website";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_IMGURI = "imgURI";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_ALT_PHONE = "altPhone";
        public static final String COLUMN_NAME_ADDRESS = "address";
    }

    public static abstract class Address implements BaseColumns
    {
        public static final String TABLE_NAME = "address";

        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_UNIT_NUM = "unitNum";
        public static final String COLUMN_NAME_STREET_NUM = "streetNum";
        public static final String COLUMN_NAME_STREET_NAME = "streetName";
        public static final String COLUMN_NAME_TOWN_SUBURB_CITY = "townSuburbCity";
        public static final String COLUMN_NAME_POSTCODE = "postcode";
        public static final String COLUMN_NAME_COUNTRY = "country";
        public static final String COLUMN_NAME_STATE = "state";
        public static final String COLUMN_NAME_COORDINATE = "coordinate";
    }

    public static abstract class Coordinate implements BaseColumns
    {
        public static final String TABLE_NAME = "coordinate";

        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
    }

    public static abstract class Category implements BaseColumns
    {
        public static final String TABLE_NAME = "category";

        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }

    public static abstract class CategoryHealthService implements BaseColumns
    {
        public static final String TABLE_NAME = "category_healthService";

        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_HEALTH_SERVICE_ID = "healthService_id";
        public static final String COLUMN_NAME_CATEGORY_ID = "category_id";
    }
}
