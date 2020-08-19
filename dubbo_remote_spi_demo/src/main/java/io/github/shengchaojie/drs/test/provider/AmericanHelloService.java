package io.github.shengchaojie.drs.test.provider;

import io.github.shengchaojie.drs.common.annotation.Extension;
import io.github.shengchaojie.drs.test.api.HelloService;

/**
 * @author shengchaojie
 * @date 2020/8/15
 **/
@Extension(bizCode = "american")
public class AmericanHelloService implements HelloService {
    @Override
    public String hello() {
        return "hello";
    }
}
