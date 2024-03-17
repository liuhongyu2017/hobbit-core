package org.hobbit.core.tool.constant;

/**
 * 系统默认角色
 *
 * @author lhy
 * @version 1.0.0 2024/3/16
 */
public interface RoleConstant {

  String ADMINISTRATOR = "administrator";

  String HAS_ROLE_ADMINISTRATOR = "hasRole('" + ADMINISTRATOR + "')";

  String ADMIN = "admin";

  String HAS_ROLE_ADMIN = "hasAnyRole('" + ADMINISTRATOR + "', '" + ADMIN + "')";

  String USER = "user";

  String HAS_ROLE_USER = "hasRole('" + USER + "')";

  String TEST = "test";

  String HAS_ROLE_TEST = "hasRole('" + TEST + "')";
}
