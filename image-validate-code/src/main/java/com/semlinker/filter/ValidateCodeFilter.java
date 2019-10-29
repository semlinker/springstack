package com.semlinker.filter;

import com.semlinker.exception.ValidateCodeException;
import com.semlinker.handler.MyAuthenctiationFailureHandler;
import com.semlinker.validate.code.image.ImageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.semlinker.controller.ValidateCodeController.SESSION_KEY;

@Slf4j
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Autowired
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    @Autowired
    private SessionStrategy sessionStrategy;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 只处理POST方式的登录请求
        if (StringUtils.equals("/login", request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), "post")) {
            log.info("request : {}", request.getRequestURI());
            try {
                // 1. 进行验证码的校验
                doValidate(new ServletWebRequest(request));
            } catch (AuthenticationException e) {
                // 2. 捕获校验过程中出现的校验异常，交给失败处理器进行处理
                myAuthenctiationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        // 3. 校验通过，执行下一个过滤器，即UsernamePasswordAuthenticationFilter
        filterChain.doFilter(request, response);

    }

    private void doValidate(ServletWebRequest request) throws ServletRequestBindingException {
        // 1. 获取请求中的验证码
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
        // 2. 校验空值情况
        if (StringUtils.isEmpty(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }

        // 3. 从服务器session中获取已保存的验证码
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, SESSION_KEY);
        if (Objects.isNull(codeInSession)) {
            throw new ValidateCodeException("验证码不存在");
        }

        // 4. 校验服务器session中的验证码是否过期
        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, SESSION_KEY);
            throw new ValidateCodeException("验证码过期了");
        }

        // 5. 校验验证码是否一致
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        // 6. 移除已完成校验的验证码
        sessionStrategy.removeAttribute(request, SESSION_KEY);
    }
}
