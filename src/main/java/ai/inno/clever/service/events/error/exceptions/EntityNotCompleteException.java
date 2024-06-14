package ai.inno.clever.service.events.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityNotCompleteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntityNotCompleteException(String message) {
        super(message);
    }
}
