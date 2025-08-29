package com.giftco.gifting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// This exception is thrown when an account with the given details already exists in the system.
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
