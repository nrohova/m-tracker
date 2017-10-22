package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.entities.Expense;

public interface ExpenseRepository {

  void create(Expense expense);

  int delete(long expenseId);

  Expense retrieve(long expenseId);

}
