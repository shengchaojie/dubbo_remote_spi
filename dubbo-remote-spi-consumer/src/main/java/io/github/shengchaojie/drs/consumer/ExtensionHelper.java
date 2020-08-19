package io.github.shengchaojie.drs.consumer;

import java.util.function.Supplier;

/**
 * @author shengchaojie
 * @date 2020/8/17
 **/
public class ExtensionHelper {

    public static <T> T execute(String bizCode, Supplier<T> supplier){
        BusinessContext.setBizCode(bizCode);

        try{
            return supplier.get();
        }finally {
            BusinessContext.clear();
        }

    }

}
