package bankingsystem.backend.config;

import bankingsystem.backend.dto.Constants;
import bankingsystem.backend.dto.Response;
import bankingsystem.backend.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandleResponse extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<Object> handleBadRequest(
            RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(Constants.ERROR, ex.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex) {
        logger.error("error produced during transfer money : {}", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(Constants.ERROR, ex.getMessage()));
    }
}
