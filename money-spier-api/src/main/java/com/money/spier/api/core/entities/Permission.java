package com.money.spier.api.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

//TODO: Check user permission using AOP
@Entity
@Data
@Table(name = "permission")
public class Permission {
  @Id
  @Column(name = "name")
  private String name;

  @Column(name = "expense_write")
  private boolean expenseWrite;

  @Column(name = "expense_read")
  private boolean expenseRead;

  @Column(name = "income_write")
  private boolean incomeWrite;

  @Column(name = "income_read")
  private boolean incomeRead;
}
