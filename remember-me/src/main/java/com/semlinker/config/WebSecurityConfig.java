package com.semlinker.config;

import com.semlinker.handler.MyAuthenctiationFailureHandler;
import com.semlinker.handler.MyAuthenctiationSuccessHandler;
import com.semlinker.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    @Autowired
    private MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;

    // 注入数据源
    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService myUserDetailService() {
        return new MyUserDetailsService();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl persistentTokenRepository = new JdbcTokenRepositoryImpl();
        persistentTokenRepository.setDataSource(dataSource);
        return persistentTokenRepository;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService()).passwordEncoder(passwordEncoder());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
//                .successHandler(myAuthenctiationSuccessHandler)
//                .failureHandler(myAuthenctiationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require", "/login").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .rememberMe()
                .tokenRepository(persistentTokenRepository()) // 配置token持久化仓库
                .tokenValiditySeconds(3600) // 过期时间，单位为秒
                .userDetailsService(myUserDetailService()) // 处理自动登录逻辑
        ;
    }
}
