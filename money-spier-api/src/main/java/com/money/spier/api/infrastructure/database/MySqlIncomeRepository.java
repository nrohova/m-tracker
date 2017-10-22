package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.entities.Income;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public final class MySqlIncomeRepository implements IncomeRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void create(Income income) {
    entityManager.persist(income);
  }

  @Override
  public int delete(long incomeId) {
    return entityManager.createQuery("delete from Income where id = :incomeId")
        .setParameter("incomeId", incomeId).executeUpdate();
  }

  @Override
  public Income retrieve(long incomeId) {
    return entityManager.find(Income.class, incomeId);
  }

}
