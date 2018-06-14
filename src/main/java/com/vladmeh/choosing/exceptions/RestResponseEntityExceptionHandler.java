package com.vladmeh.choosing.exceptions;

import com.vladmeh.choosing.exceptions.error.ExceptionResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * HTTP 400 Bad Request
     * Handle MissingServletRequestParameterException.
     * Triggered when a 'required' request parameter is missing.
     * Запускается, когда отсутствует параметр требуемого запроса.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ExceptionResponse(BAD_REQUEST, error, ex));
    }

    /**
     * HTTP 415 Unsupported Media Type
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     * указывает, что сервер отказывается принять запрос, потому что формат полезной нагрузки
     * находится в неподдерживаемом формате.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
            HttpStatus status,  WebRequest request) {

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new ExceptionResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
    }

    /**
     * HTTP 400 Bad Request
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     * Вызывается, когда объект не удовлетворяет @Valid проверки.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
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

    @ExceptionHandler(Exception.class) //500
    public final ResponseEntity<Object> handleErrorException(
            Exception ex, WebRequest request) {

        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(new ExceptionResponse(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }

        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, ex);
        return buildResponseEntity(exceptionResponse);
    }



    private ResponseEntity<Object> buildResponseEntity(ExceptionResponse exceptionResponse) {
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getStatus());
    }
}
