package com.money.spier.api.core.exceptions;

public class NoPermissions extends RuntimeException {
  public NoPermissions(String message) {
    super(message);
  }
}
