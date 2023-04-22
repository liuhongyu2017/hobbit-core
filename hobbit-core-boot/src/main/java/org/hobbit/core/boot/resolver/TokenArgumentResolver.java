package org.hobbit.core.boot.resolver;

import org.hobbit.core.auth.HobbitUser;
import org.hobbit.core.auth.util.AuthUtil;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Token 转化 HobbitUser
 *
 * @author lhy
 * @version 1.0.0 2023/4/21
 */
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

  /**
   * 判断Controller层中的参数，是否满足条件，满足条件则执行resolveArgument方法，不满足则跳过。
   *
   * @param parameter 参数集合
   * @return 格式化后的参数
   */
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(HobbitUser.class);
  }

  /**
   * 出参设置
   *
   * @param parameter     入参集合
   * @param mavContainer  model 和 view
   * @param webRequest    web相关
   * @param binderFactory 入参解析
   * @return 包装对象
   */
  @Nullable
  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
      @Nullable WebDataBinderFactory binderFactory
  ) {
    return AuthUtil.getUser();
  }
}
