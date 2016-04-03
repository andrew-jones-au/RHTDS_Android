package RuralHealthAPIClient;

import java.net.URI;

import RuralHealthAPIClient.Interfaces.TaskParamInterface;

/**
 * Created by John on 3/04/2016.
 */
public class HealthServiceRequestParams implements TaskParamInterface {
    private URI uri;

    public URI getURI() {
        return uri;
    }

    public void setURI(URI uri) {
        this.uri = uri;
    }
}
