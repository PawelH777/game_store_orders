package org.example.ordergames.infrastructure.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
public class AuthFilterConfig {

    @Bean
    public FilterRegistrationBean<GenericFilterBean> jwtFilter() {
        final FilterRegistrationBean<GenericFilterBean> filter = new FilterRegistrationBean<>();
        filter.setFilter(new JwtFilter());
        // provide endpoints which needs to be restricted.
        // All Endpoints would be restricted if unspecified
        filter.addUrlPatterns("/orders", "/orders/*");
        return filter;
    }
}
