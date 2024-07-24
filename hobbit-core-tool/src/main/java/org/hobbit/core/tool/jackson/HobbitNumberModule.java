package org.hobbit.core.tool.jackson;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 大整数序列化为 String 字符串，避免浏览器丢失精度
 *
 * <p>
 * 前端建议采用： bignumber 库： https://github.com/MikeMcl/bignumber.js decimal.js 库：
 * https://github.com/MikeMcl/decimal.js
 * </p>
 *
 * @author L.cm
 */
public class HobbitNumberModule extends SimpleModule {

  private static final long serialVersionUID = 1L;
  public static final HobbitNumberModule INSTANCE = new HobbitNumberModule();

  public HobbitNumberModule() {
    super(HobbitNumberModule.class.getName());
    // Long 和 BigInteger 采用定制的逻辑序列化，避免超过js的精度
    this.addSerializer(Long.class, BigNumberSerializer.instance);
    this.addSerializer(Long.TYPE, BigNumberSerializer.instance);
    this.addSerializer(BigInteger.class, BigNumberSerializer.instance);
    // BigDecimal 采用 toString 避免精度丢失，前端采用 decimal.js 来计算。
    this.addSerializer(BigDecimal.class, ToStringSerializer.instance);
  }
}
