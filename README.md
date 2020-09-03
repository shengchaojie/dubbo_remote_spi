# dubbo_remote_spi
这是一个基于dubbo框架做的一个远程SPI的框架。

所谓远程SPI,相对于传统的本地SPI,把扩展点的实现放到了应用外部，也就是由别人来实现你的接口。

举一个订单中心的场景

针对下单中的运费计算

有的业务方不包邮，取时价

有的业务方，针对新人第一次免邮

有的业务方，第一次免邮，后面满xx包邮

有的业务方...

业务方越来越多，业务更改越来越频繁，中台的吞吐成为瓶颈，我们需要把这些业务还给业务方。

使用远程SPI的好处是，平台与业务分离，平台只做抽象，业务方负责具体实现。

# 原理

针对普通远程接口的调用，Dubbo框架会给我们生成调用的代理。

而对于远程SPI接口，我们需要根据我们运行时的业务身份，选择具体实现进行调用，本框架的作用就是在Dubbo框架基础之上生成这个代理。

# 使用

本框架分为两部分

针对平台端，只需要抽象业务扩展点接口，使用consumer依赖

```
<dependency>
    <groupId>io.github.cmt.dubbo-remote-spi</groupId>
    <artifactId>consumer</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

针对业务端，实现扩展点接口，使用provider依赖

```
<dependency>
    <groupId>io.github.cmt.dubbo-remote-spi</groupId>
    <artifactId>provider</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 平台端
1. 定义spi接口，给接口注解@SPI
```
@SPI(mode = Mode.TAG)
public interface HelloService {

    String hello();

}
```
mode用于SPI路由模式

如果为Mode.TAG,业务端在暴露SPI实现的时候通过tag打标
如果为Mode.GROUP,业务端在暴露SPI实现的时候通过group打标

2. 激活配置
```
@EnableExtensionConsumer
@SPIRegistrar({HelloService.class})
@Configuration
public class Config {
}
```

通过@EnableExtensionConsumer激活平台端SPI功能
通过@SPIRegistrar注册SPI接口

3. 使用SPI

方式一(推荐)
```
ExtensionHelper.execute("chinese",()-> {
    String hello = helloService.hello();
    System.out.println(hello);
    return hello;
});
```
方式二
```
BusinessContext.setBizCode("chinese");
System.out.println(helloService.hello());
```
4. 默认实现

针对SPI接口，平台方可提供默认实现，业务方不需要实现所有SPI

```
@DefaultExtension
public class DefaultHelloService implements HelloService {
    @Override
    public String hello() {
        return "default";
    }
}
```

加@DefaultExtension注解即可

## 业务端

1. 激活配置
```
@EnableExtensionProvider
@Configuration
public class ProviderConfig {
}
```

通过@EnableExtensionProvider激活远程SPI特性

2. 实现远程SPI接口
```
@DefaultExtension
@Extension(bizCode = "chinese")
public class ChineseHelloService implements HelloService {
    @Override
    public String hello() {
        return "你好";
    }
}
```
通过@Extension对远程SPI实现进行打标，bizCode为具体的业务身份