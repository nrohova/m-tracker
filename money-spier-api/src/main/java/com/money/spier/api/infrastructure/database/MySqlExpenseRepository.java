package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.Expense;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public final class MySqlExpenseRepository implements ExpenseRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void create(Expense expense) {
    entityManager.persist(expense);
  }

  @Override
  public int delete(String expenseId) {
    return entityManager.createQuery("delete from Expense where id = :expenseId")
        .setParameter("expenseId", expenseId).executeUpdate();
  }

}
