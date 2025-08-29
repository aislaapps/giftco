package com.giftco.gifting.controller;

import com.giftco.gifting.constants.GiftRegistryConstants;
import com.giftco.gifting.dto.GiftRegistryDto;
import com.giftco.gifting.dto.ResponseDto;
import com.giftco.gifting.entity.Accounts;
import com.giftco.gifting.service.IGiftRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/gift-registries", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class GiftRegistryController {

    private IGiftRegistryService iGiftRegistryService;

    // create a new gift registry
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createGiftRegistry(@RequestBody GiftRegistryDto requestDto) {

        String response = iGiftRegistryService.createGiftRegistry(requestDto);
        //return same response as create account
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(GiftRegistryConstants.STATUS_201, response));
    }

    // fetch a gift registry by ID and admin email
    @GetMapping("/fetch")
    public ResponseEntity<GiftRegistryDto> fetchGiftRegistry(@RequestParam Long giftRegistryId, @RequestParam String adminEmailAddress) {
        GiftRegistryDto giftRegistryDto = iGiftRegistryService.fetchGiftRegistry(giftRegistryId, adminEmailAddress);
        //if successful return 200 OK status with the gift registry details
        if (giftRegistryDto != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(giftRegistryDto);
        } else { //else return 404 Not Found status
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    // update existing gift registry
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateGiftRegistry(@RequestParam String adminEmailAddress, @RequestBody GiftRegistryDto requestDto) {
        boolean isUpdated = iGiftRegistryService.updateGiftRegistry(adminEmailAddress, requestDto);
        //if successful, returns a 200 OK status with a message
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(GiftRegistryConstants.STATUS_200, GiftRegistryConstants.MESSAGE_200));
        } else {//if not successful, returns a 500 Internal Server Error status with a message
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(GiftRegistryConstants.STATUS_500, GiftRegistryConstants.MESSAGE_500));
        }
    }

    // delete a gift registry by ID
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteGiftRegistry(@RequestParam Long giftRegistryId) {
        boolean isDeleted = iGiftRegistryService.deleteGiftRegistry(giftRegistryId);
        //if successful return 200 OK status
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(GiftRegistryConstants.STATUS_200, GiftRegistryConstants.MESSAGE_200));
        } else { //else return 500 internal server error
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(GiftRegistryConstants.STATUS_500, GiftRegistryConstants.MESSAGE_500));
        }
    }

    //add members to a gift registry
    @PutMapping("/addMembers")
    public ResponseEntity<ResponseDto> addMembersToGiftRegistry(@RequestParam Long giftRegistryId, @RequestBody List<Accounts> members)
    {
        boolean isAdded = iGiftRegistryService.addMembersToGiftRegistry(giftRegistryId, members);
        //if successful return 200 OK status
        if (isAdded) {
            String memberEmails = "";
            for (Accounts member : members) {
                memberEmails += member.getEmailAddress() + ", ";
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(GiftRegistryConstants.STATUS_200, memberEmails + " added to gift registry successfully."));
        } else { //else return 500 internal server error
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(GiftRegistryConstants.STATUS_500, GiftRegistryConstants.MESSAGE_500));
        }
    }
}

