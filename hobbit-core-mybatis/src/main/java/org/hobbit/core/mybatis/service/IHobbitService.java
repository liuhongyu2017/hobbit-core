package org.hobbit.core.mybatis.service;

import java.util.Collection;
import org.hobbit.core.mybatis.base.IBaseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * hobbit service
 *
 * @author lhy
 * @version 1.0.0 2023/5/8
 */
public interface IHobbitService<T> extends IBaseService<T> {

  /**
   * 插入如果中已经存在相同的记录，则忽略当前新数据
   *
   * @param entity entity
   * @return 是否成功
   */
  boolean saveIgnore(T entity);

  /**
   * 表示插入替换数据，需求表中有PrimaryKey，或者unique索引，如果数据库已经存在数据，则用新数据替换，如果没有数据效果则和insert into一样；
   *
   * @param entity entity
   * @return 是否成功
   */
  boolean saveReplace(T entity);

  /**
   * 插入（批量）,插入如果中已经存在相同的记录，则忽略当前新数据
   *
   * @param entityList 实体对象集合
   * @param batchSize  批次大小
   * @return 是否成功
   */
  boolean saveIgnoreBatch(Collection<T> entityList, int batchSize);

  /**
   * 插入（批量）,插入如果中已经存在相同的记录，则忽略当前新数据
   *
   * @param entityList 实体对象集合
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  default boolean saveIgnoreBatch(Collection<T> entityList) {
    return saveIgnoreBatch(entityList, 1000);
  }

  /**
   * 插入（批量）,表示插入替换数据，需求表中有PrimaryKey，或者unique索引，如果数据库已经存在数据，则用新数据替换，如果没有数据效果则和insert into一样；
   *
   * @param entityList 实体对象集合
   * @param batchSize  批次大小
   * @return 是否成功
   */
  boolean saveReplaceBatch(Collection<T> entityList, int batchSize);

  /**
   * 插入（批量）,表示插入替换数据，需求表中有PrimaryKey，或者unique索引，如果数据库已经存在数据，则用新数据替换，如果没有数据效果则和insert into一样；
   *
   * @param entityList 实体对象集合
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  default boolean saveReplaceBatch(Collection<T> entityList) {
    return saveReplaceBatch(entityList, 1000);
  }
}
