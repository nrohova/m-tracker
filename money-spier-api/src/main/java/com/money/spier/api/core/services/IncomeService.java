package com.money.spier.api.core.services;

import com.money.spier.api.core.entities.Balance;
import com.money.spier.api.core.entities.Income;
import com.money.spier.api.core.entities.User;
import com.money.spier.api.core.exceptions.ConflictException;
import com.money.spier.api.core.exceptions.NotFoundException;
import com.money.spier.api.infrastructure.database.BalanceRepository;
import com.money.spier.api.infrastructure.database.IncomeRepository;
import com.money.spier.api.infrastructure.database.UserRepository;
import java.math.BigDecimal;
import java.util.Set;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeService {

  private static final Logger LOGGER = LoggerFactory.getLogger(IncomeService.class);

  @Autowired
  private IncomeRepository incomeRepository;

  @Autowired
  private UserRepository userRepository;


  @Autowired
  private BalanceRepository balanceRepository;

  public long create(String userName, Income income) {
    LOGGER.info(String.format("creating new income for %s", userName));

    if (!validation(userName)) {
      throw new ValidationException("Invalid username");
    }

    User user = userRepository.getByUserName(userName);
    if (user == null) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    income.setUser(user);
    incomeRepository.create(income);
    balanceRepository.update(
        addIncomeToBalance(balanceRepository.retrieve(user), income));

    LOGGER.info("created");

    return income.getId();
  }

  public Set<Income> retrieve(String userName) {
    LOGGER.info(String.format("retrieving new income for %s", userName));

    User user = userRepository.getByUserName(userName);
    if (user == null) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    LOGGER.info("retrieved");

    return user.getIncomes();
  }

  public void delete(long incomeId) {
    LOGGER.info(String.format("deleting income '%s'", incomeId));

    Income income = incomeRepository.retrieve(incomeId);
    if (income == null) {
      throw new NotFoundException(
          String.format("Income '%s' does not exist", incomeId));
    }

    User user = income.getUser();
    balanceRepository.update(
        subtractIncomeFromBalance(balanceRepository.retrieve(user), income));

    incomeRepository.delete(incomeId);

    LOGGER.info("deleted");
  }

  private Balance addIncomeToBalance(Balance balance, Income income) {
    BigDecimal total = BigDecimal.valueOf(balance.getTotal());
    BigDecimal incomeAmount = BigDecimal.valueOf(income.getAmount());
    balance.setTotal(total.add(incomeAmount).doubleValue());
    return balance;
  }

  private Balance subtractIncomeFromBalance(Balance balance, Income income) {
    BigDecimal total = BigDecimal.valueOf(balance.getTotal());
    BigDecimal incomeAmount = BigDecimal.valueOf(income.getAmount());
    double newTotal = total.subtract(incomeAmount).doubleValue();

    if (newTotal < 0) {
      throw new ConflictException("Cannot delete old income.");
    }

    balance.setTotal(newTotal);
    return balance;
  }

  private boolean validation(String username) {
    return (username != null) && !username.isEmpty()
        && (username.length() >= 2) && (username.length() <= 30);
  }

}
