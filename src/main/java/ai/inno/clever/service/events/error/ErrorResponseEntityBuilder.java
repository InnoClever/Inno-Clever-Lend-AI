package ai.inno.clever.service.events.error;

import org.springframework.http.ResponseEntity;

public class ErrorResponseEntityBuilder {

    public static ResponseEntity<Object> build(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
