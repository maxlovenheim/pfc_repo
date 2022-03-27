package com.example.iban;

import com.example.iban.Iban.IbanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IbanApplicationTests {

	private final IbanService ibanService;

	@Autowired
	public IbanApplicationTests(IbanService ibanService) {
		this.ibanService = ibanService;
	}



	@Test
	void contextLoads() {
	}

	@Test
	void validateSwedishIbanTest() {

		 assertTrue(this.ibanService.ibanIsValid("SE7280000810340009783242"));
	}


}
