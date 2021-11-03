package de.wagawin.recruitment.controller;

import de.wagawin.recruitment.exceptions.InternalException;
import de.wagawin.recruitment.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler class to avoid forwarding exception through API
 */
@RestControllerAdvice
public class ErrorController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(Exception e) {
        logger.error("Entity not found", e);
        return "Entity not found";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalException.class)
    public String handleInternalError(Exception e) {
        logger.error("Internal Error", e);
        return String.format("Internal error (%s)", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        logger.error("Unhandled Exception", e);
        return "Unhandled Exception";
    }

}
