package com.money.spier.api.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//TODO: Check user permission using AOP
@Entity
@Table(name = "permission")
public class Permission {
  @Id
  @Column(name = "name")
  @Getter
  @Setter
  private String name;

  @Column(name = "expense_write")
  @Getter
  @Setter
  private boolean expenseWrite;

  @Column(name = "expense_read")
  @Getter
  @Setter
  private boolean expenseRead;

  @Column(name = "income_write")
  @Getter
  @Setter
  private boolean incomeWrite;

  @Column(name = "income_read")
  @Getter
  @Setter
  private boolean incomeRead;
}
