package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.entities.Expense;
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
  public long create(Expense expense) {
    entityManager.persist(expense);

    return expense.getId();
  }

  @Override
  public int delete(long expenseId) {
    return entityManager.createQuery("delete from Expense where id = :expenseId")
        .setParameter("expenseId", expenseId).executeUpdate();
  }

  @Override
  public Expense retrieve(long expenseId) {
    return entityManager.find(Expense.class, expenseId);
  }

}
