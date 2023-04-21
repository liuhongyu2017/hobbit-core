package org.hobbit.core.launch.utils;

import java.util.Properties;
import org.springframework.util.StringUtils;

/**
 * @author lhy
 * @version 1.0.0 2023/4/20
 */
public class PropsUtil {

  public static void setProperty(Properties props, String key, String value) {
    if (!StringUtils.hasLength(props.getProperty(key))) {
      props.setProperty(key, value);
    }
  }
}
