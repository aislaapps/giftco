package com.giftco.gifting.service.impl;

import com.giftco.gifting.dto.GiftRegistryDto;
import com.giftco.gifting.entity.Accounts;
import com.giftco.gifting.entity.GiftFormats;
import com.giftco.gifting.entity.GiftRegistry;
import com.giftco.gifting.exception.ActionNotAuthorisedException;
import com.giftco.gifting.exception.DateTimeException;
import com.giftco.gifting.exception.ResourceNotFoundException;
import com.giftco.gifting.mappers.GiftRegistryMapper;
import com.giftco.gifting.repository.AccountsRepository;
import com.giftco.gifting.repository.GiftFormatsRepository;
import com.giftco.gifting.repository.GiftRegistryRepository;
import com.giftco.gifting.service.IGiftRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GiftRegistryImpl implements IGiftRegistryService {

    private AccountsRepository accountsRepository;
    private GiftRegistryRepository giftRegistryRepository;
    private GiftFormatsRepository giftFormatsRepository;

    @Override
    public String createGiftRegistry(GiftRegistryDto giftRegistryDto) {

        GiftRegistry newGiftRegistry = GiftRegistryMapper.mapToGiftRegistry(giftRegistryDto, new GiftRegistry());
        GiftFormats giftFormat = newGiftRegistry.getGiftFormat();
        Accounts adminAccount = newGiftRegistry.getAdminAccount();

        giftRegistryRepository.save(setupNewGiftRegistry(newGiftRegistry, giftFormat, adminAccount));

        String response = String.format("Gift Registry '%s' created successfully at %s with ID: %s, and with fields: %s ",newGiftRegistry.getName(),
                newGiftRegistry.getCreatedAt(), newGiftRegistry.getId(), newGiftRegistry.toString());
        return response;
    }

    public GiftRegistry setupNewGiftRegistry(GiftRegistry newGiftRegistry, GiftFormats giftFormat, Accounts adminAccount) {

        // find the admin account by email address, if it doesn't exist, raise an exception
        String email = adminAccount.getEmailAddress();
        adminAccount = accountsRepository.findByEmailAddress(email)
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "emailAddress", email));
        // check if the account type is "Admin"
        if (adminAccount.getAccountType() == null || !adminAccount.getAccountType().getAccountTypeName().equals("Admin")) {
            throw new ActionNotAuthorisedException(adminAccount.getEmailAddress(), "Create Gift Registry");
        }

        newGiftRegistry.setAdminAccount(adminAccount);
        newGiftRegistry.setCreatedAt(LocalDateTime.now());
        newGiftRegistry.setCreatedBy(email);

        if (newGiftRegistry.getGiftFormat() == null) {// if gift format is null, throw an exception
            throw new ResourceNotFoundException("GiftFormats", "name", "N/A");
        }
        //fetch the GiftFormat by name, if it doesn't exist, raise an exception
        giftFormat = giftFormatsRepository.findByGiftFormatName(giftFormat.getGiftFormatName())
                .orElseThrow(() -> new ResourceNotFoundException("GiftFormats", "name", "Doesn't Exist"));

        newGiftRegistry.setGiftFormat(giftFormat);

        return newGiftRegistry;
    }

    @Override
    public boolean updateGiftRegistry(String adminEmailAddress, GiftRegistryDto giftRegistryDto) {
        boolean isUpdated = false;
        // convert GiftRegistryDto to GiftRegistry entity (should include id)
        System.out.println("Updating GiftRegistry with ID: " + giftRegistryDto.getId());
        GiftRegistry requestGiftRegistry = GiftRegistryMapper.mapToGiftRegistry(giftRegistryDto, new GiftRegistry());
        System.out.println("Request GiftRegistry: " + requestGiftRegistry.getId());

        //find existing gift registry by ID
        GiftRegistry existingGiftRegistry = giftRegistryRepository.findById(requestGiftRegistry.getId())
                .orElseThrow(() -> new ResourceNotFoundException("GiftRegistry", "id", String.valueOf(requestGiftRegistry.getId())));

        // check if the account is admin
        Accounts adminAccount = accountsRepository.findByEmailAddress(adminEmailAddress)
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "emailAddress", adminEmailAddress));

        // check if the account email address is the same as the gift registry admin account email address
        if (!adminAccount.getEmailAddress().equals(existingGiftRegistry.getAdminAccount().getEmailAddress())) {
            throw new ActionNotAuthorisedException(adminEmailAddress, "Update Gift Registry");
        }

        // update the existing gift registry with new values
        if (existingGiftRegistry != null) {
            // next check if the gift format exists then set it
            GiftFormats giftFormat = requestGiftRegistry.getGiftFormat();
            giftFormat = giftFormatsRepository.findByGiftFormatName(giftFormat.getGiftFormatName())
                    .orElseThrow(() -> new ResourceNotFoundException("GiftFormats", "name", "Doesn't Exist"));
            requestGiftRegistry.setGiftFormat(giftFormat);
            // update the rest of the fields
            existingGiftRegistry.setGiftFormat(requestGiftRegistry.getGiftFormat());
            existingGiftRegistry.setName(requestGiftRegistry.getName());
            existingGiftRegistry.setDepartureDate(requestGiftRegistry.getDepartureDate());
            existingGiftRegistry.setDebarkationDate(requestGiftRegistry.getDebarkationDate());
            existingGiftRegistry.setBirthDate(requestGiftRegistry.getBirthDate());

            // if gift format is cruise, set birth date to null
            if (existingGiftRegistry.getGiftFormat().getGiftFormatName().equals("Cruise")) {
                existingGiftRegistry.setBirthDate(null);
            } else if (existingGiftRegistry.getGiftFormat().getGiftFormatName().equals("Celebration")) {
                // if gift format is celebration, set departure and debarkation dates to null
                existingGiftRegistry.setDepartureDate(null);
                existingGiftRegistry.setDebarkationDate(null);
            }

            //expiry date should not be before the creation date or now
            if (requestGiftRegistry.getExpiryDate() != null
                    && requestGiftRegistry.getExpiryDate().isBefore(LocalDateTime.now())) {
                throw new DateTimeException(LocalDateTime.now().toString(),requestGiftRegistry.getExpiryDate().toString());
            }
//            if (requestGiftRegistry.getExpiryDate() != null
//                    && requestGiftRegistry.getExpiryDate().isBefore(existingGiftRegistry.getCreatedAt())) {
//                throw new ("GiftRegistry", "expiryDate", "Cannot be before creation date");
//            }
            existingGiftRegistry.setExpiryDate(requestGiftRegistry.getExpiryDate());

            // set updated to now and save
            existingGiftRegistry.setUpdatedAt(LocalDateTime.now());
            giftRegistryRepository.save(existingGiftRegistry);

            isUpdated = true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteGiftRegistry(Long giftRegistryId) {
        boolean isDeleted = false;
        //fetch existing gift registry
        GiftRegistry currentGiftRegistry = giftRegistryRepository.findById(giftRegistryId)
                .orElseThrow(() -> new ResourceNotFoundException("GiftRegistry", "id", String.valueOf(giftRegistryId)));
        //delete the account
        if (currentGiftRegistry != null) {
            giftRegistryRepository.delete(currentGiftRegistry);
            isDeleted = true;
        }

        return isDeleted;
    }

    @Override
    public boolean addMembersToGiftRegistry(Long giftRegistryId, List<Accounts> members) {
        boolean isAdded = false;
        // fetch existing gift registry
        GiftRegistry currentGiftRegistry = giftRegistryRepository.findById(giftRegistryId)
                .orElseThrow(() -> new ResourceNotFoundException("GiftRegistry", "id", String.valueOf(giftRegistryId)));
        //null check

        // then add members to the gift registry
        // for each member, find the account by email address
        //foreach loop to iterate through the members list
        for (Accounts member : members) {
            // check if the account exists
            Accounts existingAccount = accountsRepository.findByEmailAddress(member.getEmailAddress())
                    .orElseThrow(() -> new ResourceNotFoundException("Accounts", "emailAddress", member.getEmailAddress()));
            //check if account type is not member
            if (existingAccount.getAccountType() == null || !existingAccount.getAccountType().getAccountTypeName().equals("Member")) {
                throw new ActionNotAuthorisedException(existingAccount.getEmailAddress(), "Be a Member of Gift Registry, as it is not a 'Member' account");
            }
            // check if account is already in the list. If not, then add it
            if (!currentGiftRegistry.getMembers().contains(existingAccount)) {
                currentGiftRegistry.getMembers().add(existingAccount);
                isAdded = true;
            }
        }

        // save the updated gift registry
        if (isAdded) {
            giftRegistryRepository.save(currentGiftRegistry);
        }

        return isAdded;
    }

    @Override
    public GiftRegistryDto fetchGiftRegistry(Long giftRegistryId, String adminEmailAddress) {
        // fetch existing gift registry
        GiftRegistry currentGiftRegistry = giftRegistryRepository.findById(giftRegistryId)
                .orElseThrow(() -> new ResourceNotFoundException("GiftRegistry", "id", String.valueOf(giftRegistryId)));

        // check if the admin email address matches the gift registry admin account email address
        if (!currentGiftRegistry.getAdminAccount().getEmailAddress().equals(adminEmailAddress)) {
            throw new ActionNotAuthorisedException(adminEmailAddress, "Fetch this Gift Registry - account is not admin of this Gift Registry");
        }

        // map the gift registry to DTO
        return GiftRegistryMapper.mapToGiftRegistryDto(currentGiftRegistry, new GiftRegistryDto());
    }

}
