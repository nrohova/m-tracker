package com.money.spier.api.infrastructure.web;

import com.money.spier.api.infrastructure.web.security.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

  @Bean
  public FilterRegistrationBean register(AuthenticationFilter filter) {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(filter);
    registration.addUrlPatterns("/expenses/*");
    registration.setName("authenticationFilter");

    return registration;
  }
}
