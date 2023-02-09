package org.unibl.etf.sni.anonymouschat.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleException(Exception e, HandlerMethod method){
        e.printStackTrace();
        return new ResponseEntity<>("Something went wrong.", HttpStatus.NOT_FOUND);
    }
}
