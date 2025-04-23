package utez.edu.mx.communitycommitteesystem.exception;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        logger.error(ex);
        return ResponseEntity.badRequest().body(ErrorCatalog.DATA_INTEGRITY_VIOLATION_EXCEPTION.getMessage());

    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.error(ex);
        return ResponseEntity.ok(ErrorCatalog.ENTITY_NOT_FOUND_EXCEPTION.getMessage());

    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        logger.error(ex);
        return ResponseEntity.ok(ErrorCatalog.USERNAME_NOT_FOUND_EXCEPTION.getMessage());

    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error(ex);
        return ResponseEntity.ok(ErrorCatalog.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());

    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<String> handleMissingPathVariableException(MissingPathVariableException ex) {
        logger.error(ex);
        return ResponseEntity.ok(ErrorCatalog.PATH_MISSING.getMessage());

    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<String> handleIndexOutOfBoundsException(IndexOutOfBoundsException ex) {
        logger.error(ex);
        return ResponseEntity.ok(ErrorCatalog.INDEX_OUT_OF_BOUNDS_EXCEPTION.getMessage());

    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        logger.error(ex);
        return ResponseEntity.ok(ErrorCatalog.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION.getMessage());

    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        logger.error(ex);
        return ResponseEntity.ok(ErrorCatalog.NULL_POINTER_EXCEPTION.getMessage());

    }
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<String> handleArithmeticException(ArithmeticException ex) {
        logger.error(ex);
        return ResponseEntity.ok(ErrorCatalog.ARITHMETIC_EXCEPTION.getMessage());

    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        logger.error(ex);
        return ResponseEntity.ok(ErrorCatalog.HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION.getMessage());

    }


}