package RuralHealthAPIClient;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.text.Format;

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

    @Element(name="HEALTH_SERVICE_RESOURCE")
    private String healthServiceResource;

    @Element(name="ADDRESS_RESOURCE")
    private String addressResource;

    @Element(name="CATEGORIES_RESOURCE")
    private String categoriesResource;

    @Element(name="COORDINATES_RESOURCE")
    private String coordinatesResource;

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

    public String getHealthServiceResource() {
        return healthServiceResource;
    }

    public void setHealthServiceResource(String healthServiceResource) {
        this.healthServiceResource = healthServiceResource;
    }

    public String getAddressResource() {
        return addressResource;
    }

    public void setAddressResource(String addressResource) {
        this.addressResource = addressResource;
    }

    public String getCategoriesResource() {
        return categoriesResource;
    }

    public void setCategoriesResource(String categoriesResource) {
        this.categoriesResource = categoriesResource;
    }

    public String getCoordinatesResource() {
        return coordinatesResource;
    }

    public void setCoordinatesResource(String coordinatesResource) {
        this.coordinatesResource = coordinatesResource;
    }

    public String getHealthServiceEndpoint()
    {
        return getUriScheme() + "://" + getServer() + ":" + getPort() +  getApiContext() +  getHealthServiceResource();
    }

    public String getAddressEndpoint()
    {
        return getUriScheme() + "://" + getServer() + ":" + getPort() + getApiContext() +  getAddressResource();
    }

    public String getCoordinateEndpoint()
    {
        return getUriScheme() + "://" + getServer() + ":" + getPort() + getApiContext() +  getCoordinatesResource();
    }

    public String getCategoryEndpoint()
    {
        return getUriScheme() + "://" + getServer() + ":" + getPort() +  getApiContext() +  getCategoriesResource();
    }
}
