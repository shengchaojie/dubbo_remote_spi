package io.github.cmt.api.provider;

import com.souche.extensionlite.common.annotation.Extension;
import io.github.cmt.api.HelloService;

/**
 * @author shengchaojie
 * @date 2020/8/15
 **/
@Extension(bizCode = "chinese")
public class ChineseHelloService implements HelloService {
    @Override
    public String hello() {
        return "你好";
    }
}
