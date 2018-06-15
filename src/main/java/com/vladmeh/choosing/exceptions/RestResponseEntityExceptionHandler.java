package com.vladmeh.choosing.exceptions;

import com.vladmeh.choosing.exceptions.error.ExceptionResponse;
import com.vladmeh.choosing.util.ValidationUtil;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String EXCEPTION_DUPLICATE_EMAIL = "User with this email already exists";
    private static final String EXCEPTION_DUPLICATE_NAME = "Restaurant with this name already exist";

    private static final Map<String, String> CONSTRAINS_I18N_MAP = Collections.unmodifiableMap(
            new HashMap<String, String>() {
                {
                    put("unique_users_email_idx", EXCEPTION_DUPLICATE_EMAIL);
                    put("unique_restaurant_name_idx", EXCEPTION_DUPLICATE_NAME);
                }
            });

    /**
     * Handle MissingServletRequestParameterException.
     * Triggered when a 'required' request parameter is missing.
     * Запускается, когда отсутствует параметр требуемого запроса.
     * HTTP 400 Bad Request
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ExceptionResponse(BAD_REQUEST, error, ex));
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     * Вызывается, когда объект не удовлетворяет @Valid проверке.
     * HTTP 400 Bad Request
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(BAD_REQUEST);
        exceptionResponse.setMessage("Validation error");
        exceptionResponse.addValidationErrors(ex.getBindingResult().getFieldErrors());
        exceptionResponse.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(exceptionResponse);
    }

    /**
     * Handle AccessDeniedException.
     * HTTP 403 Forbidden
     *
     * @param ex the ValidationException
     * @return a {@code ResponseEntity} instance
     */
    @ExceptionHandler(AccessDeniedException.class) //403
    public final ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.FORBIDDEN, "Access denied", ex);
        return buildResponseEntity(exceptionResponse);
    }

    /**
     * Handle the response for NoHandlerFoundException.
     * HTTP 404 Not Found
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(NOT_FOUND, ex);
        return handleExceptionInternal(ex, exceptionResponse, headers, exceptionResponse.getStatus(), request);
    }

    /**
     * Handle javax.persistence.EntityNotFoundException
     * HTTP 404 Not Found
     *
     * @param ex the javax.persistence.EntityNotFoundException
     * @return a {@code ResponseEntity} instance
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        return buildResponseEntity(new ExceptionResponse(NOT_FOUND, ex));
    }

    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     * Проверяет причину возникновения различных ошибок БД
     * HTTP 409 Conflict or 400 Bad Request
     *
     * @param ex the DataIntegrityViolationException
     * @return a {@code ResponseEntity} instance
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<Object> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {
        String rootMsg = ValidationUtil.getRootCause(ex).getMessage();
        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            Optional<Map.Entry<String, String>> entry = CONSTRAINS_I18N_MAP.entrySet().stream()
                    .filter(it -> lowerCaseMsg.contains(it.getKey()))
                    .findAny();
            if (entry.isPresent()) {
                String message = "Validation error, " + entry.get().getValue();
                return buildResponseEntity(new ExceptionResponse(CONFLICT, message, ex));
            }
        }
        return buildResponseEntity(new ExceptionResponse(BAD_REQUEST, ex));
    }

    /**
     * Handle ValidationException, inspects the cause for different DB causes.
     * Проверяет причину возникновения различных ошибок БД
     * HTTP 409 Conflict
     *
     * @param ex the ValidationException
     * @return a {@code ResponseEntity} instance
     */
    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(
            ValidationException ex) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(CONFLICT, "Validation error", ex);
        if (ex instanceof ConstraintViolationException) {
            exceptionResponse.addValidationErrors(((ConstraintViolationException) ex).getConstraintViolations());
        }

        return buildResponseEntity(exceptionResponse);
    }

    /**
     * Handle Exception
     * HTTP 400 Bad Request
     *
     * @param ex the Exception
     * @return a {@code ResponseEntity} instance
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleErrorException(Exception ex) {
        Throwable rootCause = ValidationUtil.getRootCause(ex);

        if (rootCause instanceof ConstraintViolationException) {
            ExceptionResponse exceptionResponse = new ExceptionResponse(CONFLICT, "Validation error", ex.getCause());
            exceptionResponse.addValidationErrors(((ConstraintViolationException) rootCause).getConstraintViolations());
            return buildResponseEntity(exceptionResponse);
        }

        return buildResponseEntity(new ExceptionResponse(BAD_REQUEST, ex));
    }


    private ResponseEntity<Object> buildResponseEntity(ExceptionResponse exceptionResponse) {
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getStatus());
    }
}
