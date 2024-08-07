package org.hobbit.core.tool;

import java.util.HashMap;
import java.util.Map;
import org.hobbit.core.tool.utils.StringUtil;

/**
 * @author lhy
 * @version 1.0.0 2024/07/24
 */
public class Main {

  public static void main(String[] args) {
    Map<String, String> params = new HashMap<String, String>();
    params.put("a", "aValue");
    params.put("b", "bValue");
    String format = StringUtil.format(new StringBuffer("{a} and {b}"), params);
    System.out.println(format);
  }
}
