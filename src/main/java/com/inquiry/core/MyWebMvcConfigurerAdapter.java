package com.inquiry.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description:
 * @Auther: liuweicheng
 * @Date: 2019-12-09 17:03
 */
@SuppressWarnings("all")
@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/**").excludePathPatterns
                ("/login", "/login.do", "/register", "/register.do","/static/**");
        super.addInterceptors(registry);
    }
}
