package RuralHealthAPIClient;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import RuralHealthAPIClient.Interfaces.APIInterface;
import RuralHealthAPIClient.Models.Address;
import RuralHealthAPIClient.Models.Category;
import RuralHealthAPIClient.Models.Coordinate;
import RuralHealthAPIClient.Models.HealthService;
import RuralHealthAPIClient.Models.HealthServiceRequestParams;

/**
 * Created by John on 23/04/2016.
 */
public class ApiHelper implements APIInterface {

    DatabaseHelper dbHelper;
    private static final String TAG = ApiHelper.class.getName();
    private Config config;
    private Context context;

    public ApiHelper(Context context)
    {
        this.context = context;
        this.config = new ConfigurationManager(context).getConfig();

        dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);

    }

    @Override
    public void getHealthServices() {
        List<HealthService> healthServices;
        final Type healthServiceListType = new TypeToken<List<HealthService>>(){}.getType();
        HealthServiceRequestParams params = new HealthServiceRequestParams();
        params.setEndpoint(config.getHealthServiceEndpoint());

        HealthServiceRequest request = new HealthServiceRequest(){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Object result = processResponse(healthServiceListType, s);
                getHealthServicesResponse((List<HealthService>)result);
            }
        };
        request.execute(params);
    }

    @Override
    public void getHealthServices(HealthServiceRequestParams params) {
        List<HealthService> healthServices;
        final Type healthServiceListType = new TypeToken<List<HealthService>>(){}.getType();
        params.setEndpoint(config.getHealthServiceEndpoint());

        HealthServiceRequest request = new HealthServiceRequest(){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Object result = processResponse(healthServiceListType, s);
                getHealthServicesResponse((List<HealthService>)result);
            }
        };
        request.execute(params);
    }

    public void getHealthServicesResponse(List<HealthService> healthServices)
    {
        Dao<HealthService, Integer> healthServiceDao;
        Dao<Coordinate, Integer> coordinateDao;
        Dao<Address, Integer> addressDao;

        try
        {
            healthServiceDao = dbHelper.getHealthServiceDao();
            coordinateDao = dbHelper.getCoordinateDao();
            addressDao = dbHelper.getAddressDao();
            HealthService healthServiceDbObject;
            Address addressDbObject;
            Coordinate coordinateDbObject;

            for(HealthService healthService: healthServices)
            {
                List<HealthService> matchingServices = healthServiceDao.queryBuilder().where().like("title", healthService.getTitle().replace("'", "''")).query();

                if(matchingServices.size() > 0)
                {
                    healthServiceDbObject = matchingServices.get(0);
                }
                else
                {
                    healthServiceDao.createOrUpdate(healthService);
                    healthServiceDbObject = healthServiceDao.queryForId(healthService.get_id());
                }

                for(Category category: healthService.getCategories())
                {
                    healthServiceDbObject.getCategoryForeignCollection().add(category);
                }

                coordinateDao.createOrUpdate(healthService.getAddress().getCoordinates());
                coordinateDbObject = coordinateDao.queryForId(healthService.getAddress().getCoordinates().get_id());

                addressDao.createOrUpdate(healthService.getAddress());
                int addressId = addressDao.extractId(healthService.getAddress());
                addressDbObject = addressDao.queryForId(addressId);

                addressDbObject.setCoordinates(coordinateDbObject);
                addressDao.update(addressDbObject);

                healthServiceDbObject.setAddress(addressDbObject);
                healthServiceDao.update(healthServiceDbObject);

                Log.d(TAG, healthService.toString());
            }
        }
        catch (SQLException e) {
            Log.e(TAG, e.toString());
        }

    }

    @Override
    public void getHealthService(int id) {

    }

    @Override
    public List<Category> getCategories() {
        return null;
    }

    @Override
    public List<Category> getCategories(HealthServiceRequestParams params) {
        return null;
    }

    @Override
    public Category getCategory(int id) {
        return null;
    }

    @Override
    public List<Address> getAddresses() {
        return null;
    }

    @Override
    public List<Address> getAddresses(HealthServiceRequestParams params) {
        return null;
    }

    @Override
    public Address getAddress(int id) {
        return null;
    }

    @Override
    public List<Coordinate> getCoordinates() {
        return null;
    }

    @Override
    public List<Coordinate> getCoordinates(HealthServiceRequestParams params) {
        return null;
    }

    @Override
    public Address getCoordinate(int id) {
        return null;
    }

    private Object processResponse(Type type, String jsonString)
    {
        Gson gson = new Gson();

        try
        {
            Log.d(TAG, "Attempting to convert JSON String to type: " + type.getClass().getName());

            return gson.fromJson(jsonString, type);
        }
        catch(JsonSyntaxException e)
        {
            Log.e(TAG, e.toString());
        }

        return null;
    }

    public void handleResult(Object data)
    {

    }
}
