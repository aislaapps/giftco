package com.giftco.gifting;

import com.giftco.gifting.entity.Accounts;
import com.giftco.gifting.service.impl.AccountImpl;
import com.giftco.gifting.dto.AccountRequestDto;
import com.giftco.gifting.entity.AccountsType;
import com.giftco.gifting.repository.AccountsRepository;
import com.giftco.gifting.repository.GiftRegistryRepository;
import com.giftco.gifting.repository.AccountsTypeRepository;
import com.giftco.gifting.repository.GiftFormatsRepository;
import com.giftco.gifting.service.IAccountGiftingService;
import net.bytebuddy.pool.TypePool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AccountGiftingServiceTest {

	private IAccountGiftingService accountGiftingService;
	private AccountsRepository accountsRepository;

	@BeforeEach
	void setUp() {
		//accountGiftingService = Mockito.mock(IAccountGiftingService.class);
		accountsRepository = Mockito.mock(AccountsRepository.class);
		AccountsTypeRepository accountsTypeRepository = Mockito.mock(AccountsTypeRepository.class);
		GiftRegistryRepository giftRegistryRepository = Mockito.mock(GiftRegistryRepository.class);
		GiftFormatsRepository giftFormatsRepository = Mockito.mock(GiftFormatsRepository.class);

		// Stub the AccountTypeRepository mock
		AccountsType memberType = new AccountsType();
		memberType.setAccountTypeName("Member");
		Mockito.when(accountsTypeRepository.findByAccountTypeName("Member"))
				.thenReturn(java.util.Optional.of(memberType));

		//do the same for admin
		AccountsType adminType = new AccountsType();
		adminType.setAccountTypeName("Admin");
		Mockito.when(accountsTypeRepository.findByAccountTypeName("Admin"))
				.thenReturn(java.util.Optional.of(adminType));

		//stub an account for editing tests
		Accounts existingAccount = new Accounts();
		existingAccount.setEmailAddress("editTest@giftAccount.com");
		existingAccount.setUsername("OldName");
		AccountsType editingAccountType = new AccountsType();
		editingAccountType.setAccountTypeName("Member");
		existingAccount.setAccountType(editingAccountType);
		Mockito.when(accountsRepository.findByEmailAddress(existingAccount.getEmailAddress()))
				.thenReturn(java.util.Optional.of(existingAccount));


		accountGiftingService = new AccountImpl(
				accountsRepository,
				accountsTypeRepository,
				Mockito.mock(GiftRegistryRepository.class),
				Mockito.mock(GiftFormatsRepository.class)
		);
	}

	//verify account creation with valid input
	@Test
	void shouldCreateAccountWithValidInput() {
		AccountRequestDto dto = new AccountRequestDto();
		dto.setEmailAddress("test@giftAccount.com");
		dto.setUsername("Tester");
		AccountsType accountsType = new AccountsType();
		accountsType.setAccountTypeName("Member");
		dto.setAccountType(accountsType);

		assertDoesNotThrow(() -> accountGiftingService.createAccount(dto));
	}

	//verify account creation fails with missinng username
	@Test
	void shouldThrowExceptionWhenUsernameIsMissing() {
		AccountRequestDto dto = new AccountRequestDto();
		dto.setEmailAddress("test@giftAccount.com");
		AccountsType accountsType = new AccountsType();
		accountsType.setAccountTypeName("Member");
		dto.setAccountType(accountsType);

		assertThrows(IllegalArgumentException.class, () -> accountGiftingService.createAccount(dto));
	}

	//verify account creation fails with missing email
	@Test
	void shouldThrowExceptionWhenEmailIsMissing() {
		AccountRequestDto dto = new AccountRequestDto();
		dto.setUsername("testuser");
		AccountsType accountsType = new AccountsType();
		accountsType.setAccountTypeName("Member");
		dto.setAccountType(accountsType);

		assertThrows(IllegalArgumentException.class, () -> accountGiftingService.createAccount(dto));
	}

	//verify account creation fails with missing account type
	@Test
	void shouldThrowExceptionWhenAccountTypeIsMissing() {
		AccountRequestDto dto = new AccountRequestDto();
		dto.setEmailAddress("test@giftAccount.com");
		dto.setUsername("testuser");

		assertThrows(IllegalArgumentException.class, () -> accountGiftingService.createAccount(dto));
	}

	//verify account creation fails with correct creation date (now)
	@Test
	void shouldCreateAccountWithCorrectCreationDate() {
		AccountRequestDto dto = new AccountRequestDto();
		dto.setEmailAddress("test@giftAccount.com");
		dto.setUsername("Tester");
		AccountsType accountsType = new AccountsType();
		accountsType.setAccountTypeName("Member");
		dto.setAccountType(accountsType);

		LocalDateTime before = LocalDateTime.now();

		//mock the repository to capture the saved account and verify createdAt is set to now
		AccountsRepository accountsRepository = Mockito.mock(AccountsRepository.class);
		Mockito.doAnswer(invocation -> {
			Accounts savedAccount = invocation.getArgument(0);
			LocalDateTime createdAt = savedAccount.getCreatedAt();
			LocalDateTime after = LocalDateTime.now();
			//check if createdAt is between before and after
			if (createdAt.isBefore(before) || createdAt.isAfter(after)) {
				throw new AssertionError("createdAt is not set to now");
			}

			return savedAccount;
		}).when(accountsRepository).save(Mockito.any(Accounts.class));

		accountGiftingService.createAccount(dto);
		//if creation date is in the past or future, throw exception
	}


	//editing account tests can be added here

	//template for creating edit account dto
	private AccountRequestDto createEditAccountDto(String email, String username, String accountTypeName) {
		AccountRequestDto dto = new AccountRequestDto();
		dto.setEmailAddress(email);
		dto.setUsername(username);
		AccountsType accountsType = new AccountsType();
		accountsType.setAccountTypeName(accountTypeName);
		dto.setAccountType(accountsType);
		return dto;
	}

	//verify account editing with valid input
	@Test
	void shouldEditAccountWithValidInput() {

		AccountRequestDto updateDto = new AccountRequestDto();
		updateDto.setEmailAddress("editTest@giftAccount.com");
		updateDto.setUsername("UpdatedName");
		AccountsType accountsType = new AccountsType();
		accountsType.setAccountTypeName("Member");
		updateDto.setAccountType(accountsType);

		// Act: update account
		accountGiftingService.updateAccount("editTest@giftAccount.com", updateDto);

		// Assert: verify save called with updated account
		Mockito.verify(accountsRepository).save(Mockito.argThat(acc ->
				acc.getUsername().equals("UpdatedName") &&
						acc.getEmailAddress().equals("editTest@giftAccount.com")
		));
	}

	// verify account editing fails with empty username
	@Test
	void shouldThrowExceptionWhenEditingUsernameIsEmpty() {
		AccountRequestDto updateDto = createEditAccountDto("editTest@giftAccount.com", "", "Member");
		assertThrows(IllegalArgumentException.class, () -> accountGiftingService.updateAccount("editTest@giftAccount.com", updateDto));
	}

	// verify account editing fails with empty email
	@Test
	void shouldThrowExceptionWhenEditingEmailIsEmpty() {
		AccountRequestDto updateDto = createEditAccountDto("", "NewName", "");
		assertThrows(IllegalArgumentException.class, () -> accountGiftingService.updateAccount("editTest@giftAccount.com", updateDto));
	}

	// verify account editing fails with empty account type
	@Test
	void shouldThrowExceptionWhenEditingAccountTypeIsInvalid() {
		AccountRequestDto updateDto = createEditAccountDto("", "NewName", "InvalidAccountTypeSoErrorNow");
		assertThrows(Exception.class, () -> accountGiftingService.updateAccount("editTestGiftAccount.com", updateDto));
	}

	// verify account editing updates the updatedAt field to now
//	@Test
//	void shouldUpdateUpdatedAtFieldWhenEditingAccount() {
//		AccountRequestDto updateDto = createEditAccountDto("editTest@giftAccount.com", "NewName", "Member");
//		LocalDateTime before = LocalDateTime.now();
//		accountGiftingService.updateAccount("editTest@giftAccount.com", updateDto);
//		LocalDateTime after = LocalDateTime.now();
//		//verify save called with updatedAt between before and after
//		Mockito.verify(accountsRepository).save(Mockito.argThat(acc -> {
//			LocalDateTime updatedAt = acc.getUpdatedAt();
//			return updatedAt != null && !updatedAt.isBefore(before) && !updatedAt.isAfter(after);
//		}));
//	}






//	@Test
//	void contextLoads() {
//	}



}
