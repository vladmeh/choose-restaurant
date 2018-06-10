package com.vladmeh.choosing.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 09.06.2018.
 * @link https://github.com/vladmeh/choose-restaurant
 */


public class ExceptionResponse {
    private Date timestamp;
    private HttpStatus status;
    private String message;
    private String[] details;

    public ExceptionResponse(Date timestamp, HttpStatus status, String message, String... details) {
        super();
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String[] getDetails() {
        return details;
    }
}
