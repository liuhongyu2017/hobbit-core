package org.hobbit.core.mybatis.plugins;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.hobbit.core.mybatis.intercept.QueryInterceptor;

/**
 * 拓展分页拦截器
 *
 * @author lhy
 * @version 1.0.0 2023/5/4
 */
@Setter
public class HobbitPaginationInterceptor extends PaginationInnerInterceptor {

  /**
   * 查询拦截器
   */
  private QueryInterceptor[] queryInterceptors;

  @SneakyThrows
  @Override
  public boolean willDoQuery(
      Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
    QueryInterceptorExecutor.exec(queryInterceptors, executor, ms, parameter, rowBounds, resultHandler, boundSql);
    return super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
  }
}
