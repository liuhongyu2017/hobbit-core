package org.hobbit.core.mybatis.injector.methods;

import org.hobbit.core.mybatis.injector.HobbitSqlMethod;

/**
 * 插入一条数据（选择字段插入）
 * <p>
 * 表示插入替换数据，需求表中有PrimaryKey，或者unique索引，如果数据库已经存在数据，则用新数据替换，如果没有数据效果则和insert into一样；
 * </p>
 *
 * @author lhy
 * @version 1.0.0 2023/5/8
 */
public class Replace extends AbstractInsertMethod {

  public Replace() {
    super(HobbitSqlMethod.REPLACE_ONE);
  }
}
