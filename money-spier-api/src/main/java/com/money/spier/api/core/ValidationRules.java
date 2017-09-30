package com.money.spier.api.core;

import com.money.spier.api.infrastructure.web.errors.ErrorState;
import java.util.regex.Pattern;

public final class ValidationRules {

  private static final String REQUIRED_FIELD = "Required field cannot be left blank.";
  private static final String LENGTH_OF_FIELD = "Value length should be in range [%d, %d]";
  private static final String WORD_PATTERN = "^[a-zA-Z]+$";
  private static final String USERNAME_PATTERN = "^[_a-zA-Z0-9]+$";
  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
      + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  private static Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
  private static Pattern wordPattern = Pattern.compile(WORD_PATTERN);
    private static Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN);

  private ValidationRules() {

  }

  public static void checkUserName(String userName, ErrorState errorState) {
    if (isEmptyString(userName)) {
      errorState.addError("userName", REQUIRED_FIELD);
    } else if (isStringLengthNotInRange(userName, 4, 30)) {
      errorState.addError("userName", String.format(LENGTH_OF_FIELD, 4, 30));
    } else if (!usernamePattern.matcher(userName).matches()) {
      errorState.addError("userName", "invalid value");
    }
  }

  public static void checkUserFirstName(String firstName, ErrorState errorState) {
    if (isEmptyString(firstName)) {
      errorState.addError("firstName", REQUIRED_FIELD);
    } else if (isStringLengthNotInRange(firstName, 2, 15)) {
      errorState.addError("firstName", String.format(LENGTH_OF_FIELD, 2, 15));
    } else if (!wordPattern.matcher(firstName).matches()) {
      errorState.addError("firstName", "value should contains only letters");
    }
  }

  public static void checkUserLastName(String lastName, ErrorState errorState) {
    if (isEmptyString(lastName)) {
      errorState.addError("lastName", REQUIRED_FIELD);
    } else if (isStringLengthNotInRange(lastName, 2, 15)) {
      errorState.addError("lastName", String.format(LENGTH_OF_FIELD, 2, 15));
    } else if (!wordPattern.matcher(lastName).matches()) {
      errorState.addError("lastName", "value should contains only letters");
    }
  }

  public static void checkUserEmail(String email, ErrorState errorState) {
    if (isEmptyString(email)) {
      errorState.addError("email", REQUIRED_FIELD);
    } else if (!emailPattern.matcher(email).matches())  {
      errorState.addError("email", "invalid value");
    }
  }

  private static boolean isEmptyString(String value) {
    return (value == null) || value.isEmpty();
  }

  private static boolean isStringLengthNotInRange(String value, int from, int to) {
    return (value.length() < from) || (value.length() > to);
  }
}
