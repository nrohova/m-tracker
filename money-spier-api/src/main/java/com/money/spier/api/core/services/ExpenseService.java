package com.money.spier.api.core.services;

import com.money.spier.api.core.entities.Expense;
import com.money.spier.api.core.entities.User;
import com.money.spier.api.core.exceptions.NotFoundException;
import com.money.spier.api.infrastructure.database.ExpenseRepository;
import com.money.spier.api.infrastructure.database.UserRepository;
import java.util.Set;
import javax.validation.ValidationException;
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


  public long create(String userName, Expense expense) {
    LOGGER.info(String.format("creating new expense for %s", userName));

    if (!validation(userName)) {
      throw new ValidationException("Invalid username");
    }

    User user = userRepository.getByUserName(userName);
    if (user == null || !user.isActive()) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    expense.setUser(user);
    expenseRepository.create(expense);
    LOGGER.info("created");

    return expense.getId();
  }

  public Set<Expense> retrieve(String userName) {
    LOGGER.info(String.format("retrieving new expense for %s", userName));

    User user = userRepository.getByUserName(userName);
    if (user == null || !user.isActive()) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    LOGGER.info("retrieved");

    return user.getExpenses();
  }

  public void delete(String expenseId) {
    LOGGER.info(String.format("deleting expense '%s'", expenseId));

    if (expenseRepository.delete(expenseId) == 0) {
      throw new NotFoundException(
          String.format("Expense '%s' does not exist", expenseId));
    }

    LOGGER.info("deleted");
  }

  private boolean validation(String username) {
    return (username != null) && !username.isEmpty()
        && (username.length() >= 2) && (username.length() <= 30);
  }

}
