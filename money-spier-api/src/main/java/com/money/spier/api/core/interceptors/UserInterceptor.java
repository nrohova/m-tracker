package com.money.spier.api.core.interceptors;

import com.money.spier.api.core.entities.User;
import com.money.spier.api.infrastructure.database.UserRepository;
import java.io.Serializable;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

//TODO: Is it appropriate way to implement trigger?
@Component
public class UserInterceptor extends EmptyInterceptor {

  @Autowired
  @Lazy
  private UserRepository userRepository;

  @Override
  public boolean onSave(Object entity, Serializable id,
      Object[] state, String[] propertyNames, Type[] types) {
    User user = UserTranslator.from(entity);
    if (user != null) {
      userRepository.update(user);
    }

    return false;
  }
}
