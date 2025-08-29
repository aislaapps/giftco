package com.giftco.gifting.dto;

import com.giftco.gifting.entity.AccountsType;
import lombok.*;

@Data
@Getter @Setter //@AllArgsConstructor @NoArgsConstructor
public class AccountRequestDto {
    private String emailAddress;
    private String username;
    private AccountsType accountType;
}
