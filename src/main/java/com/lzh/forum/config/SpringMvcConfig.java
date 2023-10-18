package com.lzh.forum.config;

import com.lzh.forum.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Autowired
    LoginInterceptor loginInterceptor;

    /**
     * 配置拦截信息
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(loginInterceptor)   //添加拦截处理器,根据其逻辑进行拦截
                .addPathPatterns("/**")  //添加拦截路径
                //排除不需要拦截的路径
                .excludePathPatterns("/user/login",
                        "/user/register",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/doc.html",
                        "/images/**",
                        "/webjars/**",
                        "v2/api-docs",
                        "/configuration/ui",
                        "/configuration/security");
    }
}
