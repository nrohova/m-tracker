package com.money.spier.api.infrastructure.web.security;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
class Authentication {

  private AuthenticationProvider provider;

  Authentication(AuthenticationProvider provider) {
    this.provider = provider;
  }

  boolean authenticate(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader == null) {
      return false;
    }

    return provider.verify(authorizationHeader);
  }
}
