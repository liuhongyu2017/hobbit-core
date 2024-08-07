package org.hobbit.core.secure.auth;

import org.hobbit.core.auth.HobbitUser;
import org.hobbit.core.auth.util.AuthUtil;
import org.hobbit.core.secure.handler.IPermissionHandler;
import org.hobbit.core.tool.constant.RoleConstant;
import org.hobbit.core.tool.utils.CollectionUtil;
import org.hobbit.core.tool.utils.DateUtil;
import org.hobbit.core.tool.utils.Func;
import org.hobbit.core.tool.utils.SpringUtil;
import org.hobbit.core.tool.utils.StringUtil;

/**
 * 权限判断表达式
 *
 * @author lhy
 * @version 1.0.0 2023/4/21
 */
public class AuthFun {

  /**
   * 权限校验处理器
   */
  private static IPermissionHandler permissionHandler;

  private static IPermissionHandler getPermissionHandler() {
    if (permissionHandler == null) {
      permissionHandler = SpringUtil.getBean(IPermissionHandler.class);
    }
    return permissionHandler;
  }

  /**
   * 判断角色是否具有接口权限 判断登陆用户是否拥有这个接口的权限
   *
   * @return {boolean}
   */
  public boolean permissionAll() {
    return getPermissionHandler().permissionAll();
  }

  /**
   * 判断角色是否具有接口权限
   *
   * @param permission 权限编号
   * @return {boolean}
   */
  public boolean hasPermission(String permission) {
    return getPermissionHandler().hasPermission(permission);
  }

  /**
   * 放行所有请求
   *
   * @return {boolean}
   */
  public boolean permitAll() {
    return true;
  }

  /**
   * 只有超管角色才可访问
   *
   * @return {boolean}
   */
  public boolean denyAll() {
    return hasRole(RoleConstant.ADMIN);
  }

  /**
   * 是否已授权
   *
   * @return {boolean}
   */
  public boolean hasAuth() {
    return Func.isNotEmpty(AuthUtil.getUser());
  }

  /**
   * 是否有时间授权
   *
   * @param start 开始时间
   * @param end   结束时间
   * @return {boolean}
   */
  public boolean hasTimeAuth(Integer start, Integer end) {
    Integer hour = DateUtil.hour();
    return hour >= start && hour <= end;
  }

  /**
   * 判断是否有该角色权限
   *
   * @param role 单角色
   * @return {boolean}
   */
  public boolean hasRole(String role) {
    return hasAnyRole(role);
  }

  /**
   * 判断是否具有所有角色权限
   *
   * @param role 角色集合
   * @return {boolean}
   */
  public boolean hasAllRole(String... role) {
    for (String r : role) {
      if (!hasRole(r)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 判断是否有该角色权限
   *
   * @param role 角色集合
   * @return {boolean}
   */
  public boolean hasAnyRole(String... role) {
    HobbitUser user = AuthUtil.getUser();
    if (user == null) {
      return false;
    }
    String userRole = user.getRoleName();
    if (StringUtil.isBlank(userRole)) {
      return false;
    }
    String[] roles = Func.toStrArray(userRole);
    for (String r : role) {
      if (CollectionUtil.contains(roles, r)) {
        return true;
      }
    }
    return false;
  }
}
