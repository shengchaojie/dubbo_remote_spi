package io.github.cmt.api.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author shengchaojie
 * @date 2020/8/16
 **/
public class ProviderMain {

    public static void main(String[] args) throws InterruptedException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("provider.xml");

        synchronized (ProviderMain.class){
            ProviderMain.class.wait();
        }

    }
}
