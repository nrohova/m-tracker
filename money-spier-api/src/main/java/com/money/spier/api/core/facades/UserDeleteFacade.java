package com.money.spier.api.core.facades;

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
public class UserDeleteFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserDeleteFacade.class);

  @Autowired
  private UserRepository repository;

  @Autowired
  private ErrorState errorState;

  public void delete(String userName) {
    LOGGER.info(String.format("deleting user '%s'", userName));

    if (!validate(userName)) {
      throw new ValidationException();
    }

    if (repository.delete(userName) == 0) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    LOGGER.info("deleted");
  }

  private boolean validate(String userName) {
    ValidationRules.checkUserName(userName, errorState);

    return !errorState.hasErrors();
  }
}
