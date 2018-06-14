package com.vladmeh.choosing.exceptions;


import com.vladmeh.choosing.exceptions.error.ApplicationException;
import com.vladmeh.choosing.exceptions.error.ErrorType;
import com.vladmeh.choosing.exceptions.error.ExceptionResponse;
import com.vladmeh.choosing.util.MessageUtil;
import com.vladmeh.choosing.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

//@RestControllerAdvice
//@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class RestExceptionHandler {

    private static final String EXCEPTION_DUPLICATE_EMAIL = "exception.user.duplicateEmail";
    private static final String EXCEPTION_DUPLICATE_NAME = "exception.restaurant.duplicateName";

    private static final Map<String, String> CONSTRAINS_I18N_MAP = Collections.unmodifiableMap(
            new HashMap<String, String>() {
                {
                    put("unique_users_email_idx", EXCEPTION_DUPLICATE_EMAIL);
                    put("unique_restaurant_name_idx", EXCEPTION_DUPLICATE_NAME);
                }
            });

    private static Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    private final MessageUtil messageUtil;

    @Autowired
    public RestExceptionHandler(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<Object> handleApplicationException(ApplicationException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getHttpStatus());
        return buildResponseEntity(exceptionResponse, request, ex, false, ex.getType(), messageUtil.getMessage(ex));
    }

    @ExceptionHandler(AccessDeniedException.class) //403
    public final ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.FORBIDDEN, "Access denied message here", ex.getLocalizedMessage());
        return buildResponseEntity(exceptionResponse, request, ex, true, ErrorType.ACCESS_DENIED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class) //409
    public final ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT);
        String rootMsg = ValidationUtil.getRootCause(ex).getMessage();

        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            Optional<Map.Entry<String, String>> entry = CONSTRAINS_I18N_MAP.entrySet().stream()
                    .filter(it -> lowerCaseMsg.contains(it.getKey()))
                    .findAny();
            if (entry.isPresent()) {
                return buildResponseEntity(exceptionResponse, request, ex, false, ErrorType.VALIDATION_ERROR, messageUtil.getMessage(entry.get().getValue()));
            }
        }
        return buildResponseEntity(exceptionResponse, request, ex, true, ErrorType.DATA_ERROR);
    }

    @ExceptionHandler(Exception.class) //500
    public final ResponseEntity<Object> handleErrorException(Exception ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST);
        return buildResponseEntity(exceptionResponse, request, ex, true, ErrorType.APP_ERROR);
    }

    private ResponseEntity<Object> buildResponseEntity(ExceptionResponse exceptionResponse, HttpServletRequest req, Exception ex, boolean logException, ErrorType errorType, String... details) {
        Throwable rootCause = ValidationUtil.getRootCause(ex);

        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }

        exceptionResponse.setMessage(messageUtil.getMessage(errorType.getErrorCode()));
        exceptionResponse.setDebug(details.length != 0 ? Arrays.toString(details) : ValidationUtil.getMessage(rootCause));

        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
    }

}
