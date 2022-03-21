package mealsapp.error;

import mealsapp.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExistingFieldException.class)
    public ResponseEntity<Response> handleFieldConflict(final RuntimeException ex) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .message(ex.getMessage())
                        .status(CONFLICT)
                        .statusCode(CONFLICT.value())
                        .build()
        );
    }

}
