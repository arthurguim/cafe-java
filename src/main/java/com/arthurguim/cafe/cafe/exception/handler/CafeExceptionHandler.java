package com.arthurguim.cafe.cafe.exception.handler;

import com.arthurguim.cafe.cafe.exception.IngredientNotFoundException;
import com.arthurguim.cafe.cafe.model.rest.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CafeExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IngredientNotFoundException.class)
    public ErrorResponse handleIngredientNotFoundException(final Exception exception) {
        System.out.println(exception.getMessage());
        System.out.println(exception.getLocalizedMessage());
        return createErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    protected ErrorResponse createErrorResponse(final HttpStatus httpStatus, final String message) {
        System.out.println(message);
        return new ErrorResponse(httpStatus, message);
    }
}