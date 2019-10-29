package com.semlinker.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Person {
    @Value("${person.name}")
    private String name;

    @Value("${person.sex}")
    private Integer sex;
}
