package mealsapp.error;

public class ExistingFieldException extends RuntimeException {
    public ExistingFieldException(String message) {
        super(message);
    }
}
