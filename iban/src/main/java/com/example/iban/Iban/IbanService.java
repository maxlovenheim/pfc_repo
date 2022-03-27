package com.example.iban.Iban;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.*;

@Service
public class IbanService {
    private HashMap<String, HashMap<String, Integer>> countryCodes = new HashMap<>();
    private HashMap<String, String> ibanLetterTranslation = new HashMap<>();

    public IbanService() throws FileNotFoundException {
        setCountryCodes();
        setIbanLetterTranslation();
    }

    public void setCountryCodes() {
        String resourceName = "iban_codes_length.csv";
        ClassLoader classLoader = getClass().getClassLoader();

        try (Scanner scanner = new Scanner(new File(classLoader.getResource(resourceName).getFile()))) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] ccArr = line.split(";");
                HashMap<String, Integer> lengthMap = new HashMap<>();
                lengthMap.put("length", Integer.parseInt(ccArr[1]));
                countryCodes.put(ccArr[0], lengthMap);
            }
            System.out.println("Finished loading csv resource");
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find required resource");
            System.out.println(e);
        }
    }

    public void setIbanLetterTranslation() {
        List<String> letters = Arrays.asList("A", "B", "C", "D", "E", "F", "G",
                                        "H", "I", "J", "K", "L", "M", "N",
                                        "O", "P", "Q", "R", "S","T", "U",
                                        "V", "W","X", "Y", "Z");
        int index = 0;
        for (String letter : letters) {
            this.ibanLetterTranslation.put(letter, String.valueOf(index+10));
            index++;
        }
    }

    public BigInteger convertIbanToInteger(String iban) {
        StringBuilder ibanBuilder = new StringBuilder(iban);
        ibanBuilder.append(ibanBuilder.substring(0,4));
        ibanBuilder.replace(0 ,4 ,"");
        String ibanString = "";
        for (int i = 0; i < ibanBuilder.length(); i++) {
            if (this.ibanLetterTranslation.get(String.valueOf(ibanBuilder.charAt(i))) != null) {
                ibanString += this.ibanLetterTranslation.get(String.valueOf(ibanBuilder.charAt(i)));
            } else {
                ibanString+= String.valueOf(ibanBuilder.charAt(i));
            }
        }
        try {
            BigInteger result = new BigInteger(ibanString);
            return result;
        } catch (Exception e) {
            System.out.println("Couldn't convert iban to biginteger. Bad format!");
            System.out.println(e);
            return new BigInteger("0");
        }

    }

    public boolean ibanIsValid(String iban) {
        if (iban == null) {
            return false;
        }
        String countryCode = iban.substring(0,2);
        //Check valid country code
        if (this.countryCodes.get(countryCode) != null) {
            // Check correct length per country
            if (iban.length() == this.countryCodes.get(countryCode).get("length")) {
                BigInteger ibanAsInteger = convertIbanToInteger(iban);
                //Run mod 97 test to validate iban
                if (ibanAsInteger.mod(new BigInteger("97")).equals(new BigInteger("1"))) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
