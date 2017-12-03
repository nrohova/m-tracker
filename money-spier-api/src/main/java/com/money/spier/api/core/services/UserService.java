package com.money.spier.api.core.services;

import com.money.spier.api.core.entities.Balance;
import com.money.spier.api.core.entities.User;
import com.money.spier.api.core.exceptions.ConflictException;
import com.money.spier.api.core.exceptions.NotFoundException;
import com.money.spier.api.infrastructure.database.BalanceRepository;
import com.money.spier.api.infrastructure.database.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserRepository repository;

  @Autowired
  private BalanceRepository balanceRepository;

  public void create(User user) {
    LOGGER.info(String.format("creating user '%s'", user.getUserName()));

    List<User> users = repository.getByUserNameOrEmail(user.getUserName(), user.getEmail());
    if (users != null) {
      if (users.stream().anyMatch(u -> user.getUserName().equals(u.getUserName()))) {
        throw new ConflictException(
            String.format("User with username '%s' already exist", user.getUserName()));
      }
      if (users.stream().anyMatch(u -> user.getEmail().equals(u.getEmail()))) {
        throw new ConflictException(
            String.format("User with email '%s' already exist", user.getEmail()));
      }
    }

    repository.create(user);
    balanceRepository.create(balance(user));
    LOGGER.info("created");
  }

  public User retrieve(String userName) {
    LOGGER.info(String.format("retrieving user '%s'", userName));

    User user = repository.getByUserName(userName);
    if (user == null) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    LOGGER.info("retrieved");

    return user;
  }

  public void delete(String userName) {
    LOGGER.info(String.format("deleting user '%s'", userName));

    User storedUser = repository.getByUserName(userName);
    if (storedUser == null) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    repository.delete(userName);
    LOGGER.info("deleted");
  }

  public void update(String userName, User user) {
    LOGGER.info(String.format("updating user '%s'", user.getUserName()));

    User storedUser = repository.getByUserName(userName);
    if (storedUser == null) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    repository.update(user);
    LOGGER.info("updated");
  }

  private Balance balance(User user) {
    Balance balance = new Balance();
    balance.setUser(user);
    balance.setTotal(0.0);
    return balance;
  }

}
