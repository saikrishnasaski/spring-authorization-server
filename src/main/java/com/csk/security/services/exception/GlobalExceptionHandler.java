package com.csk.security.services.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> exception(Exception e) {

        var error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);

        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<Error> exception(BusinessValidationException e) {

        var error = new Error(HttpStatus.BAD_REQUEST.toString(), e.getMessage(), e.getTarget());

        return ResponseEntity.badRequest().body(error);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Error {

        private String errorCode;
        private String errorMessage;
        private String target;
    }
}
