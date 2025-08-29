package com.giftco.gifting.mappers;

import com.giftco.gifting.dto.GiftRegistryDto;
import com.giftco.gifting.entity.GiftRegistry;

public class GiftRegistryMapper {
    //map to GiftRegistry DTO
    public static GiftRegistryDto mapToGiftRegistryDto(GiftRegistry giftRegistry, GiftRegistryDto giftRegistryDto) {
        //giftRegistryDto.setId(giftRegistry.getId());
        giftRegistryDto.setName(giftRegistry.getName());

        giftRegistryDto.setDepartureDate(giftRegistry.getDepartureDate());
        giftRegistryDto.setDebarkationDate(giftRegistry.getDebarkationDate());
        //if its celebration
        giftRegistryDto.setBirthDate(giftRegistry.getBirthDate());
        //expirl
        giftRegistry.setExpiryDate(giftRegistry.getExpiryDate());

        //admin account and members
        giftRegistryDto.setAdminAccount(giftRegistry.getAdminAccount());
        giftRegistryDto.setMembers(giftRegistry.getMembers());

        return giftRegistryDto;
    }

    //map to GiftRegistry entity
    public static GiftRegistry mapToGiftRegistry(GiftRegistryDto giftRegistryDto, GiftRegistry giftRegistry) {
        // Ensure the giftRegistryDto id is not null
        if (giftRegistryDto.getId() != null) {
            giftRegistry.setId(giftRegistryDto.getId());
        }


        giftRegistry.setGiftFormat(giftRegistryDto.getGiftFormat());

        giftRegistry.setName(giftRegistryDto.getName());

        giftRegistry.setDepartureDate(giftRegistryDto.getDepartureDate());
        giftRegistry.setDebarkationDate(giftRegistryDto.getDebarkationDate());
        //if its celebration
        giftRegistry.setBirthDate(giftRegistryDto.getBirthDate());
        //expiry
        giftRegistry.setExpiryDate(giftRegistryDto.getExpiryDate());

        // Set admin account
        if (giftRegistryDto.getAdminAccount() != null) {
            giftRegistry.setAdminAccount(giftRegistryDto.getAdminAccount());
        }   else {
            giftRegistry.setAdminAccount(null);
        }
        // Set members
        if (giftRegistryDto.getMembers() != null && !giftRegistryDto.getMembers().isEmpty()) {
            giftRegistry.setMembers(giftRegistryDto.getMembers());
        } else {
            giftRegistry.setMembers(null);
        }

        return giftRegistry;
    }
}
