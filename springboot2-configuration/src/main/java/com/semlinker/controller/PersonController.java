package com.semlinker.controller;

import com.semlinker.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    @Autowired
    private Person person;

    @RequestMapping("/person")
    public String index() {
        return "My name is " + person.getName() + " and my sex is " +
                person.getSex();
    }
}
