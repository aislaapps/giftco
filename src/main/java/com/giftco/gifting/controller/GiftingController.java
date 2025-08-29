package com.giftco.gifting.controller;

import com.giftco.gifting.constants.AccountsConstants;
import com.giftco.gifting.dto.AccountRequestDto;
import com.giftco.gifting.dto.GiftRegistryDto;
import com.giftco.gifting.dto.ResponseDto;
import com.giftco.gifting.service.IAccountGiftingService;
import com.giftco.gifting.service.IGiftRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//requestMapping is used to map web requests onto specific handler functions
// Within it, "path" specifies the url path for the controller
// "produces" specifies the return type of the response, in this case, JSON
//JSON is a common format for APIs, allowing for easy data exchange between the server and client
@RestController
@RequestMapping (path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class GiftingController {

    private IAccountGiftingService iAccountGiftingService;
    private IGiftRegistryService iGiftRegistryService;

    //create account
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody AccountRequestDto request) {

        iAccountGiftingService.createAccount(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    //fetch account
    @GetMapping("/fetch")
    public ResponseEntity<AccountRequestDto> fetchAccount(@RequestParam String emailAddress) {

        AccountRequestDto requestDto = iAccountGiftingService.fetchAccount(emailAddress);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestDto);
    }

    //update account
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@RequestParam String emailAddress, @RequestBody AccountRequestDto requestDto) {
        boolean isUpdated = iAccountGiftingService.updateAccount(emailAddress, requestDto);
        //if successful, returns a 200 OK status with a message
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {//if not successful, returns a 500 Internal Server Error status with a message
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    @PutMapping("/updateAccountType")
    public ResponseEntity<ResponseDto> updateAccountType(@RequestParam String emailAddress, @RequestBody AccountRequestDto requestDto) {
        boolean isUpdated = iAccountGiftingService.updateAccountType(emailAddress, requestDto.getAccountType());
        //if successful, returns a 200 OK status with a message
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {//if not successful, returns a 500 Internal Server Error status with a message
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    //delete account
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam String emailAddress) {
        boolean isDeleted = iAccountGiftingService.deleteAccount(emailAddress);
        //if successful return 200 OK status
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else { //else return 500 internal server error
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));

        }
    }

}
