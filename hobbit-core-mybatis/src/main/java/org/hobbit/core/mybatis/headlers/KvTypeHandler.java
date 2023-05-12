package org.hobbit.core.mybatis.headlers;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.hobbit.core.tool.support.Kv;

/**
 * @author lhy
 * @version 1.0.0 2023/5/12
 */
@Slf4j
@MappedTypes({Kv.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class KvTypeHandler extends AbstractJsonTypeHandler<Kv> {

  private static ObjectMapper OBJECT_MAPPER;

  @Override
  protected Kv parse(String json) {
    try {
      return getObjectMapper().readValue(json, new TypeReference<>() {
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected String toJson(Kv obj) {
    try {
      return getObjectMapper().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static ObjectMapper getObjectMapper() {
    if (null == OBJECT_MAPPER) {
      OBJECT_MAPPER = new ObjectMapper();
    }
    return OBJECT_MAPPER;
  }

  public static void setObjectMapper(ObjectMapper objectMapper) {
    Assert.notNull(objectMapper, "ObjectMapper should not be null");
    KvTypeHandler.OBJECT_MAPPER = objectMapper;
  }
}
