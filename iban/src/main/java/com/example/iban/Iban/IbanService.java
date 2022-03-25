package com.example.iban.Iban;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

@Service
public class IbanService {
    private HashMap<String, HashMap<String, Integer>> countryCodes = new HashMap<>();

    public IbanService() throws FileNotFoundException {
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


    public boolean ibanIsValid(String iban) {

        //Check valid country code

        // Check correct length per country
        //Run mod 97 test to validate iban
        System.out.println(iban);
        return false;
    }
}
