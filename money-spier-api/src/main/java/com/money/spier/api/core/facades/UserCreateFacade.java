package com.money.spier.api.core.facades;

import com.money.spier.api.core.User;
import com.money.spier.api.core.ValidationRules;
import com.money.spier.api.core.exceptions.ConflictException;
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
public class UserCreateFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserCreateFacade.class);

  @Autowired
  private UserRepository repository;

  @Autowired
  private ErrorState errorState;

  public void create(User user) {
    LOGGER.info(String.format("creating user '%s'", user.getUserName()));

    if (!validate(user)) {
      throw new ValidationException();
    }

    if (repository.getByUserName(user.getUserName()) != null) {
      throw new ConflictException(
          String.format("User with username '%s' already exist", user.getUserName()));
    }

    repository.create(user);
    LOGGER.info("created");
  }

  private boolean validate(User user) {
    ValidationRules.checkUserName(user.getUserName(), errorState);
    ValidationRules.checkUserFirstName(user.getFirstName(), errorState);
    ValidationRules.checkUserLastName(user.getLastName(), errorState);
    ValidationRules.checkUserEmail(user.getEmail(), errorState);

    return !errorState.hasErrors();
  }
}
