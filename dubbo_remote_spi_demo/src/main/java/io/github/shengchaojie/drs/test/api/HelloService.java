package io.github.shengchaojie.drs.test.api;

import io.github.shengchaojie.drs.common.Mode;
import io.github.shengchaojie.drs.common.annotation.SPI;

/**
 * @author shengchaojie
 * @date 2020/8/15
 **/
@SPI(mode = Mode.TAG)
public interface HelloService {

    String hello();

}
