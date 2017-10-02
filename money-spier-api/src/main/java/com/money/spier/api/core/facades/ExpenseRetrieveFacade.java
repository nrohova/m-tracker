package com.money.spier.api.core.facades;

import com.money.spier.api.core.Expense;
import com.money.spier.api.core.ValidationRules;
import com.money.spier.api.infrastructure.database.ExpenseRepository;
import com.money.spier.api.infrastructure.web.errors.ErrorState;
import java.util.List;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ExpenseRetrieveFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseRetrieveFacade.class);

  @Autowired
  private ExpenseRepository expenseRepository;

  @Autowired
  private ErrorState errorState;

  public List<Expense> retrieve(String userName) {
    LOGGER.info(String.format("retrieving new expense for %s", userName));

    if (!validate(userName)) {
      throw new ValidationException();
    }

    List<Expense> expenses = expenseRepository.getExpensesByUserName(userName);

    LOGGER.info("retrieved");

    return expenses;
  }

  private boolean validate(String userName) {
    ValidationRules.checkUserName(userName, errorState);

    return !errorState.hasErrors();
  }
}
