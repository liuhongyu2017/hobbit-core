package org.hobbit.core.tool.jackson.serizlizer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.hobbit.core.tool.jackson.Symbol;
import org.hobbit.core.tool.jackson.annotation.Desensitize;
import org.hobbit.core.tool.jackson.desensitization.Desensitization;
import org.hobbit.core.tool.jackson.desensitization.DesensitizationFactory;
import org.hobbit.core.tool.jackson.desensitization.StringDesensitization;

/**
 * @author lhy
 * @version 1.0.0 2022/3/30 10:32
 */
@Slf4j
public class ObjectDesensitizeSerializer extends StdSerializer<Object> implements
    ContextualSerializer {

  private transient Desensitization<Object> desensitization;

  protected ObjectDesensitizeSerializer() {
    super(Object.class);
  }

  public Desensitization<Object> getDesensitization() {
    return desensitization;
  }

  public void setDesensitization(Desensitization<Object> desensitization) {
    this.desensitization = desensitization;
  }

  @Override
  public JsonSerializer<Object> createContextual(SerializerProvider prov, BeanProperty property) {
    Desensitize annotation = property.getAnnotation(Desensitize.class);
    return createContextual(annotation.desensitization());
  }

  @SuppressWarnings("unchecked")
  public JsonSerializer<Object> createContextual(Class<? extends Desensitization<?>> clazz) {
    ObjectDesensitizeSerializer serializer = new ObjectDesensitizeSerializer();
    if (clazz != StringDesensitization.class) {
      serializer.setDesensitization(
          (Desensitization<Object>) DesensitizationFactory.getDesensitization(clazz));
    }
    return serializer;
  }

  @Override
  public void serialize(Object value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    Desensitization<Object> objectDesensitization = getDesensitization();
    if (objectDesensitization != null) {
      try {
        gen.writeObject(objectDesensitization.desensitize(value));
      } catch (Exception e) {
        // 脱敏失败，直接进行序列化
        log.warn("脱敏失败：{}", e.getMessage());
        gen.writeObject(value);
      }
    } else if (value instanceof String) {
      // 如果没有具体脱敏实现，将所有字符串转变为脱敏替代字符
      gen.writeString(Symbol.getSymbol(((String) value).length(), Symbol.STAR));
    } else {
      // 脱敏失败，直接进行序列化
      log.warn("脱敏失败：desensitization is null");
      gen.writeObject(value);
    }
  }
}
