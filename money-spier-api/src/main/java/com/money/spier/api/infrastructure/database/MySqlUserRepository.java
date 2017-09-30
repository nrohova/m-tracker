package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public final class MySqlUserRepository implements UserRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void create(User user) {
    entityManager.persist(user);
  }

  @Override
  public void update(User user) {
    entityManager.merge(user);
  }

  @Override
  public int delete(String userName) {
    return entityManager.createQuery("delete from User where userName = :userName")
        .setParameter("userName", userName).executeUpdate();
  }

  @Override
  public User getByUserName(String userName) {
    return entityManager.find(User.class, userName);
  }
}
