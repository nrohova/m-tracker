package com.money.spier.api.core.services;

import com.money.spier.api.core.Expense;
import com.money.spier.api.core.User;
import com.money.spier.api.core.exceptions.NotFoundException;
import com.money.spier.api.infrastructure.database.ExpenseRepository;
import com.money.spier.api.infrastructure.database.UserRepository;
import java.util.List;
import java.util.UUID;
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


  public String create(String userName, Expense expense) {
    LOGGER.info(String.format("creating new expense for %s", userName));

    if (!validation(userName)) {
      throw new ValidationException("Invalid username");
    }

    User user = userRepository.getByUserName(userName);
    if (user == null || !user.isActive()) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    String expenseId = UUID.randomUUID().toString();
    expense.setUser(user);
    expense.setId(expenseId);
    expenseRepository.create(expense);

    LOGGER.info("created");

    return expenseId;
  }

  public List<Expense> retrieve(String userName) {
    LOGGER.info(String.format("retrieving new expense for %s", userName));

    List<Expense> expenses = expenseRepository.getExpensesByUserName(userName);

    LOGGER.info("retrieved");

    return expenses;
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
