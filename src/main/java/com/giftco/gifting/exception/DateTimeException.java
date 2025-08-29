package com.giftco.gifting.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DateTimeException extends RuntimeException {
    public DateTimeException(String condition, String value) {
        super(String.format("The provided date time: %s, cannot be before %s", value, condition));
    }
}
