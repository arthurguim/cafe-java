package com.arthurguim.cafe.cafe.exception.handler;

import com.arthurguim.cafe.cafe.exception.HamburguerNotFoundException;
import com.arthurguim.cafe.cafe.exception.IngredientNotFoundException;
import com.arthurguim.cafe.cafe.model.rest.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
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
        return createErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HamburguerNotFoundException.class)
    public ErrorResponse handleHamburguerNotFoundException(final Exception exception) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ErrorResponse handleHttpMediaTypeNotSupportedException(final Exception exception) {
        return createErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleHttpMessageNotReadableException(final Exception exception) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Malformed JSON request");
    }

    protected ErrorResponse createErrorResponse(final HttpStatus httpStatus, final String message) {
        System.out.println(message);
        return new ErrorResponse(httpStatus, message);
    }
}