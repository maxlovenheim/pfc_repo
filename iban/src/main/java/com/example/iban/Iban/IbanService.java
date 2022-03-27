package com.example.iban.Iban;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

@Service
public class IbanService {
    private HashMap<String, HashMap<String, Integer>> countryCodes = new HashMap<>();
    private HashMap<String, Integer> ibanLetterTranslation = new HashMap<>();

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
                System.out.println(line);
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
            this.ibanLetterTranslation.put(letter, index+10);
            index++;
        }
    }

    public long convertIbanToLong(String iban) {
        StringBuffer ibanBuffer = new StringBuffer(iban);
        ibanBuffer.append(ibanBuffer.substring(0,4));
        ibanBuffer.replace(0 ,4 ,"");
        return 0;
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
                long ibanAsLong = convertIbanToLong(iban);
                //Run mod 97 test to validate iban
                if (ibanAsLong % 97 == 1) {
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
