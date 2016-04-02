package RuralHealthAPIClient;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by John on 2/04/2016.
 */
@Root
public class Config {
    @Element(name = "URI_SCHEME")
    private String uriScheme;

    @Element(name="SERVER")
    private String server;

    @Element(name="PORT")
    private int port;

    @Element(name="API_CONTEXT")
    private String apiContext;

    @Element(name="HEALTH_SERVICE_ENDPOINT")
    private String healthServiceEndpoint;

    @Element(name="ADDRESS_ENDPOINT")
    private String addressEndpoint;

    @Element(name="CATEGORIES_ENDPOINT")
    private String categoriesEndpoint;

    @Element(name="COORDINATES_ENDPOINT")
    private String coordinatesEndpoint;

    public String getUriScheme() {
        return uriScheme;
    }

    public void setUriScheme(String uriScheme) {
        this.uriScheme = uriScheme;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getApiContext() {
        return apiContext;
    }

    public void setApiContext(String apiContext) {
        this.apiContext = apiContext;
    }

    public String getHealthServiceEndpoint() {
        return healthServiceEndpoint;
    }

    public void setHealthServiceEndpoint(String healthServiceEndpoint) {
        this.healthServiceEndpoint = healthServiceEndpoint;
    }

    public String getAddressEndpoint() {
        return addressEndpoint;
    }

    public void setAddressEndpoint(String addressEndpoint) {
        this.addressEndpoint = addressEndpoint;
    }

    public String getCategoriesEndpoint() {
        return categoriesEndpoint;
    }

    public void setCategoriesEndpoint(String categoriesEndpoint) {
        this.categoriesEndpoint = categoriesEndpoint;
    }

    public String getCoordinatesEndpoint() {
        return coordinatesEndpoint;
    }

    public void setCoordinatesEndpoint(String coordinatesEndpoint) {
        this.coordinatesEndpoint = coordinatesEndpoint;
    }
}
