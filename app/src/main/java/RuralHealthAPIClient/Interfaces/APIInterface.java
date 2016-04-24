package RuralHealthAPIClient.Interfaces;

import java.util.List;

import RuralHealthAPIClient.Models.HealthServiceRequestParams;
import RuralHealthAPIClient.Models.Address;
import RuralHealthAPIClient.Models.Category;
import RuralHealthAPIClient.Models.Coordinate;

/**
 * Created by John on 23/04/2016.
 */
public interface APIInterface {
    void getHealthServices();
    void getHealthServices(HealthServiceRequestParams params);
    void getHealthService(int id);

    List<Category> getCategories();
    List<Category> getCategories(HealthServiceRequestParams params);
    Category getCategory(int id);

    List<Address> getAddresses();
    List<Address> getAddresses(HealthServiceRequestParams params);
    Address getAddress(int id);

    List<Coordinate> getCoordinates();
    List<Coordinate> getCoordinates(HealthServiceRequestParams params);
    Address getCoordinate(int id);
}
