package com.giftco.gifting.dto;

import com.giftco.gifting.entity.AccountsType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AccountsDto {
    private long accountId;
    private String emailAddress;
    private String username;
    private AccountsType accountType;
}
