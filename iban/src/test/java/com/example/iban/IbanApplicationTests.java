package com.example.iban;

import com.example.iban.Iban.IbanController;
import com.example.iban.Iban.IbanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IbanApplicationTests {

	private final IbanService ibanService;
	private final IbanController ibanController;


	@Autowired
	public IbanApplicationTests(IbanController ibanController, IbanService ibanService) {
		this.ibanService = ibanService;
		this.ibanController = ibanController;
	}

	//Service Tests
	@Test
	void validateSwedishIbanTest() {
		 assertTrue(this.ibanService.ibanIsValid("SE7280000810340009783242"));
	}
	@Test
	void validateFrenchIbanTest() {
		assertTrue(this.ibanService.ibanIsValid("FR7630006000011234567890189"));
	}

	@Test
	void validateGermanIbanTest() {
		assertTrue(this.ibanService.ibanIsValid("DE75512108001245126199"));
	}

	@Test
	void validateMalteeseIbanTest() {
		assertTrue(this.ibanService.ibanIsValid("MT31MALT01100000000000000000123"));
	}

	@Test
	void noExistingCountryCodeIbanTest() {
		assertFalse(this.ibanService.ibanIsValid("SQ7280000810340009783242"));
	}

	@Test
	void notAllowedCharsIbanTest() {
		assertFalse(this.ibanService.ibanIsValid("SE7280000/103400097%3242"));
	}
}
