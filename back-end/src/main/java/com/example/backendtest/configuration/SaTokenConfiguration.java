package com.example.backendtest.configuration;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class SaTokenConfiguration implements WebMvcConfigurer {
    //鉴权配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
    }
}
