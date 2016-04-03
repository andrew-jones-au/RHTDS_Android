package RuralHealthAPIClient.Exceptions;

/**
 * Created by John on 3/04/2016.
 */
public class RequestException extends RuralHealthAPIClientException {
    public RequestException(){super();}
    public RequestException(String message){super(message);}
    public RequestException(Throwable cause){super(cause);}
    public RequestException(String message, Throwable cause){super(message, cause);}
}
