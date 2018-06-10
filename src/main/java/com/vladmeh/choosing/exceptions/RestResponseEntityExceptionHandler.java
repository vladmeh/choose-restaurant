package com.vladmeh.choosing.exceptions;

import com.vladmeh.choosing.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 09.06.2018.
 * @link https://github.com/vladmeh/choose-restaurant
 * http://www.baeldung.com/global-error-handler-in-a-spring-rest-api
 */

//@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        log.info(error);

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(headers.getDate()),
                HttpStatus.NOT_FOUND,
                error,
                request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(
            MissingPathVariableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        log.info("request description {}",request.getDescription(true));

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(headers.getDate()),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(
            ConversionNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        log.info("request description {}",request.getDescription(true));

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(headers.getDate()),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            HttpMessageNotWritableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        log.info("request description {}",request.getDescription(true));

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(headers.getDate()),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(
            Exception ex,
            WebRequest request) {
        log.info("request description {}", request.getDescription(true));

        Throwable rootCause = ValidationUtil.getRootCause(ex);

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                HttpStatus.BAD_REQUEST,
                ValidationUtil.getMessage(rootCause),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
    }
}
