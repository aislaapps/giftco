package com.giftco.gifting.mappers;
import com.giftco.gifting.dto.AccountRequestDto;
import com.giftco.gifting.dto.AccountsDto;
import com.giftco.gifting.entity.Accounts;

public class AccountsMapper {

    // map to accounts DTO
    public static AccountsDto mapToAccountsDto (Accounts accounts, AccountsDto accountsDto) {
        accountsDto.setAccountId(accounts.getAccountId());
        accountsDto.setEmailAddress(accounts.getEmailAddress());
        accountsDto.setUsername(accounts.getUsername());

        accountsDto.setAccountType(accounts.getAccountType());

        return accountsDto;
    }

    // map from DTO to accounts entity
    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts) {
        accounts.setAccountId(accountsDto.getAccountId());
        accounts.setEmailAddress(accountsDto.getEmailAddress());
        accounts.setUsername(accountsDto.getUsername());

        accounts.setAccountType(accountsDto.getAccountType());

        return accounts;
    }

    // map from accounts to accountRequest DTO
    public static AccountRequestDto mapToAccountRequestDto(Accounts accounts, AccountRequestDto accountRequestDto) {
        accountRequestDto.setEmailAddress(accounts.getEmailAddress());
        accountRequestDto.setUsername(accounts.getUsername());

        accountRequestDto.setAccountType(accounts.getAccountType());

        return accountRequestDto;
    }
}
