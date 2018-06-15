package com.vladmeh.choosing.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 09.06.2018.
 * @link https://github.com/vladmeh/choose-restaurant
 */

@Data
public class ExceptionResponse {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debug;
    private List<DetailsError> details;

    private ExceptionResponse() {
        timestamp = LocalDateTime.now();
    }

    public ExceptionResponse(HttpStatus status) {
        this();
        this.status = status;
    }

    public ExceptionResponse(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debug = ex.getLocalizedMessage();
    }

    public ExceptionResponse(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debug = ex.getLocalizedMessage();
    }

    public ExceptionResponse(HttpStatus status, String message, String debug) {
        this();
        this.status = status;
        this.message = message;
        this.debug = debug;
    }

    private void addSubError(DetailsError subError) {
        if (details == null) {
            details = new ArrayList<>();
        }
        details.add(subError);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new DetailsValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new DetailsValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

    abstract class DetailsError {
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @AllArgsConstructor
    class DetailsValidationError extends DetailsError {
        private String object;
        private String field;
        private Object rejectedValue;
        private String message;

        DetailsValidationError(String object, String message) {
            this.object = object;
            this.message = message;
        }
    }
}
