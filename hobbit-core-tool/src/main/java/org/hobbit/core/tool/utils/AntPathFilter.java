package org.hobbit.core.tool.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * Spring AntPath 规则文件过滤
 *
 * @author L.cm
 */
@AllArgsConstructor
public class AntPathFilter implements FileFilter, Serializable {

  private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

  private final String pattern;

  /**
   * 过滤规则
   *
   * @param pathname 路径
   * @return boolean
   */
  @Override
  public boolean accept(File pathname) {
    String filePath = pathname.getAbsolutePath();
    return PATH_MATCHER.match(pattern, filePath);
  }
}
