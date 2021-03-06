package com.tenpo.tenpobackendtest.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnautorizedException extends RuntimeException {

    public UnautorizedException(String message) {
        super(message);
    }

}
