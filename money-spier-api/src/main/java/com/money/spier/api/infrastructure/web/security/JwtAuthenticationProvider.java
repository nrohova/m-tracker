package com.money.spier.api.infrastructure.web.security;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      JwtAuthenticationProvider.class);
  private static final Pattern ACCESS_TOKEN_PATTERN =
      Pattern.compile("Bearer ([0-9A-Za-z.\\-_]*)");

  @Override
  public boolean verify(String authorizationHeader) {
    if (authorizationHeader == null) {
      LOGGER.warn("no authorization header");
      return false;
    }

    Matcher matcher = ACCESS_TOKEN_PATTERN.matcher(authorizationHeader);
    if (!matcher.matches()) {
      LOGGER.warn("access token does not match pattern");
      return false;
    }

    SignedJWT jwt;
    try {
      jwt = SignedJWT.parse(matcher.group(1));
    } catch (ParseException e) {
      LOGGER.warn("incorrect access token format");
      return false;
    }

    JSONObject payload = jwt.getPayload().toJSONObject();
    if (payload == null) {
      LOGGER.warn("no payload");
      return false;
    }

    JWTClaimsSet claims;
    try {
      claims = jwt.getJWTClaimsSet();
    } catch (ParseException e) {
      LOGGER.warn("unable to parse claims");
      return false;
    }

    Date expirationTime = claims.getExpirationTime();
    if (expirationTime == null || new Date().after(expirationTime)) {
      LOGGER.warn("access token has expired");
      return false;
    }

    return true;
  }
}
