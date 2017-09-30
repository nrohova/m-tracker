package com.money.spier.api.infrastructure.web.errors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ErrorState {
  public ErrorState() {

  }

  private final Map<String, Collection<String>> errors = new HashMap<>();

  public boolean hasErrors() {
    return !this.errors.isEmpty();
  }

  public Map<String, Collection<String>> getErrors() {
    return this.errors;
  }

  /**
   * Adds a field error message.
   */
  public void addError(String field, String message) {
    this.errors.computeIfAbsent(field, k -> new ArrayList<>()).add(message);
  }
}
