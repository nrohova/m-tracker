package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.Expense;

public interface ExpenseRepository {

  void create(Expense expense);

  int delete(String expenseId);

}
