package com.money.spier.api.core.listeners;

import com.money.spier.api.core.entities.Expense;
import com.money.spier.api.core.entities.User;
import com.money.spier.api.infrastructure.database.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;

@Component
public class ExpenseListener {

  @Autowired
  private UserRepository repository;

  @PrePersist
  public void expensePrePersist(Expense expense) {
    User user = expense.getUser();
    user.setTotal(user.getTotal() - expense.getAmount());
    repository.update(user);
    System.out.println("Listening User Pre Persist : " + expense.getId());
  }

  public UserRepository getRepository() {
    return repository;
  }

  public void setRepository(UserRepository repository) {
    this.repository = repository;
  }
}
