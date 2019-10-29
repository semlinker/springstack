package com.semlinker.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "blog")
@Component
public class Blog implements Serializable {
    private String name;

    private String title;
}
