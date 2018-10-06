package com.marry.inner.config;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.marry.inner.intercept.LoginInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer{

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginInterceptor()).
                addPathPatterns(getIncludePathPatterns());
    }
	
	@Bean
	LoginInterceptor getLoginInterceptor() {
		return new LoginInterceptor();
	}
	
	/**
     * 需要用户和服务认证判断的路径
     * @return
     */
    private ArrayList<String> getIncludePathPatterns() {
        ArrayList<String> list = new ArrayList<>();
        String[] urls = {
                "/sys/**",
        };
        Collections.addAll(list, urls);
        return list;
    }
}
