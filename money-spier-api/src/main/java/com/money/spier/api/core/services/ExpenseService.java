package com.money.spier.api.core.services;

import com.money.spier.api.core.Expense;
import com.money.spier.api.core.User;
import com.money.spier.api.core.exceptions.NotFoundException;
import com.money.spier.api.infrastructure.database.ExpenseRepository;
import com.money.spier.api.infrastructure.database.UserRepository;
import com.money.spier.api.infrastructure.web.errors.ErrorState;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseService.class);

  @Autowired
  private ExpenseRepository expenseRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ErrorState errorState;

  public void create(String userName, Expense expense) {
    LOGGER.info(String.format("creating new expense for %s", userName));

    User user = userRepository.getByUserName(userName);
    if (user == null) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    expense.setUser(user);
    expenseRepository.create(expense);
    LOGGER.info("created");
  }

  public List<Expense> retrieve(String userName) {
    LOGGER.info(String.format("retrieving new expense for %s", userName));

    List<Expense> expenses = expenseRepository.getExpensesByUserName(userName);

    LOGGER.info("retrieved");

    return expenses;
  }

}
