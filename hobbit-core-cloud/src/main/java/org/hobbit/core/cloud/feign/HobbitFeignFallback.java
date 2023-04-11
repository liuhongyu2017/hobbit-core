package org.hobbit.core.cloud.feign;

import com.fasterxml.jackson.databind.JsonNode;
import feign.FeignException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.hobbit.core.tool.api.R;
import org.hobbit.core.tool.api.ResultCode;
import org.hobbit.core.tool.jackson.JsonUtil;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.lang.Nullable;

/**
 * fallBack 代理处理
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
@Slf4j
public record HobbitFeignFallback<T>(
    Class<T> targetType, String targetName, Throwable cause
) implements MethodInterceptor {

  private final static String CODE = "code";

  @Nullable
  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) {
    String errorMessage = cause.getMessage();
    log.error("BladeFeignFallback:[{}.{}] serviceId:[{}] message:[{}]", targetType.getName(),
        method.getName(), targetName, errorMessage);
    Class<?> returnType = method.getReturnType();
    // 集合类型反馈空集合
    if (List.class == returnType || Collection.class == returnType) {
      return Collections.emptyList();
    }
    if (Set.class == returnType) {
      return Collections.emptySet();
    }
    if (Map.class == returnType) {
      return Collections.emptyMap();
    }
    // 暂时不支持 flux，rx，异步等，返回值不是 R，直接返回 null。
    if (R.class != returnType) {
      return null;
    }
    // 非 FeignException
    if (!(cause instanceof FeignException exception)) {
      return R.fail(ResultCode.INTERNAL_SERVER_ERROR, errorMessage);
    }
    byte[] content = null;
    // 如果返回的数据为空
    if (exception.responseBody().isEmpty()) {
      return R.fail(ResultCode.INTERNAL_SERVER_ERROR, errorMessage);
    }
    ByteBuffer byteBuffer = exception.responseBody().get();
    // 转换成 jsonNode 读取，因为直接转换，可能 对方放回的并 不是 R 的格式。
    JsonNode resultNode = JsonUtil.readTree(byteBuffer.array());
    // 判断是否 R 格式 返回体
    if (resultNode.has(CODE)) {
      return JsonUtil.getInstance().convertValue(resultNode, R.class);
    }
    return R.fail(resultNode.toString());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HobbitFeignFallback<?> that = (HobbitFeignFallback<?>) o;
    return targetType.equals(that.targetType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(targetType);
  }
}
