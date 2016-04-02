package RuralHealthAPIClient.Exceptions;

/**
 * Created by John on 2/04/2016.
 */
public class RuralHealthAPIClientException extends Exception
{
    public RuralHealthAPIClientException(){super();}
    public RuralHealthAPIClientException(String message){super(message);}
    public RuralHealthAPIClientException(Throwable cause){super(cause);}
    public RuralHealthAPIClientException(String message, Throwable cause){super(message, cause);}
}
