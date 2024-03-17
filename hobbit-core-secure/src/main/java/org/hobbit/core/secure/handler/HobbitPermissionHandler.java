package org.hobbit.core.secure.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author lhy
 * @version 1.0.0 2023/4/22
 */
@RequiredArgsConstructor
public class HobbitPermissionHandler implements IPermissionHandler{

  private static final String SCOPE_CACHE_CODE = "apiScope:code:";

  private final JdbcTemplate jdbcTemplate;


  @Override
  public boolean permissionAll() {
    return false;
  }

  @Override
  public boolean hasPermission(String permission) {
    return false;
  }
}
