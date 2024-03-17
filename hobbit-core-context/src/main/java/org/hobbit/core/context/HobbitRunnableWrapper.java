package org.hobbit.core.context;

import jakarta.annotation.Nullable;
import java.util.Map;
import org.hobbit.core.tool.utils.ThreadLocalUtil;
import org.slf4j.MDC;

/**
 * 多线程中传递 context 和 mdc
 *
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
public class HobbitRunnableWrapper implements Runnable {

  private final Runnable delegate;
  private final Map<String, Object> tlMap;

  @Nullable
  private final Map<String, String> mdcMap;

  public HobbitRunnableWrapper(Runnable runnable) {
    this.delegate = runnable;
    this.tlMap = ThreadLocalUtil.getAll();
    this.mdcMap = MDC.getCopyOfContextMap();
  }

  @Override
  public void run() {
    if (!tlMap.isEmpty()) {
      ThreadLocalUtil.put(tlMap);
    }
    if (mdcMap != null && mdcMap.isEmpty()) {
      MDC.setContextMap(mdcMap);
    }
    try {
      delegate.run();
    } finally {
      tlMap.clear();
      if (mdcMap != null) {
        mdcMap.clear();
      }
      ThreadLocalUtil.clear();
      MDC.clear();
    }
  }
}
