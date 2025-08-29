package com.giftco.gifting.service;


import com.giftco.gifting.dto.AccountRequestDto;
import com.giftco.gifting.entity.AccountsType;
import com.giftco.gifting.dto.GiftRegistryDto;

public interface IAccountGiftingService {

    /* Method to create a new account for a user.
     *
     * @param emailAddress the email address of the user
     * @param username the username of the user
     */
    void createAccount(AccountRequestDto accountDto);

    //fetch account
    /*     * Method to fetch account details based on the email address.
     *
     * @param emailAddress the email address of the user whose account details are to be fetched
     */
    AccountRequestDto fetchAccount(String emailAddress);

    //update account
    boolean updateAccount(String emailAddress, AccountRequestDto accountDto);

    //update account type
    boolean updateAccountType(String emailAddress, AccountsType accountsType);

    //delete account
    boolean deleteAccount(String emailAddress);


}
