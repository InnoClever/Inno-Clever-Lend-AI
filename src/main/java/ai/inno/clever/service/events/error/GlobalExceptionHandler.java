package ai.inno.clever.service.events.error;

import ai.inno.clever.service.events.error.exceptions.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError
                err = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, "Resource Not Found", ex.getMessage());

        return ErrorResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(ActionNotPermittedException.class)
    public ResponseEntity<Object> handleActionNotPermitted(ActionNotPermittedException ex) {
        ApiError
                err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Action not permitted", ex.getMessage());

        return ErrorResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(PipelineException.class)
    public ResponseEntity<Object> handleActionNotPermitted(PipelineException ex) {
        ApiError
                err = new ApiError(LocalDateTime.now(), HttpStatus.ACCEPTED , "Pipeline Error", ex.getMessage());

        return ErrorResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(NotAuthorisedException.class)
    public ResponseEntity<Object> handleActionNotPermitted(NotAuthorisedException ex) {
        ApiError
                err = new ApiError(LocalDateTime.now(), HttpStatus.FORBIDDEN, "User not authorised", ex.getMessage());

        return ErrorResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleActionNotPermitted(UserAlreadyExistsException ex) {
        ApiError
                err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Could not create user", ex.getMessage());

        return ErrorResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex) {
        ApiError
                err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Format exception", ex.getMessage());

        return ErrorResponseEntityBuilder.build(err);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        return this.handleActionNotPermitted(ex);
    }

    public ResponseEntity<Object> handleActionNotPermitted(MethodArgumentNotValidException ex) {
        StringBuilder messageBuilder = new StringBuilder();

        ex
            .getBindingResult()
            .getFieldErrors()
            .forEach(err -> messageBuilder.append(err.getField()).append(" ").append(err.getDefaultMessage()).append(", "));
        String errorMessage = messageBuilder.toString();
        errorMessage = StringUtils.substring(errorMessage, 0, errorMessage.length() - 2);

        ApiError
                err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Registration could not be completed", errorMessage);
        return ErrorResponseEntityBuilder.build(err);
    }

//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(
//        HttpMessageNotReadableException ex,
//        HttpHeaders headers,
//        HttpStatus status,
//        WebRequest request
//    ) {
//        return this.handleActionNotPermitted(ex);
//    }

//    com.fasterxml.jackson.databind.exc.InvalidFormatException:
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<Object> handleActionNotPermitted(HttpMessageNotReadableException ex) {
//        StringBuilder messageBuilder = new StringBuilder();
//        messageBuilder.append(
//            ex
//                .getLocalizedMessage()
//                .split("; ")[0].replace("JSON parse error: ", "")
//                .replace("ai.innoclever.domain.enumeration.", "")
//                .replace("ai.innoclever.domain.", "")
//                .replace("ai.innoclever.", "")
//        );
//        String errorMessage = messageBuilder.toString();
//
//        ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "HttpMessageNotReadableException ", errorMessage);
//        return ErrorResponseEntityBuilder.build(err);
//    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Object> handleActionNotPermitted(ServerException ex) {
        ApiError err = new ApiError(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR,
            "There was a problem with the server while trying to perform this action",
            ex.getMessage()
        );

        return ErrorResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(FieldNotValidException.class)
    public ResponseEntity<Object> handleActionNotPermitted(FieldNotValidException ex) {
        ApiError
                err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Registration could not be completed", ex.getMessage());

        return ErrorResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(EntityNotCompleteException.class)
    public ResponseEntity<Object> handleActionNotPermitted(EntityNotCompleteException ex) {
        ApiError err = new ApiError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST,
            "Please complete all application fields!",
            ex.getMessage()
        );

        return ErrorResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(NumberOfTriesWithoutResultExceededException.class)
    public ResponseEntity<Object> handleNumberOfTriesExceeded(NumberOfTriesWithoutResultExceededException ex) {
        ApiError err = new ApiError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST,
            "Reached the maximum number of tries without a valid result.",
            ex.getMessage()
        );

        return ErrorResponseEntityBuilder.build(err);
    }
}
