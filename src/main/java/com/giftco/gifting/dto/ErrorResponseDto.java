package com.giftco.gifting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class ErrorResponseDto {
    private String errorCode;
    private HttpStatus httpStatus;
    private String errorDescription;
    private LocalDateTime localDateTime;
}
