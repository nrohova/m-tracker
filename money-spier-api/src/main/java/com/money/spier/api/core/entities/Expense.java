package com.money.spier.api.core.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "expense")
public final class Expense {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne
  @JoinColumn(name = "user")
  private User user;

  @NotNull
  @Digits(integer = Integer.MAX_VALUE, fraction = 2)
  @DecimalMin(value = "0.0")
  @Column(name = "amount")
  private Double amount;

  @NotNull
  @Column(name = "group_name")
  private String group;

  @Column(name = "creation_date", nullable = false)
  private LocalDateTime creationDate;

  @Column(name = "comment")
  private String comment;

}
