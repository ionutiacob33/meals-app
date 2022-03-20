package mealsapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleUsernameConflict(final RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Username already exists");
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailConflict(final RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Email already exists");
    }

}
