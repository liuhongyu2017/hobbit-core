package org.hobbit.core.mybatis.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.hobbit.core.mybatis.intercept.QueryInterceptor;
import org.hobbit.core.tool.utils.ObjectUtil;

/**
 * 查询拦截器执行器
 *
 * @author lhy
 * @version 1.0.0 2023/5/4
 */
@SuppressWarnings({"rawtypes"})
public class QueryInterceptorExecutor {

  /**
   * 执行查询拦截器
   */
  static void exec(QueryInterceptor[] interceptors, Executor executor, MappedStatement ms,
      Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
    if (ObjectUtil.isEmpty(interceptors)) {
      return;
    }
    for (QueryInterceptor interceptor : interceptors) {
      interceptor.intercept(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }
  }
}
