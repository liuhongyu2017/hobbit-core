package org.hobbit.core.loadbalancer.rule;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.hobbit.core.loadbalancer.props.HobbitLoadBalancerProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PatternMatchUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * LoadBalancer 负载规则
 *
 * @author lhy
 * @version 1.0.0 2023/4/11
 */
@RequiredArgsConstructor
@Slf4j
public class GrayscaleLoadBalancer implements ReactorServiceInstanceLoadBalancer {

  private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
  private final HobbitLoadBalancerProperties hobbitLoadBalancerProperties;

  @Override
  public Mono<Response<ServiceInstance>> choose(@SuppressWarnings("rawtypes") Request request) {
    ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
        .getIfAvailable(NoopServiceInstanceListSupplier::new);
    return supplier.get(request).next()
        .map(serviceInstances -> getInstanceResponse(serviceInstances, request));
  }

  /**
   * 自定义节点规则返回目标节点
   */
  private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances,
      Request<?> request) {
    // 注册中心无可用实例 返回空
    if (CollectionUtils.isEmpty(instances)) {
      return new EmptyResponse();
    }
    // 指定ip则返回满足ip的服务
    List<String> priorIpPattern = hobbitLoadBalancerProperties.getPriorIpPattern();
    if (!priorIpPattern.isEmpty()) {
      String[] priorIpPatterns = priorIpPattern.toArray(new String[0]);
      List<ServiceInstance> priorIpInstances = instances.stream().filter(
          (i -> PatternMatchUtils.simpleMatch(priorIpPatterns, i.getHost()))
      ).collect(Collectors.toList());
      if (!priorIpInstances.isEmpty()) {
        instances = priorIpInstances;
      }
    }
    return randomInstance(instances);
  }

  /**
   * 采用随机规则返回
   */
  private Response<ServiceInstance> randomInstance(List<ServiceInstance> instances) {
    // 若没有可用节点则返回空
    if (instances.isEmpty()) {
      return new EmptyResponse();
    }

    // 挑选随机节点返回
    int randomIndex = ThreadLocalRandom.current().nextInt(instances.size());
    ServiceInstance instance = instances.get(randomIndex % instances.size());
    return new DefaultResponse(instance);
  }
}
