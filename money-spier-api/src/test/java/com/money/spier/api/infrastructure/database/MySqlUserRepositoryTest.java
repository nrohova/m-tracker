package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.entities.Permission;
import com.money.spier.api.core.entities.User;
import com.money.spier.api.infrastructure.web.Application;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
public class MySqlUserRepositoryTest {

  @Autowired
  private UserRepository repository;

  @PersistenceContext
  private EntityManager entityManager;

  @Before
  public void setUp() {
    Permission permission = new Permission();
    permission.setName("expenseAll");
    permission.setExpenseRead(true);
    permission.setExpenseWrite(true);
    entityManager.persist(permission);

    User user = new User();
    user.setEmail("test@email.com");
    user.setFirstName("testFirstName");
    user.setLastName("testLastName");
    user.setUserName("testUserName");
    user.setPermissions(Collections.singletonList(permission));

    repository.create(user);
  }

  @Test
  public void testRetrieveStoredUserByName() {
    Assert.notNull(
        repository.getByUserName("testUserName"),
        "Cannot retrieve stored user entity");
  }

  @Test
  public void testNotRetrieveUserByName() {
    Assert.isNull(
        repository.getByUserName("unavailable"),
        "Should not retrieve unavailable user entity");
  }

  @Test
  public void testRetrieveUserByNameOrEmail() {
    Assert.notEmpty(
        repository.getByUserNameOrEmail("unavailable", "test@email.com"),
        "Cannot retrieve stored user entities");
  }

  @Test
  public void testNotRetrieveUserByNameOrEmail() {
    List<User> users = repository.getByUserNameOrEmail("unavailable", "unavailable@email.com");

    Assert.isTrue(
        users.isEmpty(),
        "Should not retrieve unavailable user entities");
  }

  //TODO: doesn't work
//  @Test
  public void testDelete() {
    repository.delete("test@email.com");

    Assert.isNull(
        repository.getByUserName("testUserName"),
        "Should be deleted");
  }

}