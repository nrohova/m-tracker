package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    return entityManager
        .createQuery("update User set active = :isActive where username = :username")
        .setParameter("username", userName)
        .setParameter("isActive", false)
        .executeUpdate();
  }

  @Override
  public User getByUserName(String userName) {
    return entityManager.find(User.class, userName);
  }

  @Override
  public List<User> getByUserNameOrEmail(String userName, String email) {
    return entityManager
        .createQuery("from User where username = :username or email = :email", User.class)
        .setParameter("username", userName)
        .setParameter("email", email)
        .getResultList();
  }

}
