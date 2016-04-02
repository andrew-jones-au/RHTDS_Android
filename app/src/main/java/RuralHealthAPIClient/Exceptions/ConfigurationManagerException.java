package RuralHealthAPIClient.Exceptions;

/**
 * Created by John on 2/04/2016.
 */
public class ConfigurationManagerException extends Exception {
    public ConfigurationManagerException(){super();}
    public ConfigurationManagerException(String message){super(message);}
    public ConfigurationManagerException(Throwable cause){super(cause);}
    public ConfigurationManagerException(String message, Throwable cause){super(message, cause);}
}
