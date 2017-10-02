package com.money.spier.api.core.facades;

import com.money.spier.api.core.User;
import com.money.spier.api.core.ValidationRules;
import com.money.spier.api.core.exceptions.NotFoundException;
import com.money.spier.api.infrastructure.database.UserRepository;
import com.money.spier.api.infrastructure.web.errors.ErrorState;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UserUpdateFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserUpdateFacade.class);

  @Autowired
  private UserRepository repository;

  @Autowired
  private ErrorState errorState;

  public void update(String userName, User user) {
    LOGGER.info(String.format("updating user '%s'", user.getUserName()));

    if (!validate(user) || !validateUserName(userName)) {
      throw new ValidationException();
    }

    if (repository.getByUserName(userName) == null) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    repository.update(user);
    LOGGER.info("updated");
  }

  private boolean validate(User user) {
    if (user.getUserName() != null) {
      ValidationRules.checkUserName(user.getUserName(), errorState);
    }

    if (user.getFirstName() != null) {
      ValidationRules.checkUserFirstName(user.getFirstName(), errorState);
    }

    if (user.getLastName() != null) {
      ValidationRules.checkUserLastName(user.getLastName(), errorState);
    }

    if (user.getEmail() != null) {
      ValidationRules.checkUserEmail(user.getEmail(), errorState);
    }

    return !errorState.hasErrors();
  }

  private boolean validateUserName(String userName) {
    ValidationRules.checkUserName(userName, errorState);
    return !errorState.hasErrors();
  }
}
