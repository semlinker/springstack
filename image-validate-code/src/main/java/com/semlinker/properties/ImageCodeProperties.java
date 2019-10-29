package com.semlinker.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kaptcha")
@Data
public class ImageCodeProperties {
    private int width = 90;
    private int height = 30;
    private int length = 6;
}
