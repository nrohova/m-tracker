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
public class UserRetrieveFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRetrieveFacade.class);

  @Autowired
  private UserRepository repository;

  @Autowired
  private ErrorState errorState;

  public User retrieve(String userName) {
    LOGGER.info(String.format("retrieving user '%s'", userName));

    if (!validate(userName)) {
      throw new ValidationException();
    }

    User user = repository.getByUserName(userName);
    if (user == null) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    LOGGER.info("retrieved");

    return user;
  }

  private boolean validate(String userName) {
    ValidationRules.checkUserName(userName, errorState);

    return !errorState.hasErrors();
  }
}
