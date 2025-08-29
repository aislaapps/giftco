package com.giftco.gifting.service.impl;

import com.giftco.gifting.dto.AccountRequestDto;
import com.giftco.gifting.dto.GiftRegistryDto;
import com.giftco.gifting.entity.Accounts;
import com.giftco.gifting.entity.AccountsType;
import com.giftco.gifting.entity.GiftFormats;
import com.giftco.gifting.entity.GiftRegistry;
import com.giftco.gifting.exception.ResourceNotFoundException;
import com.giftco.gifting.mappers.AccountsMapper;
import com.giftco.gifting.mappers.GiftRegistryMapper;
import com.giftco.gifting.repository.AccountsRepository;
import com.giftco.gifting.repository.AccountsTypeRepository;
import com.giftco.gifting.repository.GiftFormatsRepository;
import com.giftco.gifting.repository.GiftRegistryRepository;
import com.giftco.gifting.service.IAccountGiftingService;

import com.giftco.gifting.exception.AccountAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountImpl implements IAccountGiftingService {

    private AccountsRepository accountsRepository;
    private AccountsTypeRepository accountsTypeRepository;
    private GiftRegistryRepository giftRegistryRepository;
    private GiftFormatsRepository giftFormatsRepository;

    @Override
    public void createAccount(AccountRequestDto requestDto) {
        //create and save new account
        Accounts newAccount = setupNewAccount(requestDto);
        accountsRepository.save(newAccount); // Assuming you have a way to get the repository instance
    }

    //create and set fields for new account
    private Accounts setupNewAccount(AccountRequestDto requestDto) {
        Accounts newAccount = new Accounts();

        //validate if email address already exists
        Optional<Accounts> existingAccount = accountsRepository.findByEmailAddress(requestDto.getEmailAddress());
        if (existingAccount.isPresent()) {
            throw new AccountAlreadyExistsException("Account already exists with email address: " + requestDto.getEmailAddress());
        }

        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy(requestDto.getEmailAddress());
        newAccount.setEmailAddress(requestDto.getEmailAddress());
        newAccount.setUsername(requestDto.getUsername());

        //set account type
        String requestDtoAccountTypeName = requestDto.getAccountType().getAccountTypeName();
        AccountsType currentAccountType = accountsTypeRepository.findByAccountTypeName(requestDtoAccountTypeName)
                .orElseThrow(() -> new ResourceNotFoundException("AccountType", "name", requestDtoAccountTypeName));
        newAccount.setAccountType(currentAccountType);

        return newAccount;
    }

    // Implement the methods defined in IAccountGiftingService interface
    public AccountRequestDto fetchAccount(String emailAddress) {
        Accounts accounts = accountsRepository.findByEmailAddress(emailAddress).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "emailAddress", emailAddress));
        //map to dto
        AccountRequestDto accountRequestDto = new AccountRequestDto();
        AccountsMapper.mapToAccountRequestDto(accounts, accountRequestDto);
        return accountRequestDto;
    }

    @Override
    public boolean updateAccount(String emailAddress, AccountRequestDto requestDto) {
        boolean isUpdated = false;
        //fetch existing account
        Accounts currentAccount = getAccountByEmail(emailAddress);
        //then update the fields and save account
        if (currentAccount != null) {
            currentAccount.setEmailAddress(requestDto.getEmailAddress());
            currentAccount.setUsername(requestDto.getUsername());
            currentAccount.setUpdatedAt(LocalDateTime.now());
            currentAccount.setUpdatedBy(requestDto.getUsername());

            //save updated account
            accountsRepository.save(currentAccount);
            isUpdated = true;
        }
        return isUpdated;
    }

    //update account type
    @Override
    public boolean updateAccountType(String emailAddress, AccountsType accountsType) {
        boolean isUpdated = false;
        //fetch existing account and its account type
        Accounts currentAccount = getAccountByEmail(emailAddress);
        AccountsType accountType = accountsTypeRepository.findByAccountTypeName(accountsType.getAccountTypeName()).orElseThrow(
                () -> new ResourceNotFoundException("AccountType", "name", accountsType.getAccountTypeName()));
        //update account type
        if (currentAccount != null) {
            currentAccount.setAccountType(accountType);
            //set updated by and updated at fields

            //save updated accountType
            accountsRepository.save(currentAccount);

            isUpdated = true;
        }
        return isUpdated;
    }

    public boolean deleteAccount(String emailAddress) {
        boolean isDeleted = false;
        //fetch existing account
        Accounts currentAccount = getAccountByEmail(emailAddress);
        //delete the account
        if (currentAccount != null) {
            accountsRepository.delete(currentAccount);
            isDeleted = true;
        }

        return isDeleted;
    }

    //method that calls the findByEmailAddress method, either raising an exception or returning the account.
    public Accounts getAccountByEmail(String emailAddress) {
        return accountsRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "emailAddress", emailAddress));
    }
}
