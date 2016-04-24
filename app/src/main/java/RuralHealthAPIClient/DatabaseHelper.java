package RuralHealthAPIClient;

import android.content.Context;
import java.sql.SQLException;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import RuralHealthAPIClient.Models.Address;
import RuralHealthAPIClient.Models.Category;
import RuralHealthAPIClient.Models.Coordinate;
import RuralHealthAPIClient.Models.HealthService;
import rhtds.ruralhealthtasdirectoryservices.R;

/**
 * Created by John on 23/04/2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
   // ConnectionSource connectionSource;
    private static final String TAG = DatabaseHelper.class.getName();

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "ruralhealthapi.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 4;

    private Context context;

    private Dao<HealthService, Integer> healthServiceDao;
    private Dao<Address, Integer> addressDao;
    private Dao<Category, Integer> categoryDao;
    private Dao<Coordinate, Integer> coordinateDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);

        this.context = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");

            TableUtils.dropTable(connectionSource, Coordinate.class, true);
            TableUtils.dropTable(connectionSource, Address.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, HealthService.class, true);

            // after we drop the old databases, we create the new ones
            onCreate(sqLiteDatabase, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Coordinate.class);
            TableUtils.createTable(connectionSource, Address.class);
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, HealthService.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<HealthService, Integer> getHealthServiceDao() throws SQLException{
        if(healthServiceDao == null)
        {
            healthServiceDao = getDao(HealthService.class);
        }
        return healthServiceDao;
    }

    public Dao<Address, Integer> getAddressDao() throws SQLException {
        if(addressDao == null)
        {
            addressDao = getDao(Address.class);
        }
        return addressDao;
    }

    public Dao<Category, Integer> getCategoryDao()  throws SQLException{
        if(categoryDao == null)
        {
            categoryDao = getDao(Category.class);
        }
        return categoryDao;
    }

    public Dao<Coordinate, Integer> getCoordinateDao()  throws SQLException{
        if(coordinateDao == null)
        {
            coordinateDao = getDao(Coordinate.class);
        }
        return coordinateDao;
    }


}
