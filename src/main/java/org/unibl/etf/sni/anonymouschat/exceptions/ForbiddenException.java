package org.unibl.etf.sni.anonymouschat.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends HttpException {

    public ForbiddenException() {
        this(null);
    }

    public ForbiddenException(Object data) {
        super(HttpStatus.FORBIDDEN, data);
    }
}
