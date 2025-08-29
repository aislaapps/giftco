package com.giftco.gifting.service;

import com.giftco.gifting.dto.GiftRegistryDto;
import com.giftco.gifting.entity.Accounts;
import com.giftco.gifting.entity.GiftRegistry;

import java.util.List;

public interface IGiftRegistryService {

    //create gift_registry
    /**
     * Method to create a new gift registry.
     *
     * @param giftRegistryDto the DTO containing gift registry details
     */
    String createGiftRegistry(GiftRegistryDto giftRegistryDto);

    //update gift_registry
    /**
     * Method to update an existing gift registry.
     *
     * @param giftRegistryDto the DTO containing updated gift registry details
     */
    boolean updateGiftRegistry(String adminEmailAddress, GiftRegistryDto giftRegistryDto);

    // delete gift_registry
    /**
     * Method to delete a gift registry by its ID.
     *
     * @param giftRegistryId the ID of the gift registry to be deleted
     */
    boolean deleteGiftRegistry(Long giftRegistryId);

    // add members to gift_registry
    /**
     * Method to add members to an existing gift registry.
     *
     * @param giftRegistryId the ID of the gift registry
     * @param memberEmail the email address of the member to be added
     */
    boolean addMembersToGiftRegistry(Long giftRegistryId, List<Accounts> members);

    // fetch gift_registry by ID and admin email
    /**
     * Method to fetch a gift registry by its ID and admin email address.
     *
     * @param giftRegistryId the ID of the gift registry
     * @param adminEmailAddress the email address of the admin
     */
    GiftRegistryDto fetchGiftRegistry(Long giftRegistryId, String adminEmailAddress);
}
