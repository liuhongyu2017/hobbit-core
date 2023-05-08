package org.hobbit.core.mybatis.injector.methods;

import org.hobbit.core.mybatis.injector.HobbitSqlMethod;

/**
 * @author lhy
 * @version 1.0.0 2023/5/8
 */
public class InsertIgnore extends AbstractInsertMethod {

  public InsertIgnore() {
    super(HobbitSqlMethod.INSERT_IGNORE_ONE);
  }
}
