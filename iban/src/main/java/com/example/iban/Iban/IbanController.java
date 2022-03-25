package com.example.iban.Iban;


import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public boolean ibanIsValid(@RequestBody String iban) throws ParseException {
        JSONParser jsonParser = new JSONParser(iban);
        HashMap<String,String> map = (HashMap<String, String>) jsonParser.parse();

        return this.ibanService.ibanIsValid(map.get("iban"));
    }




}
