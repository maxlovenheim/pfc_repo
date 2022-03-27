package com.example.iban.Iban;


import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("api/v1/iban/")
public class IbanController {

    private final IbanService ibanService;

    @Autowired
    public IbanController(IbanService ibanService) {
        this.ibanService = ibanService;
    }

    @PostMapping("validate")
    public ResponseEntity ibanIsValid(@RequestBody String iban) throws ParseException {
        try {
            JSONParser jsonParser = new JSONParser(iban);
            HashMap<String,String> map = (HashMap<String, String>) jsonParser.parse();
            if (map.get("iban") != null) {
                return new ResponseEntity(this.ibanService.ibanIsValid(map.get("iban")), HttpStatus.OK);
            } else {
                return new ResponseEntity("Couldn't parse request, check format!", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            System.out.println("Couldn't handle request!");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
