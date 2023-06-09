package org.hobbit.core.mybatis.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hobbit.core.launch.props.HobbitPropertySource;
import org.hobbit.core.mybatis.injector.HobbitSqlInjector;
import org.hobbit.core.mybatis.intercept.QueryInterceptor;
import org.hobbit.core.mybatis.plugins.HobbitPaginationInterceptor;
import org.hobbit.core.mybatis.props.MybatisPlusProperties;
import org.hobbit.core.mybatis.resolver.PageArgumentResolver;
import org.hobbit.core.tool.utils.ObjectUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mybatis-plus 配置
 *
 * @author lhy
 * @version 1.0.0 2023/5/4
 */
@AutoConfiguration
@RequiredArgsConstructor
@MapperScan("org.hobbit.**.mapper.**")
@EnableConfigurationProperties(MybatisPlusProperties.class)
@HobbitPropertySource("classpath:/hobbit-mybatis.yml")
public class MybatisPlusConfiguration implements WebMvcConfigurer {

  /**
   * mybatis-plus 拦截器集合
   */
  @Bean
  @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
  public MybatisPlusInterceptor mybatisPlusInterceptor(
      ObjectProvider<QueryInterceptor[]> queryInterceptors,
      MybatisPlusProperties mybatisPlusProperties
  ) {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    // 配置分页拦截器
    HobbitPaginationInterceptor paginationInterceptor = new HobbitPaginationInterceptor();
    // 配置自定义查询拦截器
    QueryInterceptor[] queryInterceptorArray = queryInterceptors.getIfAvailable();
    if (ObjectUtil.isNotEmpty(queryInterceptorArray)) {
      AnnotationAwareOrderComparator.sort(queryInterceptorArray);
      paginationInterceptor.setQueryInterceptors(queryInterceptorArray);
    }
    paginationInterceptor.setMaxLimit(mybatisPlusProperties.getPageLimit());
    paginationInterceptor.setOverflow(mybatisPlusProperties.getOverflow());
    paginationInterceptor.setOptimizeJoin(mybatisPlusProperties.getOptimizeJoin());
    interceptor.addInnerInterceptor(paginationInterceptor);
    return interceptor;
  }

  /**
   * sql 注入
   */
  @Bean
  @ConditionalOnMissingBean(ISqlInjector.class)
  public ISqlInjector sqlInjector() {
    return new HobbitSqlInjector();
  }

  /**
   * page 解析器
   */
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new PageArgumentResolver());
  }
}
