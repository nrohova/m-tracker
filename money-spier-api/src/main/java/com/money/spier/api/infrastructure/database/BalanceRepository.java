package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.entities.Balance;
import com.money.spier.api.core.entities.User;

public interface BalanceRepository {

  void create(Balance balance);

  void update(Balance balance);

  Balance retrieve(User user);
}
