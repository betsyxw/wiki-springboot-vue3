package com.xuwen.wiki.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author:xuwen
 * Created on 2021/8/18
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
//    //spring注入
//    @Resource
//    LogInterceptor logInterceptor;
//
//
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(logInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(
//                        "/test/**",
//                        "/redis/**",
//                        "/user/login",
//                        "/category/all",
//                        "/ebook/list",
//                        "/doc/all/**",
//                        "/doc/vote/**",
//                        "/doc/find-content/**",
//                        "/ebook-snapshot/**"
//                );
//
//
//    }
}