package io.github.shengchaojie.drs.test.consumer;

/**
 * @author shengchaojie
 * @date 2020/8/16
 **/

import io.github.shengchaojie.drs.consumer.annotation.EnableExtensionConsumer;
import io.github.shengchaojie.drs.consumer.annotation.SPIRegistrar;
import io.github.shengchaojie.drs.test.api.HelloService;
import org.springframework.context.annotation.Configuration;

@EnableExtensionConsumer
@SPIRegistrar({HelloService.class})
@Configuration
public class Config {
}
