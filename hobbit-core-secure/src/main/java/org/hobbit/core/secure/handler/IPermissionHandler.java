package org.hobbit.core.secure.handler;

/**
 * 权限处理器
 *
 * @author lhy
 * @version 1.0.0 2023/4/21
 */
public interface IPermissionHandler {

  /**
   * 判断角色是否具有接口权限
   *
   * @return {boolean}
   */
  boolean permissionAll();

  /**
   * 判断角色是否具有接口权限
   *
   * @param permission 权限编号
   * @return {boolean}
   */
  boolean hasPermission(String permission);
}
