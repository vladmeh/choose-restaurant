package com.vladmeh.choosing.exceptions;


import com.vladmeh.choosing.util.MessageUtil;
import com.vladmeh.choosing.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final String EXCEPTION_DUPLICATE_EMAIL = "exception.user.duplicateEmail";
    private static final String EXCEPTION_DUPLICATE_DATETIME = "exception.meal.duplicateDateTime";

    private static final Map<String, String> CONSTRAINS_I18N_MAP = Collections.unmodifiableMap(
            new HashMap<String, String>() {
                {
                    put("users_unique_email_idx", EXCEPTION_DUPLICATE_EMAIL);
                    put("uk_6dotkott2kjsp8vw4d0m25fb7", EXCEPTION_DUPLICATE_EMAIL);
                    put("meals_unique_user_datetime_idx", EXCEPTION_DUPLICATE_DATETIME);
                }
            });

    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    private final MessageUtil messageUtil;

    @Autowired
    public RestExceptionHandler(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        String rootMsg = ValidationUtil.getRootCause(ex).getMessage();

        if (rootMsg != null) {
            String lowerCaseMsg = rootMsg.toLowerCase();
            Optional<Map.Entry<String, String>> entry = CONSTRAINS_I18N_MAP.entrySet().stream()
                    .filter(it -> lowerCaseMsg.contains(it.getKey()))
                    .findAny();
            if (entry.isPresent()){
                return handleExceptionInternal(request, ex, false, ErrorType.VALIDATION_ERROR, messageUtil.getMessage(entry.get().getValue()));
            }
        }

        return handleExceptionInternal(request, ex, true, ErrorType.DATA_ERROR);
    }


    private ResponseEntity<Object> handleExceptionInternal(HttpServletRequest req, Exception ex, boolean logException, ErrorType errorType, String... details){
        Throwable rootCause = ValidationUtil.getRootCause(ex);

        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                HttpStatus.CONFLICT,
                messageUtil.getMessage(errorType.getErrorCode()),
                details.length !=0 ? details : new String[]{ValidationUtil.getMessage(rootCause)}
        );

        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
    }

}
