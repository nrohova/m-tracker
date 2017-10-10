package com.money.spier.api.core.entities;

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
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "expense")
public final class Expense {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Getter
  @Setter
  private long id;

  @ManyToOne
  @JoinColumn(name = "user")
  @Getter
  @Setter
  private User user;

  @NotNull
  @Digits(integer = Integer.MAX_VALUE, fraction = 2)
  @DecimalMin(value = "0.0")
  @Column(name = "amount")
  @Getter
  @Setter
  private Double amount;

  @NotNull
  @Column(name = "group_name")
  @Getter
  @Setter
  private String group;

  @Column(name = "creation_date", nullable = false)
  @Getter
  @Setter
  private LocalDateTime creationDate;

  @Column(name = "comment")
  @Getter
  @Setter
  private String comment;

}
