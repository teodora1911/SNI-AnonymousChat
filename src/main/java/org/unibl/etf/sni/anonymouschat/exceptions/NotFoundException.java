package org.unibl.etf.sni.anonymouschat.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException {

    public NotFoundException() {
        this(null);
    }

    public NotFoundException(Object data) {
        super(HttpStatus.NOT_FOUND, data);
    }
}
