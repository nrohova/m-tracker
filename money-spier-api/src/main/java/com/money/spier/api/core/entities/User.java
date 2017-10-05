package com.money.spier.api.core.entities;

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
  private String userName;

  @NotNull
  @Length(min = 2, max = 15)
  @Pattern(regexp = "^[A-Za-z]+$")
  @Column(name = "first_name")
  private String firstName;

  @NotNull
  @Length(min = 2, max = 15)
  @Pattern(regexp = "^[A-Za-z]+$")
  @Column(name = "last_name")
  private String lastName;

  @Email
  @NotNull
  @Column(name = "email", unique = true)
  private String email;

  @OneToMany(mappedBy="user")
  private Set<Expense> expenses;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name="permission_user",
             joinColumns=@JoinColumn(name="permission_name"),
             inverseJoinColumns=@JoinColumn(name="username"))
  private List<Permission> permissions;

  @Column(name = "active", nullable = false, columnDefinition = "boolean default true")
  private boolean active = true;

  public String getUserName() {
    return userName;
  }

  public void setUserame(String username) {
    this.userName = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<Expense> getExpenses() {
    return expenses;
  }

  public void setExpenses(Set<Expense> expenses) {
    this.expenses = expenses;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public List<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<Permission> permissions) {
    this.permissions = permissions;
  }
}
