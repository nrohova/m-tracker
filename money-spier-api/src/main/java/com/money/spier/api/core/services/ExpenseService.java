package com.money.spier.api.core.services;

import com.money.spier.api.core.aspects.Authorized;
import com.money.spier.api.core.entities.Balance;
import com.money.spier.api.core.entities.Expense;
import com.money.spier.api.core.entities.User;
import com.money.spier.api.core.exceptions.ConflictException;
import com.money.spier.api.core.exceptions.NotFoundException;
import com.money.spier.api.infrastructure.database.BalanceRepository;
import com.money.spier.api.infrastructure.database.ExpenseRepository;
import com.money.spier.api.infrastructure.database.UserRepository;
import java.math.BigDecimal;
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

  @Autowired
  private BalanceRepository balanceRepository;

  @Authorized
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

    balanceRepository.update(
        subtractExpenseFromBalance(balanceRepository.retrieve(user), expense));

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

  public void delete(long expenseId) {
    LOGGER.info(String.format("deleting expense '%d'", expenseId));

    Expense expense = expenseRepository.retrieve(expenseId);
    if (expense == null) {
      throw new NotFoundException(
          String.format("Expense '%d' does not exist", expenseId));
    }

    User user = expense.getUser();
    balanceRepository.update(
        addExpenseToBalance(balanceRepository.retrieve(user), expense));
    expenseRepository.delete(expenseId);

    LOGGER.info("deleted");
  }

  private Balance addExpenseToBalance(Balance balance, Expense expense) {
    BigDecimal total = BigDecimal.valueOf(balance.getTotal());
    BigDecimal expenseAmount = BigDecimal.valueOf(expense.getAmount());
    balance.setTotal(total.add(expenseAmount).doubleValue());
    return balance;
  }

  private Balance subtractExpenseFromBalance(Balance balance, Expense expense) {
    BigDecimal total = BigDecimal.valueOf(balance.getTotal());
    BigDecimal expenseAmount = BigDecimal.valueOf(expense.getAmount());
    double newTotal = total.subtract(expenseAmount).doubleValue();

    if (newTotal < 0) {
      throw new ConflictException("Insufficient funds on the account.");
    }

    balance.setTotal(newTotal);
    return balance;
  }

  private boolean validation(String username) {
    return (username != null) && !username.isEmpty()
        && (username.length() >= 2) && (username.length() <= 30);
  }

}
