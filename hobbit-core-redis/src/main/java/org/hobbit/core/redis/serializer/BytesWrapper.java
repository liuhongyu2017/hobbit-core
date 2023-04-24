package org.hobbit.core.redis.serializer;

/**
 * redis 序列化辅助类.单纯的泛型无法定义通用 schema ，原因是无法通过泛型T得到Class
 *
 * @author lhy
 * @version 1.0.0 2023/4/23
 */
public class BytesWrapper<T> implements Cloneable {

  private T value;

  public BytesWrapper() {
  }

  public BytesWrapper(T value) {
    this.value = value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  @SuppressWarnings("unchecked")
  @Override
  public BytesWrapper<T> clone() {
    try {
      return (BytesWrapper<T>) super.clone();
    } catch (CloneNotSupportedException e) {
      return new BytesWrapper<>();
    }
  }
}
