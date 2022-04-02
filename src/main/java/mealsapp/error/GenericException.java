package mealsapp.error;

public class GenericException extends RuntimeException {
    public GenericException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }
    public GenericException(String exMessage) {
        super(exMessage);
    }
}
