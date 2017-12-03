package com.money.spier.api.core.services;

import com.money.spier.api.core.entities.Balance;
import com.money.spier.api.core.entities.User;
import com.money.spier.api.core.exceptions.ConflictException;
import com.money.spier.api.core.exceptions.NotFoundException;
import com.money.spier.api.infrastructure.database.BalanceRepository;
import com.money.spier.api.infrastructure.database.UserRepository;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

  @Mock
  private UserRepository repository;
  @Mock
  private BalanceRepository balanceRepository;

  @InjectMocks
  private UserService userService;

  private User user;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    user = new User();
    user.setUserName("John");
    user.setLastName("Walker");
    user.setEmail("john@email.com");
  }

  @Test
  public void testCreateUserWhenNotExist() {
    Mockito.when(repository.getByUserNameOrEmail("John", "john@email.com"))
        .thenReturn(null);

    userService.create(user);

    Mockito.verify(repository, Mockito.times(1)).create(Mockito.eq(user));
    Mockito.verify(balanceRepository, Mockito.times(1)).create(Mockito.any(Balance.class));
  }

  @Test(expected = ConflictException.class)
  public void testCannotCreateUserWhenExist() {
    Mockito.when(repository.getByUserNameOrEmail("John", "john@email.com"))
        .thenReturn(Collections.singletonList(user));

    userService.create(user);
  }

  @Test(expected = NotFoundException.class)
  public void testCannotRetrieveUserWhenNotExist() {
    Mockito.when(repository.getByUserName("John"))
        .thenReturn(null);

    userService.retrieve("John");
  }

  @Test
  public void testRetrieveUserWhenExist() {
    Mockito.when(repository.getByUserName("John"))
        .thenReturn(user);

    Assert.assertEquals("Should return existed user", userService.retrieve("John"), user);
  }

  @Test
  public void testDeleteUserWhenExist() {
    Mockito.when(repository.getByUserName("John"))
        .thenReturn(user);

    userService.delete("John");

    Mockito.verify(repository, Mockito.times(1)).delete(Mockito.eq("John"));
  }

  @Test(expected = NotFoundException.class)
  public void testCannotDeleteUserWhenNotExist() {
    Mockito.when(repository.getByUserName("John"))
        .thenReturn(null);

    userService.delete("John");
  }

  @Test
  public void testUpdateUserWhenExist() {
    Mockito.when(repository.getByUserName("John"))
        .thenReturn(user);

    userService.update("John", user);

    Mockito.verify(repository, Mockito.times(1)).update(Mockito.eq(user));
  }

  @Test(expected = NotFoundException.class)
  public void testCannotUpdateUserWhenNotExist() {
    Mockito.when(repository.getByUserName("John"))
        .thenReturn(null);

    userService.update("John", user);
  }

}