package org.hobbit.core.loadbalancer.config;

import org.hobbit.core.loadbalancer.props.HobbitLoadBalancerProperties;
import org.hobbit.core.loadbalancer.rule.GrayscaleLoadBalancer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientSpecification;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

/**
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
@AutoConfiguration(before = LoadBalancerClientConfiguration.class)
@EnableConfigurationProperties(HobbitLoadBalancerProperties.class)
@ConditionalOnProperty(value = HobbitLoadBalancerProperties.PROPERTIES_PREFIX
    + ".enabled", havingValue = "true", matchIfMissing = true)
@Order(HobbitLoadBalancerConfiguration.REACTIVE_SERVICE_INSTANCE_SUPPLIER_ORDER)
public class HobbitLoadBalancerConfiguration {

  public static final int REACTIVE_SERVICE_INSTANCE_SUPPLIER_ORDER = 193827465;

  @Bean
  public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(
      Environment environment,
      LoadBalancerClientFactory loadBalancerClientFactory,
      HobbitLoadBalancerProperties hobbitLoadBalancerProperties) {
    String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
    return new GrayscaleLoadBalancer(
        loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class),
        hobbitLoadBalancerProperties);
  }

  @Bean
  public LoadBalancerClientSpecification loadBalancerClientSpecification() {
    return new LoadBalancerClientSpecification("default.hobbitLoadBalancerConfiguration",
        new Class[]{HobbitLoadBalancerConfiguration.class});
  }
}
