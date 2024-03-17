package org.hobbit.core.jwt.serializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

/**
 * 将redis key序列化为字符串
 *
 * <p>
 * spring cache 中的简单基本类型直接使用 StringRedisSerializer 会有问题
 * </p>
 *
 * @author lhy
 * @version 1.0.0 2023/4/21
 */
public class JwtRedisKeySerializer implements RedisSerializer<Object> {

  private final Charset charset;
  private final ConversionService converter;

  public JwtRedisKeySerializer() {
    this(StandardCharsets.UTF_8);
  }

  public JwtRedisKeySerializer(Charset charset) {
    Objects.requireNonNull(charset, "Charset must not be null");
    this.charset = charset;
    this.converter = DefaultConversionService.getSharedInstance();
  }

  @Nullable
  @Override
  public byte[] serialize(@Nullable Object o) throws SerializationException {
    Objects.requireNonNull(o, "redis key is null");
    String key;
    if (o instanceof SimpleKey) {
      key = "";
    } else if (o instanceof String) {
      key = (String) o;
    } else {
      key = converter.convert(o, String.class);
    }
    Objects.requireNonNull(key, "redis key is null");
    return key.getBytes(this.charset);
  }

  @Nullable
  @Override
  public Object deserialize(@Nullable byte[] bytes) throws SerializationException {
    if (bytes == null) {
      return null;
    }
    return new String(bytes, charset);
  }
}
