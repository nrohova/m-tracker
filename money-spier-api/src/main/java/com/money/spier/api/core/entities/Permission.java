package com.money.spier.api.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isExpenseWrite() {
    return expenseWrite;
  }

  public void setExpenseWrite(boolean expenseWrite) {
    this.expenseWrite = expenseWrite;
  }

  public boolean isExpenseRead() {
    return expenseRead;
  }

  public void setExpenseRead(boolean expenseRead) {
    this.expenseRead = expenseRead;
  }

  public boolean isIncomeWrite() {
    return incomeWrite;
  }

  public void setIncomeWrite(boolean incomeWrite) {
    this.incomeWrite = incomeWrite;
  }

  public boolean isIncomeRead() {
    return incomeRead;
  }

  public void setIncomeRead(boolean incomeRead) {
    this.incomeRead = incomeRead;
  }
}
