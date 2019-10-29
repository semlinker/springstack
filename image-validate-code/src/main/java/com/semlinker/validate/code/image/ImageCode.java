package com.semlinker.validate.code.image;

import com.semlinker.validate.code.ValidateCode;
import lombok.Getter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Getter
public class ImageCode extends ValidateCode {
    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }
}
