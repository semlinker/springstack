package com.semlinker.controller;

import com.sun.tools.javac.util.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;

@Controller
public class HomeController {
    private String[] users = {"Semlinker", "Lolo", "Kakuqo"};

    @GetMapping("/users")
    @ResponseBody
    public String[] users() {
        return users;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
