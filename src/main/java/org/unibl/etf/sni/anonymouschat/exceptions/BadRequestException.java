package org.unibl.etf.sni.anonymouschat.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpException {

    public BadRequestException() {
        this(null);
    }

    public BadRequestException(Object data) {
        super(HttpStatus.BAD_REQUEST, data);
    }
}
