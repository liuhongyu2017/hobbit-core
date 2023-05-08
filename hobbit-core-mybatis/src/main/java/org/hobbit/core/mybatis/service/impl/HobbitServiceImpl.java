package org.hobbit.core.mybatis.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import java.util.Collection;
import org.hobbit.core.mybatis.base.BaseEntity;
import org.hobbit.core.mybatis.base.BaseServiceImpl;
import org.hobbit.core.mybatis.injector.HobbitSqlMethod;
import org.hobbit.core.mybatis.mapper.IHobbitMapper;
import org.hobbit.core.mybatis.service.IHobbitService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * HobbitService impl
 *
 * @author lhy
 * @version 1.0.0 2023/5/8
 */
@Validated
public class HobbitServiceImpl<M extends IHobbitMapper<T>, T extends BaseEntity> extends
    BaseServiceImpl<M, T> implements IHobbitService<T> {

  @Override
  public boolean saveIgnore(T entity) {
    return SqlHelper.retBool(baseMapper.insertIgnore(entity));
  }

  @Override
  public boolean saveReplace(T entity) {
    return SqlHelper.retBool(baseMapper.replace(entity));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean saveIgnoreBatch(Collection<T> entityList, int batchSize) {
    return saveBatch(entityList, batchSize, HobbitSqlMethod.INSERT_IGNORE_ONE);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean saveReplaceBatch(Collection<T> entityList, int batchSize) {
    return saveBatch(entityList, batchSize, HobbitSqlMethod.REPLACE_ONE);
  }

  private boolean saveBatch(Collection<T> entityList, int batchSize, HobbitSqlMethod sqlMethod) {
    String sqlStatement = hobbitSqlStatement(sqlMethod);
    executeBatch(entityList, batchSize,
        (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    return true;
  }

  /**
   * 获取 bladeSqlStatement
   *
   * @param sqlMethod ignore
   * @return sql
   */
  protected String hobbitSqlStatement(HobbitSqlMethod sqlMethod) {
    return currentMapperClass().getName() + StringPool.DOT + sqlMethod.getMethod();
  }
}
