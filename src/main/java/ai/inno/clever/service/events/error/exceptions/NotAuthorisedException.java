package ai.inno.clever.service.events.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotAuthorisedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotAuthorisedException(String message) {
        super(message);
    }
}
