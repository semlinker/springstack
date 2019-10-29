package com.semlinker.controller;

import com.semlinker.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private User user;

    @RequestMapping("/user")
    public String index() {
        return user.toString();
    }
}
