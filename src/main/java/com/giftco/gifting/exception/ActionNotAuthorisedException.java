package com.giftco.gifting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// This class represents an exception that is thrown when an action is not authorized.
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ActionNotAuthorisedException extends RuntimeException {
    public ActionNotAuthorisedException(String accountEmailAddess, String action) {
        super(String.format("Account %s is not authorised to execute the following action: %s", accountEmailAddess, action));

    }
}
