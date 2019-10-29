package com.semlinker.controller;

import com.semlinker.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {
    @Autowired
    private Blog blog;

    @RequestMapping("/blog")
    public String index() {
        return "My blog name is " + blog.getName() +
                " and title is " + blog.getTitle();
    }
}
