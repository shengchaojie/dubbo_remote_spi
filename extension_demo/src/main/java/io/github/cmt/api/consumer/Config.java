package io.github.cmt.api.consumer;

/**
 * @author shengchaojie
 * @date 2020/8/16
 **/

import com.souche.extensionlite.business.annotation.EnableExtensionConsumer;
import com.souche.extensionlite.business.annotation.SPIRegistrar;
import io.github.cmt.api.HelloService;
import org.springframework.context.annotation.Configuration;

@EnableExtensionConsumer
@SPIRegistrar({HelloService.class})
@Configuration
public class Config {
}
