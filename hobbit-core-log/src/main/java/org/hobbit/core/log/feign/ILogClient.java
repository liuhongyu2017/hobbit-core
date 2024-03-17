package org.hobbit.core.log.feign;

import org.hobbit.core.launch.constant.AppConstant;
import org.hobbit.core.log.model.LogApi;
import org.hobbit.core.log.model.LogError;
import org.hobbit.core.log.model.LogUsual;
import org.hobbit.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign接口类
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
@SuppressWarnings("UnusedReturnValue")
@FeignClient(
    value = AppConstant.APPLICATION_LOG_NAME,
    fallback = LogClientFallback.class
)
public interface ILogClient {

  String API_PREFIX = "/log";

  /**
   * 保存错误日志
   *
   * @param log 日志实体
   * @return boolean
   */
  @PostMapping(API_PREFIX + "/saveUsualLog")
  R<Boolean> saveUsualLog(@RequestBody LogUsual log);

  /**
   * 保存操作日志
   *
   * @param log 日志实体
   * @return boolean
   */
  @PostMapping(API_PREFIX + "/saveApiLog")
  R<Boolean> saveApiLog(@RequestBody LogApi log);

  /**
   * 保存错误日志
   *
   * @param log 日志实体
   * @return boolean
   */
  @PostMapping(API_PREFIX + "/saveErrorLog")
  R<Boolean> saveErrorLog(@RequestBody LogError log);
}
