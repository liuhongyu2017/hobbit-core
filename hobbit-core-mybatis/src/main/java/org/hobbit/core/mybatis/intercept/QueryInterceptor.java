package org.hobbit.core.mybatis.intercept;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.core.Ordered;

/**
 * 自定义 mybatis plus 查询拦截器
 *
 * @author lhy
 * @version 1.0.0 2023/5/4
 */
@SuppressWarnings("rawtypes")
public interface QueryInterceptor extends Ordered {

  /**
   * 拦截处理
   */
  void intercept(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
      ResultHandler resultHandler, BoundSql boundSql);

  /**
   * 排序
   *
   * @return int
   */
  @Override
  default int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }
}
