package org.unibl.etf.sni.anonymouschat.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends HttpException {

    public UnauthorizedException() {
        this(null);
    }

    public UnauthorizedException(Object data) {
        super(HttpStatus.UNAUTHORIZED, data);
    }
}
