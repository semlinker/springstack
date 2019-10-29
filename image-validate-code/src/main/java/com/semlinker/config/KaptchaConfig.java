package com.semlinker.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.semlinker.properties.ImageCodeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@EnableConfigurationProperties(ImageCodeProperties.class)
public class KaptchaConfig {
    private ImageCodeProperties imageCodeProperties;

    @Autowired
    public void setImageCodeProperties(ImageCodeProperties imageCodeProperties) {
        this.imageCodeProperties = imageCodeProperties;
    }

    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        properties.setProperty("kaptcha.textproducer.char.space", "5");
        properties.setProperty("kaptcha.image.width", String.valueOf(imageCodeProperties.getWidth()));
        properties.setProperty("kaptcha.image.height", String.valueOf(imageCodeProperties.getHeight()));
        properties.setProperty("kaptcha.textproducer.char.length", String.valueOf(imageCodeProperties.getLength()));
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}

