package org.hobbit.core.log.feign;

import lombok.extern.slf4j.Slf4j;
import org.hobbit.core.log.model.LogApi;
import org.hobbit.core.log.model.LogError;
import org.hobbit.core.log.model.LogUsual;
import org.hobbit.core.tool.api.R;

/**
 * @author lhy
 * @version 1.0.0 2023/4/19
 */
@Slf4j
public class LogClientFallback implements ILogClient {

  @Override
  public R<Boolean> saveUsualLog(LogUsual log) {
    return R.fail("usual log send fail");
  }

  @Override
  public R<Boolean> saveApiLog(LogApi log) {
    return R.fail("api log send fail");
  }

  @Override
  public R<Boolean> saveErrorLog(LogError log) {
    return R.fail("error log send fail");
  }
}
