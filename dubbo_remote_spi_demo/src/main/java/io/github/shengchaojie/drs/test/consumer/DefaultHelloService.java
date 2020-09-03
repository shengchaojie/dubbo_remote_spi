package io.github.shengchaojie.drs.test.consumer;

import io.github.shengchaojie.drs.common.annotation.DefaultExtension;
import io.github.shengchaojie.drs.test.api.HelloService;

/**
 * @author shengchaojie
 * @date 2020/9/3
 **/
@DefaultExtension
public class DefaultHelloService implements HelloService {
    @Override
    public String hello() {
        return "default";
    }
}
