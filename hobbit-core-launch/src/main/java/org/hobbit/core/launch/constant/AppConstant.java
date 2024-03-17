package org.hobbit.core.launch.constant;

/**
 * @author lhy
 * @version 1.0.0 2022/12/4
 */
public interface AppConstant {

  /**
   * 应用版本
   */
  String APPLICATION_VERSION = "1.0.0-SNAPSHOT";
  /**
   * 开发环境
   */
  String DEV_CODE = "dev";
  /**
   * 生产环境
   */
  String PROD_CODE = "prod";
  /**
   * 测试环境
   */
  String TEST_CODE = "test";

  /**
   * 代码部署于 linux 上，工作默认为 mac 和 Windows
   */
  String OS_NAME_LINUX = "LINUX";

  /**
   * 应用名前缀
   */
  String APPLICATION_NAME_PREFIX = "hobbit-";

  /**
   * 日志模块名称
   */
  String APPLICATION_LOG_NAME = APPLICATION_NAME_PREFIX + "log";
}
