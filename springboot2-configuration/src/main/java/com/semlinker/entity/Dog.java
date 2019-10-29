package com.semlinker.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Dog implements Serializable {
    private String name;
    private Integer age;
}
