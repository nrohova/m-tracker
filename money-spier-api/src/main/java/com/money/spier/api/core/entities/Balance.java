package com.money.spier.api.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "balance")
public class Balance {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Getter
  @Setter
  private long id;

  @Digits(integer = Integer.MAX_VALUE, fraction = 2)
  @DecimalMin(value = "0.0")
  @Column(name = "total", columnDefinition = "double default 0")
  @Getter
  @Setter
  private Double total = 0.0;

  @OneToOne
  @JoinColumn(name = "user")
  @NotNull
  @Getter
  @Setter
  private User user;

}
