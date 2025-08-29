package com.giftco.gifting.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter @Setter
public class GiftRegistryResponseDto {
    private Long id;
    private String registryName;
    private LocalDateTime createdAt;
    private String createdBy;
}
