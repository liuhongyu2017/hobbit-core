package org.hobbit.core.redis.constant;

/**
 * 缓存名
 *
 * @author lhy
 * @version 1.0.0 2023/4/25
 */
public interface CacheConstant {

  String BIZ_CACHE = "hobbit:biz";

  String MENU_CACHE = "hobbit:menu";

  String USER_CACHE = "hobbit:user";

  String DICT_CACHE = "hobbit:dict";

  String FLOW_CACHE = "hobbit:flow";

  String SYS_CACHE = "hobbit:sys";

  String RESOURCE_CACHE = "hobbit:resource";

  String PARAM_CACHE = "hobbit:param";

  String DEFAULT_CACHE = "default:cache";

  String RETRY_LIMIT_CACHE = "retry:limit:cache";

  String HALF_HOUR = "half:hour";

  String HOUR = "hour";

  String ONE_DAY = "one:day";
}
