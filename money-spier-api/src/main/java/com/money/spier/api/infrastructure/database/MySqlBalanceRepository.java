package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.entities.Balance;
import com.money.spier.api.core.entities.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public final class MySqlBalanceRepository implements BalanceRepository {

  @PersistenceContext
  private EntityManager entityManager;


  @Override
  public void create(Balance balance) {
    entityManager.persist(balance);
  }

  @Override
  public void update(Balance balance) {
    entityManager.merge(balance);
  }

  @Override
  public Balance retrieve(User user) {
    return entityManager.createQuery("from Balance where user = :user", Balance.class)
        .setParameter("user", user).getSingleResult();
  }
}
