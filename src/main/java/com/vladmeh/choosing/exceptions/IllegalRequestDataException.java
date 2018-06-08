package com.vladmeh.choosing.exceptions;

import org.springframework.lang.NonNull;

public class IllegalRequestDataException extends RuntimeException {
    public IllegalRequestDataException(@NonNull String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        return getMessage();
    }

}