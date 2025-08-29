package com.giftco.gifting.dto;

import com.giftco.gifting.entity.Accounts;
import com.giftco.gifting.entity.GiftFormats;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GiftRegistryDto {
    private Long id;
    private GiftFormats giftFormat;
    private String name;

    private LocalDateTime departureDate;
    private LocalDateTime debarkationDate;

    private LocalDateTime birthDate;
    private LocalDateTime expiryDate;

    private Accounts adminAccount;
    private List<Accounts> members;

}
