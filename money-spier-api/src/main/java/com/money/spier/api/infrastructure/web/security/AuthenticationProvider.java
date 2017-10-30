package com.money.spier.api.infrastructure.web.security;

interface AuthenticationProvider {
  boolean verify(String authorizationHeader);
}
