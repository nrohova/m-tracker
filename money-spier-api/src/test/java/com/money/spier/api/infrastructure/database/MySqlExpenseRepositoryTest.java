package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.entities.Expense;
import com.money.spier.api.core.entities.Permission;
import com.money.spier.api.core.entities.User;
import com.money.spier.api.infrastructure.web.Application;
import java.time.LocalDateTime;
import java.util.Collections;
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
public class MySqlExpenseRepositoryTest {

  @Autowired
  private ExpenseRepository repository;

  @PersistenceContext
  private EntityManager entityManager;

  private long expenseId;

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
    entityManager.persist(user);

    Expense expense = new Expense();
    expense.setAmount(130.);
    expense.setGroup("newGroup");
    expense.setUser(user);
    expense.setCreationDate(LocalDateTime.now());
    expenseId = repository.create(expense);
  }

  @Test
  public void testRetrieveStoredExpense() {
    Assert.notNull(repository.retrieve(expenseId), "Should be stored");
  }

  //TODO: does not work
  @Test
  public void testDelete() {
    repository.delete(expenseId);

    Assert.isNull(repository.retrieve(expenseId), "Should be deleted");
  }
}