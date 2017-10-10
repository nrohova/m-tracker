package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.entities.Income;

public interface IncomeRepository {

  void create(Income income);

  int delete(String incomeId);

}
