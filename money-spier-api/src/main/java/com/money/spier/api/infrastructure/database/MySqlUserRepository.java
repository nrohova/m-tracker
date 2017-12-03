package com.money.spier.api.infrastructure.database;

import com.money.spier.api.core.entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public final class MySqlUserRepository implements UserRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(MySqlUserRepository.class);

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
    try {
      return entityManager
          .createQuery("from User where username = :username and active = :isActive", User.class)
          .setParameter("username", userName)
          .setParameter("isActive", true)
          .getSingleResult();
    } catch (NoResultException ex) {
      LOGGER.info(String.format("%s not found: %s", userName, ex.getMessage()));
      return null;
    }
  }

  @Override
  public List<User> getByUserNameOrEmail(String userName, String email) {
    return entityManager
        .createQuery("from User where username = :username or email = :email and active = :isActive", User.class)
        .setParameter("username", userName)
        .setParameter("email", email)
        .setParameter("isActive", true)
        .getResultList();
  }

}
