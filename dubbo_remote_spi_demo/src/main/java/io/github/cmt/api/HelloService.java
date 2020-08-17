package io.github.cmt.api;

import com.souche.extensionlite.common.Mode;
import com.souche.extensionlite.common.annotation.SPI;

/**
 * @author shengchaojie
 * @date 2020/8/15
 **/
@SPI(mode = Mode.TAG)
public interface HelloService {

    String hello();

}
