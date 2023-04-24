package org.hobbit.core.redis.serializer;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.ObjectUtils;

/**
 * ProtoStuff 序列化
 *
 * @author lhy
 * @version 1.0.0 2023/4/23
 */
public class ProtoStuffSerializer implements RedisSerializer<Object> {

  @SuppressWarnings("rawtypes")
  private final Schema<BytesWrapper> schema;

  public ProtoStuffSerializer() {
    this.schema = RuntimeSchema.getSchema(BytesWrapper.class);
  }

  @Override
  public byte[] serialize(Object o) throws SerializationException {
    if (o == null) {
      return null;
    }
    // 分配缓冲区
    LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
    try {
      // 使用给定的架构将消息序列化为字节数组
      return ProtostuffIOUtil.toByteArray(new BytesWrapper<>(o), schema, buffer);
    } finally {
      buffer.clear();
    }
  }

  @Override
  public Object deserialize(byte[] bytes) throws SerializationException {
    if (ObjectUtils.isEmpty(bytes)) {
      return null;
    }
    BytesWrapper<Object> wrapper = new BytesWrapper<>();
    // 使用给定的架构将消息与字节数组合并
    ProtostuffIOUtil.mergeFrom(bytes, wrapper, schema);
    return wrapper.getValue();
  }
}
