package ai.inno.clever.service.events.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PipelineException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PipelineException(String message) {
        super(message);
    }
}
