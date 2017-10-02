package com.money.spier.api.core.facades;

import com.money.spier.api.core.Expense;
import com.money.spier.api.core.User;
import com.money.spier.api.core.ValidationRules;
import com.money.spier.api.core.exceptions.NotFoundException;
import com.money.spier.api.infrastructure.database.ExpenseRepository;
import com.money.spier.api.infrastructure.database.UserRepository;
import com.money.spier.api.infrastructure.web.errors.ErrorState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;

@Component
@Scope("prototype")
public class ExpenseCreateFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseCreateFacade.class);

  @Autowired
  private ExpenseRepository expenseRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ErrorState errorState;

  public void create(String userName, Expense expense) {
    LOGGER.info(String.format("creating new expense for %s", userName));

    if (!validate(expense) || !validate(userName)) {
      throw new ValidationException();
    }

    User user = userRepository.getByUserName(userName);
    if (user == null) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    expense.setUser(user);
    expenseRepository.create(expense);
    LOGGER.info("created");
  }

  private boolean validate(Expense expense) {
    return !errorState.hasErrors();
  }

  private boolean validate(String userName) {
    ValidationRules.checkUserName(userName, errorState);

    return !errorState.hasErrors();
  }
}
