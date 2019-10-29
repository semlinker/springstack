package com.semlinker.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.semlinker.validate.code.image.ImageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
@RestController
public class ValidateCodeController {
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
    // 验证码过期秒数
    public static final int EXPIRE_SECOND = 60;

    @Autowired
    private SessionStrategy sessionStrategy;

    @Autowired
    DefaultKaptcha defaultKaptcha;


    @GetMapping("/code/image")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        String codeText = defaultKaptcha.createText();
        log.info("生成的验证码为: " + codeText);
        BufferedImage codeImage = defaultKaptcha.createImage(codeText);
        ImageCode imageCode = new ImageCode(codeImage, codeText, EXPIRE_SECOND);
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }
}
