package com.vladmeh.choosing.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 09.06.2018.
 * @link https://github.com/vladmeh/choose-restaurant
 */


public class ExceptionResponse {
    private HttpStatus status;
    private String message;
    private String details;

    public ExceptionResponse(HttpStatus status, String message, String details) {
        super();
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
