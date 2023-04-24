package org.hobbit.core.redis.cache;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.Getter;
import org.hobbit.core.tool.utils.CollectionUtil;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.Assert;

/**
 * redis 工具
 *
 * @author lhy
 * @version 1.0.0 2023/4/23
 */
@Getter
@SuppressWarnings("unchecked")
public class HobbitRedis {

  private final RedisTemplate<String, Object> redisTemplate;
  private final StringRedisTemplate stringRedisTemplate;
  private final ValueOperations<String, Object> valueOps;
  private final HashOperations<String, Object, Object> hashOps;
  private final ListOperations<String, Object> listOps;
  private final SetOperations<String, Object> setOps;
  private final ZSetOperations<String, Object> zSetOps;
  private final ConversionService converter;

  public HobbitRedis(RedisTemplate<String, Object> redisTemplate,
      StringRedisTemplate stringRedisTemplate) {
    this.redisTemplate = redisTemplate;
    this.stringRedisTemplate = stringRedisTemplate;
    Assert.notNull(redisTemplate, "redisTemplate is null");
    this.valueOps = redisTemplate.opsForValue();
    this.hashOps = redisTemplate.opsForHash();
    this.listOps = redisTemplate.opsForList();
    this.setOps = redisTemplate.opsForSet();
    this.zSetOps = redisTemplate.opsForZSet();
    this.converter = DefaultConversionService.getSharedInstance();
  }

  /**
   * 设置缓存
   *
   * @param cacheKey 缓存key
   * @param value    缓存value
   */
  public void set(CacheKey cacheKey, Object value) {
    String key = cacheKey.getKey();
    Duration expire = cacheKey.getExpire();
    if (expire == null) {
      set(key, value);
    } else {
      set(key, value, expire);
    }
  }

  /**
   * 存放 key value 对到 redis。如果 key 存在，将覆盖旧值
   */
  public void set(String key, Object value) {
    valueOps.set(key, value);
  }

  public void set(String key, Object value, Duration expire) {
    valueOps.set(key, value, expire);
  }

  public void set(String key, Object value, Long seconds) {
    valueOps.set(key, value, seconds, TimeUnit.SECONDS);
  }

  /**
   * 返回 key 所关联的 value 值
   */
  public <T> T get(String key) {
    return (T) valueOps.get(key);
  }

  public <T> T get(String key, Class<T> aClass) {
    return converter.convert(valueOps.get(key), aClass);
  }

  public <T> T get(CacheKey cacheKey) {
    Duration expire = cacheKey.getExpire();
    if (expire == null) {
      return this.get(cacheKey.getKey());
    }
    return (T) valueOps.getAndExpire(cacheKey.getKey(), expire);
  }

  public <T> T get(CacheKey cacheKey, Class<T> aClass) {
    Duration expire = cacheKey.getExpire();
    if (expire == null) {
      return this.get(cacheKey.getKey(), aClass);
    }
    return converter.convert(valueOps.getAndExpire(cacheKey.getKey(), expire), aClass);
  }

  /**
   * 获取 cache 为 null 时，设置缓存
   *
   * @param key    cacheKey
   * @param loader cache loader
   */
  public <T> T get(String key, Supplier<T> loader) {
    T value = this.get(key);
    if (value != null) {
      return value;
    }
    value = loader.get();
    if (value == null) {
      return null;
    }
    this.set(key, value);
    return value;
  }

  /**
   * 删除给定的 key
   */
  public Boolean del(String key) {
    return redisTemplate.delete(key);
  }

  public Boolean del(CacheKey cacheKey) {
    return del(cacheKey.getKey());
  }

  /**
   * 删除多个指定 key
   */
  public Long del(Collection<String> keys) {
    return redisTemplate.delete(keys);
  }

  public Long del(String... keys) {
    return del(Arrays.asList(keys));
  }

  /**
   * 查询所有给定模式 pattern 的 key
   * <pre>
   *  KEYS * 匹配数据库中所有 key
   *  KEYS h?llo 匹配 hello、hallo、hxllo等
   *  KEYS h*llo 匹配 hllo、heeeeelo等
   *  KEYS h[ae]llo 匹配 hello、hallo，但不匹配 hillo
   * </pre>
   * 特殊符号用 \ 分割
   */
  public Set<String> keys(String pattern) {
    return redisTemplate.keys(pattern);
  }

  /**
   * <p>同时设置一个或多个 key-value 对</p>
   * <p>如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，如果这不是你所希望的效果，请考虑使用 MSETNX命令：它只会在所有给定 key
   * 都不存在的情况下进行设置操作。</p>
   * <p>MSET 是一个原子性(atomic)操作，所有给定 key 都会在同一时间内被设置，某些给定 key 被更新而另一些给定 key 没有改变的情况，不可能发生。</p>
   * <pre>
   * 例子：
   * Cache cache = RedisKit.use();			// 使用 Redis 的 cache
   * cache.mset("k1", "v1", "k2", "v2");		//放入多个 key value 键值对 List
   * list = cache.mget("k1", "k2");		// 利用多个键值得到上面代码放入的值
   * </pre>
   */
  public void mSet(Object... keysValues) {
    valueOps.multiSet(CollectionUtil.toMap(keysValues));
  }

  /**
   * <p>返回所有（一个或多个）给定 key 的值。</p>
   * <p>如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil。因此，该命令永不失效。</p>
   */
  private List<Object> mGet(Collection<String> keys) {
    return valueOps.multiGet(keys);
  }

  public List<Object> mGet(String... keys) {
    return mGet(Arrays.asList(keys));
  }

  /**
   * <p>将 key 中储存的数字值减一。</p>
   * <p>如果 key 不存在，那么 key 的值先被初始化为 0，再执行 DECR 操作</p>
   * <p>如果值包含错误类型（比如字符串不能表示为数字），那么返回一个错误</p>
   * <p>本操作的值限制在 64 位（bit）有符号数字表示之内</p>
   */
  public Long decr(String key) {
    return valueOps.decrement(key);
  }

  /**
   * <p>将 key 中储存的数字减去 decrement</p>
   * <p>如果 key 不存在，那么 key 的值先被初始化为 0，再执行 DECRBY 操作</p>
   * <p>如果值包含错误类型（比如字符串不能表示为数字），那么返回一个错误</p>
   * <p>本操作的值限制在 64 位（bit）有符号数字表示之内</p>
   */
  public Long decr(String key, long longValue) {
    return valueOps.decrement(key, longValue);
  }

  /**
   * <p>将 key 中所储存的数字加一</p>
   * <p>如果 key 不存在，那么 key 的值先被初始化为 0，再执行 INCR 操作</p>
   * <p>如果值包含错误类型（比如字符串不能表示为数字），那么返回一个错误</p>
   * <p>本操作的值限制在 64 位（bit）有符号数字表示之内</p>
   */
  public Long incr(String key) {
    return valueOps.increment(key);
  }

  /**
   * <p>将 key 中所储存的数字加上 increment</p>
   * <p>如果 key 不存在，那么 key 的值先被初始化为 0，再执行 INCRBY 操作</p>
   * <p>如果值包含错误类型（比如字符串不能表示为数字），那么返回一个错误</p>
   * <p>本操作的值限制在 64 位（bit）有符号数字表示之内</p>
   */
  public Long incr(String key, long longValue) {
    return valueOps.increment(key, longValue);
  }

  /**
   * 获取计数器的值
   */
  public Long getCounter(String key) {
    return Long.valueOf(String.valueOf(get(key)));
  }

  /**
   * 检查给定的 key 是否存在
   */
  public Boolean exists(String key) {
    return redisTemplate.hasKey(key);
  }

  /**
   * 从 redis 当中，随机返回（不删除）一个 key
   */
  public String randomKey() {
    return redisTemplate.randomKey();
  }

  /**
   * <p>将 key 改名为 newKey</p>
   * <p>当 key 和 newKey 相同，或者 key 不存在，返回一个错误</p>
   * <p>当 key 存在，RENAME 命令将覆盖旧值</p>
   */
  public void rename(String oldKey, String newKey) {
    redisTemplate.rename(oldKey, newKey);
  }

  /**
   * <p>将当前数据库的 key 移动到给定的数据库 db 中</p>
   * <p>如果当前数据库（源数据库）和给定数据库（目标数据库）有相同名字的给定 key，或者 key 不存在于当前数据库，那么 MOVE 没有任何效果</p>
   * <p>因此，也可以利用这一特性，将 MOVE 当作锁（locking）原语（primitive）</p>
   */
  public Boolean move(String key, int dbIndex) {
    return redisTemplate.move(key, dbIndex);
  }

  /**
   * <p>为给定 key 设置缓存时间，当 key 过期时（缓存时间为 0），它会被自动清除</p>
   * <p>再 redis 中，带有生存时间的 key 被称为【易失的】（volatile）</p>
   */
  public Boolean expire(String key, long seconds) {
    return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
  }

  public Boolean expire(String key, Duration timeout) {
    return expire(key, timeout.getSeconds());
  }

  /**
   * <p>EXPIREAT 的作用和 EXPIRE 类似，EXPIREAT 到指定的时间，会被自动清除</p>
   */
  public Boolean expire(String key, Instant instant) {
    return redisTemplate.expireAt(key, instant);
  }

  public Boolean expireMilliseconds(String key, long milliseconds) {
    return redisTemplate.expire(key, milliseconds, TimeUnit.MILLISECONDS);
  }

  /**
   * <p>返回 key 的旧值后，将 key 的值设置为 value</p>
   */
  public <T> T getSet(String key, Object value) {
    return (T) valueOps.getAndSet(key, value);
  }

  /**
   * <p>移除 key 的缓存时间，key 从【易失的】转换为【持久的】</p>
   */
  public Boolean persist(String key) {
    return redisTemplate.persist(key);
  }

  /**
   * 返回 key 保存的 value 类型
   */
  public String type(String key) {
    return Objects.requireNonNull(redisTemplate.type(key)).code();
  }

  /**
   * <p>秒为单位，返回 key 的缓存时间</p>
   */
  public Long ttl(String key) {
    return redisTemplate.getExpire(key);
  }

  /**
   * <p>毫秒为单位，返回 key 的缓存时间</p>
   */
  public Long ttlMilliseconds(String key) {
    return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
  }

  /**
   * <p>将哈希表 key 中的域 field 的值设置为 value</p>
   * <p>如果 key 不存在，创建新的哈希表，并 HSET</p>
   * <p>如果 field 以及存在于哈希表中，旧的值将会被覆盖</p>
   */
  public void hSet(String key, Object field, Object value) {
    hashOps.put(key, field, value);
  }

  /**
   * <p>同时将多个 field-value 设置到哈希表 key 中</p>
   * <p>此命令会覆盖哈希表中已存在的 field</p>
   * <p>如果 key 不存在，创建新的哈希表，并 HMSET</p>
   */
  public void hMset(String key, Map<Object, Object> hash) {
    hashOps.putAll(key, hash);
  }

  /**
   * 返回哈希表 key 中给定的域 field 的值
   */
  public <T> T hGet(String key, Object field) {
    return (T) hashOps.get(key, field);
  }

  /**
   * <p>返回哈希表 key 中，一个或多个给定 field 的值</p>
   * <p>如果给定的域不存在于哈希表，那么返回一个 nil 值</p>
   * <p>不存在的 key 被当作一个空的哈希表处理，将返回只带有 nil 值的表</p>
   */
  public List<Object> hmGet(String key, Collection<Object> fieldKeys) {
    return hashOps.multiGet(key, fieldKeys);
  }

  public List<Object> hmGet(String key, Object... fields) {
    return hmGet(key, Arrays.asList(fields));
  }

  /**
   * 删除哈希表 key 中一个或多个指定域，不存在的域将被忽略
   */
  public Long hDel(String key, Object... fields) {
    return hashOps.delete(key, fields);
  }

  /**
   * 查看哈希表 key 中，给定域 field 是否存在
   */
  public Boolean hExists(String key, Object field) {
    return hashOps.hasKey(key, field);
  }

  /**
   * 返回哈希表 key 中，所有的域和值
   */
  public Map<?, ?> hGetAll(String key) {
    return hashOps.entries(key);
  }

  /**
   * 返回哈希表 key 中所有域的值
   */
  public List<?> hValues(String key) {
    return hashOps.values(key);
  }

  /**
   * 返回哈希表 key 中所有的域
   */
  public Set<Object> hKeys(String key) {
    return hashOps.keys(key);
  }

  /**
   * 返回哈希表 key 中域的数量
   */
  public Long hLen(String key) {
    return hashOps.size(key);
  }

  /**
   * <p>为哈希表 key 中域 field 的值加上增量 increment</p>
   * <p>增量可以为负数，相当于对给定的域进行减法操作</p>
   * <p>如果 key 不存在，将创建新的哈希表</p>
   * <p>如果 field 不存在，在执行之前，域的值将会被设置为 0</p>
   * <p>对域的值非数字类型进行该操作，将会造成错误</p>
   * <p>本操作的值限制在 64 位（bit）有符号数字表示之内</p>
   */
  public Long hIncrBy(String key, Object field, long value) {
    return hashOps.increment(key, field, value);
  }

  /**
   * <p>为哈希表 key 中域 field 的值加上浮点类型的增量 increment</p>
   * <p>增量可以为负数，相当于对给定的域进行减法操作</p>
   * <p>如果 key 不存在，将创建新的哈希表</p>
   * <p>如果 field 不存在，在执行之前，域的值将会被设置为 0</p>
   * <pre>
   *   当以下任意一个条件发生时，返回一个错误：
   *   1: 域 field 的值不是字符串类型(因为 redis 中的数字和浮点数都以字符串的形式保存，所以它们都属于字符串类型）
   *   2:域 field 当前的值或给定的增量 increment 不能解释(parse)为双精度浮点数(double precision floating point number)
   * </pre>
   */
  public Double hIncrByFloat(String key, Object field, double value) {
    return hashOps.increment(key, field, value);
  }

  /**
   * <p>返回列表 key 中，下标为 index 的元素</p>
   * <p>下标 index 参数 0 代表第一个元素，1 代表第二个元素</p>
   * <p>也可以使用负数下标，-1 代表列表最后一个元素，-2 代表列表倒数第二个元素</p>
   * <p>如果 key 不是类表类型，返回一个错误</p>
   */
  public <T> T lIndex(String key, long index) {
    return (T) listOps.index(key, index);
  }

  /**
   * <p>返回列表 key 长度</p>
   * <p>如果列表 key 不存在，则 key 被视为一个空列表，返回 0</p>
   * <p>如果 key 不是类表类型，返回一个错误</p>
   */
  public Long lLen(String key) {
    return listOps.size(key);
  }

  /**
   * 移除并返回列表 key 的头元素
   */
  public <T> T lPop(String key) {
    return (T) listOps.leftPop(key);
  }

  /**
   * <p>将一个或多个值 value ，插入 列表 key 的头部</p>
   * <p>如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头</p>
   * <pre>
   *   例如：
   *   对空列表 myList 插入 a、b、c，列表的值将是 c、b、a
   * </pre>
   * <p>如果 key 不存在，将创建一个空的列表</p>
   * <p>当 key 不是列表类型，返回一个错误</p>
   */
  public Long lPush(String key, Object... values) {
    return listOps.leftPush(key, values);
  }

  /**
   * <p>将列表 key 下标为 index 的元素的值设置为 value</p>
   * <p>当 index 参数超出范围，或对一个空列表（key 不存在），返回一个错误</p>
   */
  public void lSet(String key, long index, Object value) {
    listOps.set(key, index, value);
  }

  /**
   * <p>根据参数 count 的值，移除列表中与参数 value 相等的元素</p>
   * <pre>
   *   count 的值可以是以下几种：
   *   count > 0：从表头向表尾搜索，移除与 value 相等的元素，数量为 count
   *   count < 0: 从表尾向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值
   *   count = 0: 移除表中所有与 value 相等的值
   * </pre>
   */
  public Long lRem(String key, long count, Object value) {
    return listOps.remove(key, count, value);
  }

  /**
   * <p>返回列表 key 中指定区间内的元素，区间以偏移量 start 和 end 指定</p>
   * <p>下标 index 参数 0 代表第一个元素，1 代表第二个元素</p>
   * <p>也可以使用负数下标，-1 代表列表最后一个元素，-2 代表列表倒数第二个元素</p>
   * <pre>
   *   例子：
   *   获取 list 中所有元素：cache.lRange(listKey, 0, -1);
   *   获取 list 中下标 1 到 3 的元素：cache.lRange(listKey, 1, 3);
   * </pre>
   */
  public List<?> lRange(String key, long start, long end) {
    return listOps.range(key, start, end);
  }

  /**
   * <p>对列表 key 进修修剪（trim），列表只保留指定区域内元素，不在区域内元素将被删除</p>
   * <pre>
   *   例如：
   *   cache.lTrim(listKey, 0, 2); 列表只保留前3个元素，其余元素全部删除
   * </pre>
   * <p>下标 index 参数 0 代表第一个元素，1 代表第二个元素</p>
   * <p>也可以使用负数下标，-1 代表列表最后一个元素，-2 代表列表倒数第二个元素</p>
   * <p>当 key 不是列表类型时，返回一个错误</p>
   */
  public void lTrim(String key, long start, long end) {
    listOps.trim(key, start, end);
  }

  /**
   * 移除并返回列表 key 的尾元素
   */
  public <T> T rPop(String key) {
    return (T) listOps.rightPop(key);
  }

  /**
   * <p>将一个或多个值 value ，插入 列表 key 的尾部</p>
   * <p>如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到尾部</p>
   * <pre>
   *   例如：
   *   对空列表 myList 插入 a、b、c，列表的值将是 a、b、c
   * </pre>
   * <p>如果 key 不存在，将创建一个空的列表</p>
   * <p>当 key 不是列表类型，返回一个错误</p>
   */
  public Long rPush(String key, Object... values) {
    return listOps.rightPush(key, values);
  }

  /**
   * <pre>
   *   命令在一个原子时间内，执行以下两个动作：
   *   将列表 source 中最后一个元素（尾元素）弹出，并返回给客户端
   *   将列表 source 弹出的元素插入列表 destination，作为 destination 列表的头元素
   * </pre>
   */
  public <T> T rPopLPush(String srcKey, String dstKey) {
    return (T) listOps.rightPopAndLeftPush(srcKey, dstKey);
  }

  /**
   * <p>将一个或多个 member 元素插入到集合 key 当中，已经存在于集合的 member 元素将被忽略</p>
   * <p>如果 key 不存在，将创建一个空集合，并插入 member</p>
   * <p>当 key 不是集合类型，返回一个错误</p>
   */
  public Long sAdd(String key, Object... members) {
    return setOps.add(key, members);
  }

  /**
   * 移除并返回集合中的一个随机元素
   */
  public <T> T sPop(String key) {
    return (T) setOps.pop(key);
  }

  /**
   * <p>返回集合 key 中的所有元素</p>
   * <p>不存在的 key 将返回空集合</p>
   */
  public Set<?> sMembers(String key) {
    return setOps.members(key);
  }

  /**
   * 判断 member 元素是否是集合 key 的成员
   */
  public Boolean sIsMember(String key, Object member) {
    return setOps.isMember(key, member);
  }

  /**
   * 返回多个集合的交集
   */
  public Set<?> sInter(String key, String otherKey) {
    return setOps.intersect(key, otherKey);
  }

  /**
   * 返回多个集合的交集
   */
  public Set<?> sInter(String key, Collection<String> otherKeys) {
    return setOps.intersect(key, otherKeys);
  }

  /**
   * 返回集合 key 中一个随机元素
   */
  public <T> T sRandMember(String key) {
    return (T) setOps.randomMember(key);
  }

  /**
   * <p>返回集合中 count 个随机元素</p>
   * <pre>
   *   count大于0且小于集合长度，将返回一个包含 count 个元素的数组，数组中的元素个不相同
   *   count大于集合长度，将返回整个集合
   *   count为负数，将返回一个包含 count 绝对值数量的数组，数组中的元素会重复出现多次
   * </pre>
   */
  public List<?> sRandMember(String key, int count) {
    return setOps.randomMembers(key, count);
  }

  /**
   * 移除集合 key 中一个或多个 member 元素，不存在的 member 将被忽略
   */
  public Long sRem(String key, Object... members) {
    return setOps.remove(key, members);
  }

  /**
   * 返回多个集合的并集，不存在的 key 视为空
   */
  public Set<?> sUnion(String key, String otherKey) {
    return setOps.union(key, otherKey);
  }

  /**
   * 返回多个集合的并集，不存在的 key 视为空
   */
  public Set<?> sUnion(String key, Collection<String> otherKeys) {
    return setOps.union(key, otherKeys);
  }

  /**
   * 返回多个集合的差集，不存在的 key 视为空
   */
  public Set<?> sDiff(String key, String otherKey) {
    return setOps.difference(key, otherKey);
  }

  /**
   * 返回多个集合的差集，不存在的 key 视为空
   */
  public Set<?> sDiff(String key, Collection<String> otherKeys) {
    return setOps.difference(key, otherKeys);
  }

  /**
   * <p>将一个或多个元素 member 插入有序集合 key 中，根据 score 排序</p>
   * <p>如果元素 member 已经存在，将更新这个元素的 score</p>
   */
  public Boolean zSet(String key, Object member, double score) {
    return zSetOps.add(key, member, score);
  }

  /**
   * <p>将一个或多个元素 member 插入有序集合 key 中，根据 score 排序</p>
   * <p>如果元素 member 已经存在，将更新这个元素的 score</p>
   */
  public Long zAdd(String key, Map<Object, Double> scoreMembers) {
    Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
    scoreMembers.forEach((k, v) -> tuples.add(new DefaultTypedTuple<>(k, v)));
    return zSetOps.add(key, tuples);
  }

  /**
   * 返回有序集合的长度
   */
  public Long zCard(String key) {
    return zSetOps.zCard(key);
  }

  /**
   * 返回有序集合 key 中，score 值在 min 和 max 之间（包含等于）的成员数量
   */
  public Long zCount(String key, double min, double max) {
    return zSetOps.count(key, min, max);
  }

  /**
   * 有序集合 key 的成员 member 的 score 值加上增量 increment
   */
  public Double zIncrBy(String key, Object member, double score) {
    return zSetOps.incrementScore(key, member, score);
  }

  /**
   * <p>返回有序集合 key 指定区域内的成员</p>
   * <p>其中成员的位置按 score 值递增（从小到大）来排序</p>
   * <p>具有相同 score 值的成员按字典序（lexicographical order）来排列。</p>
   */
  public Set<?> zRange(String key, long start, long end) {
    return zSetOps.range(key, start, end);
  }

  /**
   * <p>返回有序集合 key 指定区域内的成员</p>
   * <p>其中成员的位置按 score 值递增（从大到小）来排序</p>
   * <p>具有相同 score 值的成员按字典序（revers lexicographical order）来排列。</p>
   */
  public Set<?> zReverseRange(String key, long start, long end) {
    return zSetOps.reverseRange(key, start, end);
  }

  /**
   * <p>返回有序集 key 中，所有 score 值介于 min 和 max 之间（包含等于）的成员</p>
   * <p>有序集合 key 按照 score 的值从小到大排序</p>
   */
  public Set<?> zRangeByScore(String key, double min, double max) {
    return zSetOps.rangeByScore(key, min, max);
  }

  /**
   * <p>返回有序集 key 中成员 member 的排名，按照 score 值从小到大排序</p>
   * <p>排名以 0 为底，也就是说，score 值最小的成员排名为 0 </p>
   */
  public Long zRank(String key, Object member) {
    return zSetOps.rank(key, member);
  }

  /**
   * <p>返回有序集 key 中成员 member 的排名，其中有序集成员按 score 值从大到小排序</p>
   * <p>排名以 0 为底，也就是说，score 值最大的成员排名为 0 </p>
   */
  public Long zReverseRank(String key, Object member) {
    return zSetOps.reverseRank(key, member);
  }

  /**
   * <p>移除有序集 key 中的一个或多个成员，不存在的成员将被忽略</p>
   * <p>当 key 存在但不是有序集类型时，返回一个错误</p>
   */
  public Long zRem(String key, Object... members) {
    return zSetOps.remove(key, members);
  }

  /**
   * <p>返回有序集 key 中，成员 member 的 score 值</p>
   * <p>如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 nil</p>
   */
  public Double zScore(String key, Object member) {
    return zSetOps.score(key, member);
  }
}
