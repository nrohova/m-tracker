package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.entities.Expense;
import java.util.List;

public interface ExpenseRepository {

  void create(Expense expense);

  int delete(String expenseId);

  List<Expense> getExpensesByUserName(String userName);

}
