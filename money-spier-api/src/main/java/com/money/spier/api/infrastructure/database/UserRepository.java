package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.User;

public interface UserRepository {

  void create(User user);

  void update(User user);

  int delete(String userName);

  User getByUserName(String userName);
}
