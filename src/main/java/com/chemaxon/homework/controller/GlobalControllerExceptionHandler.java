package com.chemaxon.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;

/**
 * Created by szfilep on 15. 09. 06..
 * <p>
 * Global exception handlers for the REST API.
 */

@ControllerAdvice
class GlobalControllerExceptionHandler {

    /**
     * Handles all type of validation kind expcetions.
     */
    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class,
            ValidationException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class
    })
    public void validationError(HttpServletResponse resp) throws IOException {
        resp.sendError(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Handles all type of resource not found expcetions.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public void entityNotFound(HttpServletResponse resp) throws IOException {
        resp.sendError(HttpStatus.NOT_FOUND.value());
    }
}