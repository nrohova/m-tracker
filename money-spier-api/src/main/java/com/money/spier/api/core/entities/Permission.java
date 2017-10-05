package com.money.spier.api.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_permission")
public class Permission {
  @Id
  @Column(name = "permission_name")
  private String permissionName;

  @Column(name = "write_permission")
  private boolean writePermission;

  @Column(name = "read_permission")
  private boolean readPermission;

  public Permission() {
  }

  public String getPermissionName() {
    return permissionName;
  }

  public void setPermissionName(String permissionName) {
    this.permissionName = permissionName;
  }

  public boolean isReadPermission() {
    return readPermission;
  }

  public void setReadPermission(boolean readPermission) {
    this.readPermission = readPermission;
  }

  public boolean isWritePermission() {
    return writePermission;
  }

  public void setWritePermission(boolean writePermission) {
    this.writePermission = writePermission;
  }
}
