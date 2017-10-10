package com.money.spier.api.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "user")
public final class User {
  @Id
  @NotNull
  @Length(min = 4, max = 30)
  @Pattern(regexp = "^[_A-Za-z0-9]+$")
  @Column(name = "username")
  @Getter
  @Setter
  private String userName;

  @NotNull
  @Length(min = 2, max = 15)
  @Pattern(regexp = "^[A-Za-z]+$")
  @Column(name = "first_name")
  @Getter
  @Setter
  private String firstName;

  @NotNull
  @Length(min = 2, max = 15)
  @Pattern(regexp = "^[A-Za-z]+$")
  @Column(name = "last_name")
  @Getter
  @Setter
  private String lastName;

  @Email
  @NotNull
  @Column(name = "email", unique = true)
  @Getter
  @Setter
  private String email;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "user")
  @Getter
  @Setter
  private Set<Expense> expenses;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "user")
  @Getter
  @Setter
  private Set<Income> incomes;

  @NotNull
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name="user_permission",
             joinColumns=@JoinColumn(name="permission"),
             inverseJoinColumns=@JoinColumn(name="username"))
  @Getter
  @Setter
  private List<Permission> permissions;

  @Column(name = "active", nullable = false, columnDefinition = "boolean default true")
  @Getter
  @Setter
  private boolean active = true;
}
