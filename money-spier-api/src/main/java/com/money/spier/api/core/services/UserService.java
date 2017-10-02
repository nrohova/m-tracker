package com.money.spier.api.core.services;

import com.money.spier.api.core.User;
import com.money.spier.api.core.exceptions.ConflictException;
import com.money.spier.api.core.exceptions.NotFoundException;
import com.money.spier.api.infrastructure.database.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserRepository repository;

  public void create(User user) {
    LOGGER.info(String.format("creating user '%s'", user.getUserName()));

    if (repository.getByUserName(user.getUserName()) != null) {
      throw new ConflictException(
          String.format("User with username '%s' already exist", user.getUserName()));
    }

    repository.create(user);
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

    if (repository.delete(userName) == 0) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    LOGGER.info("deleted");
  }

  public void update(String userName, User user) {
    LOGGER.info(String.format("updating user '%s'", user.getUserName()));

    if (repository.getByUserName(userName) == null) {
      throw new NotFoundException(
          String.format("User with username '%s' does not exist", userName));
    }

    repository.update(user);
    LOGGER.info("updated");
  }

}
