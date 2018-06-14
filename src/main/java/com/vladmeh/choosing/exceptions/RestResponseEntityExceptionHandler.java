package com.vladmeh.choosing.exceptions;

import com.vladmeh.choosing.exceptions.error.ExceptionResponse;
import com.vladmeh.choosing.util.ValidationUtil;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
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
     * Handle the response for NoHandlerFoundException.
     *
     * @param ex the exception
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(NOT_FOUND, ex);
        return handleExceptionInternal(ex, exceptionResponse, headers, exceptionResponse.getStatus(), request);
    }

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
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     * указывает, что сервер отказывается принять запрос, потому что формат полезной нагрузки
     * находится в неподдерживаемом формате.
     * HTTP 415 Unsupported Media Type
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new ExceptionResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
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
     * Handle javax.persistence.EntityNotFoundException
     *
     * @param ex the javax.persistence.EntityNotFoundException
     * @return a {@code ResponseEntity} instance
     */
    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
    public final ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
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
                return buildResponseEntity(new ExceptionResponse(HttpStatus.CONFLICT, message, ex));
            }
        }
        return buildResponseEntity(new ExceptionResponse(HttpStatus.BAD_REQUEST, ex));
    }


    /**
     * Handle Exception
     * HTTP 400 Bad Request or 500 Internal Server Error
     *
     * @param ex the Exception
     * @return a {@code ResponseEntity} instance
     */
    @ExceptionHandler(Exception.class) //500
    public final ResponseEntity<Object> handleErrorException(Exception ex) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(new ExceptionResponse(HttpStatus.BAD_REQUEST, "Database error", ex.getCause()));
        }
        return buildResponseEntity(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }


    private ResponseEntity<Object> buildResponseEntity(ExceptionResponse exceptionResponse) {
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getStatus());
    }
}
