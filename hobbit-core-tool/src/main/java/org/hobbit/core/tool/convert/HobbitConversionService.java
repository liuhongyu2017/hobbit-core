package org.hobbit.core.tool.convert;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/**
 * 类型 转换 服务，添加了 IEnum 转换
 *
 * @author L.cm
 */
public class HobbitConversionService extends ApplicationConversionService {

  @Nullable
  private static volatile HobbitConversionService SHARED_INSTANCE;

  public HobbitConversionService() {
    this(null);
  }

  public HobbitConversionService(@Nullable StringValueResolver embeddedValueResolver) {
    super(embeddedValueResolver);
    super.addConverter(new EnumToStringConverter());
    super.addConverter(new StringToEnumConverter());
  }

  /**
   * Return a shared default application {@code ConversionService} instance, lazily building it once
   * needed.
   * <p>
   * Note: This method actually returns an {@link HobbitConversionService} instance. However, the
   * {@code ConversionService} signature has been preserved for binary compatibility.
   *
   * @return the shared {@code HobbitConversionService} instance (never{@code null})
   */
  public static GenericConversionService getInstance() {
    HobbitConversionService sharedInstance = HobbitConversionService.SHARED_INSTANCE;
    if (sharedInstance == null) {
      synchronized (HobbitConversionService.class) {
        sharedInstance = HobbitConversionService.SHARED_INSTANCE;
        if (sharedInstance == null) {
          sharedInstance = new HobbitConversionService();
          HobbitConversionService.SHARED_INSTANCE = sharedInstance;
        }
      }
    }
    return sharedInstance;
  }

}
