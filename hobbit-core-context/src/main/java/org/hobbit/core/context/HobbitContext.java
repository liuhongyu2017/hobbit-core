package org.hobbit.core.context;

import jakarta.annotation.Nullable;
import java.util.function.Function;

/**
 * Hobble 微服务上下文
 *
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
public interface HobbitContext {

  /**
   * 获取请求id
   *
   * @return 请求id
   */
  @Nullable
  String getRequestId();

  /**
   * 账号id
   *
   * @return 账号id
   */
  @Nullable
  String getAccountId();

  /**
   * 获取租户id
   *
   * @return 租户id
   */
  @Nullable
  String getTenantId();

  /**
   * 获取上下文中的数据
   *
   * @param ctxKey 上下文的key
   * @return 返回的对象
   */
  String get(String ctxKey);

  /**
   * 获取上下文中数据
   *
   * @param ctxKey   上下文的key
   * @param function 函数式
   * @param <T>      泛型对象
   * @return 返回对象
   */
  @Nullable
  <T> T get(String ctxKey, Function<String, T> function);
}
