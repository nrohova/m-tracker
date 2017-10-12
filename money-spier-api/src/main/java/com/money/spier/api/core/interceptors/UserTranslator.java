package com.money.spier.api.core.interceptors;

import com.money.spier.api.core.entities.Expense;
import com.money.spier.api.core.entities.Income;
import com.money.spier.api.core.entities.User;
import com.money.spier.api.core.exceptions.ConflictException;
import java.math.BigDecimal;

final class UserTranslator {
  private UserTranslator() {

  }

  static User from(Object entity) {
    User user = null;
    if (entity instanceof Expense) {
      user = visit((Expense) entity);
    } else if (entity instanceof Income) {
      user = visit((Income) entity);
    }

    return user;
  }

  private static User visit(Expense expense) {
    User user = expense.getUser();
    BigDecimal total = BigDecimal.valueOf(user.getTotal());
    BigDecimal expenseAmount = BigDecimal.valueOf(expense.getAmount());
    double newTotal = total.subtract(expenseAmount).doubleValue();

    if (newTotal < 0) {
      throw new ConflictException("Insufficient funds on the account.");
    }

    user.setTotal(newTotal);
    return user;
  }

  private static User visit(Income income) {
    User user = income.getUser();
    BigDecimal total = BigDecimal.valueOf(user.getTotal());
    BigDecimal incomeAmount = BigDecimal.valueOf(income.getAmount());
    user.setTotal(total.add(incomeAmount).doubleValue());
    return user;
  }

}
