# auto 代码自动生成

## 参考

Google Auto: https://github.com/google/auto

Spring 5 -
spring-context-indexer：https://github.com/spring-projects/spring-framework/tree/master/spring-context-indexer

## 1.Java SPI (Service Provider Interface)

SPI 全称：Service Provider Interface，是Java提供的一套用来被第三方实现或者扩展的接口，它可以用来启用框架扩展和替换组件。

面向的对象的设计里，我们一般推荐模块之间基于接口编程，模块之间不对实现类进行硬编码。一旦代码里涉及具体的实现类，就违反了可拔插的原则，如果需要替换一种实现，就需要修改代码。

为了实现在模块装配的时候不用在程序里动态指明，这就需要一种服务发现机制。java
spi就是提供这样的一个机制：为某个接口寻找服务实现的机制。这有点类似IOC的思想，将装配的控制权移到了程序之外。

SPI的作用就是为被扩展的API寻找服务实现。

SPI（Service Provider Interface），是JDK内置的一种
服务提供发现机制，可以用来启用框架扩展和替换组件，主要是被框架的开发人员使用，比如java.sql.Driver接口，其他不同厂商可以针对同一接口做出不同的实现，MySQL和PostgreSQL都有不同的实现提供给用户，而Java的SPI机制可以为某个接口寻zhao服务实现。Java中SPI机制主要思想是将装配的控制权移到程序之外，在模块化设计中这个机制尤其重要，其核心思想就是
解耦。

## 2.spring.factories

在Spring中也有一种类似与Java
SPI的加载机制。它在META-INF/spring.factories文件中配置接口的实现类名称，然后在程序中读取这些配置文件并实例化。这种自定义的SPI机制是Spring
Boot Starter实现的基础。

### 1.ApplicationContextInitializer

- 用于在spring容器刷新之前初始化Spring ConfigurableApplicationContext的回调接口。（剪短说就是在容器刷新之前调用该类的
  initialize 方法。并将 ConfigurableApplicationContext 类的实例传递给该方法）

- 通常用于需要对应用程序上下文进行编程初始化的web应用程序中。例如，根据上下文环境注册属性源或激活配置文件等。

- 可排序的（实现Ordered接口，或者添加@Order注解）

### 2.ApplicationListener

监听容器中发布的事件

### 3.SpringApplicationRunListener

SpringApplicationRunListener 接口的作用主要就是在Spring Boot
启动初始化的过程中可以通过SpringApplicationRunListener接口回调来让用户在启动的各个流程中可以加入自己的逻辑。
Spring Boot启动过程的关键事件（按照触发顺序）包括：

- 开始启动

- Environment构建完成

- ApplicationContext构建完成

- ApplicationContext完成加载

- ApplicationContext完成刷新并启动

- 启动完成

- 启动失败

```java
package org.springframework.boot;

public interface SpringApplicationRunListener {

  // 在run()方法开始执行时，该方法就立即被调用，可用于在初始化最早期时做一些工作
  void starting();

  // 当environment构建完成，ApplicationContext创建之前，该方法被调用
  void environmentPrepared(ConfigurableEnvironment environment);

  // 当ApplicationContext构建完成时，该方法被调用
  void contextPrepared(ConfigurableApplicationContext context);

  // 在ApplicationContext完成加载，但没有被刷新前，该方法被调用
  void contextLoaded(ConfigurableApplicationContext context);

  // 在ApplicationContext刷新并启动后，CommandLineRunners和ApplicationRunner未被调用前，该方法被调用
  void started(ConfigurableApplicationContext context);

  // 在run()方法执行完成前该方法被调用
  void running(ConfigurableApplicationContext context);

  // 当应用运行出错时该方法被调用
  void failed(ConfigurableApplicationContext context, Throwable exception);
}
```

### 4.FailureAnalyzer

FailureAnalyzer拦截启动时异常，将异常转换成更加易读的信息并包装成FailureAnalysis对象。Spring Boot
为应用上下文相关异常、JSR-303 validations 提供了此类分析器。

AbstractFailureAnalyzer是FailureAnalyzer的抽象实现，检查要处理的异常中是否存在指定的异常类型。扩展AbstractFailureAnalyzer可以实现自定义异常处理，如果无法处理该异常则可以返回null，以便其它FailureAnalyzer有机会处理该异常。

### 5.EnvironmentPostProcessor

在使用spring
boot做开发时，有时我们需要自定义环境变量或者编写第三方扩展点，可以使用EnvironmentPostProcessor，注意如果你只是基本的使用环境，就不需要看此文了 。
