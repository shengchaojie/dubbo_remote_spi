package io.github.shengchaojie.drs.test.consumerandprovider;

import io.github.shengchaojie.drs.consumer.BusinessContext;
import io.github.shengchaojie.drs.consumer.ExtensionHelper;
import io.github.shengchaojie.drs.test.api.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author shengchaojie
 * @date 2020/8/16
 **/
public class ConsumerAndProviderMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("consumerAndProvider.xml");
        HelloService helloService = applicationContext.getBean("io.github.shengchaojie.drs.test.api.HelloService",HelloService.class);
        BusinessContext.setBizCode("chinese");
        System.out.println(helloService.hello());
        BusinessContext.setBizCode("american");
        System.out.println(helloService.hello());
        BusinessContext.setBizCode("american222");
        System.out.println(helloService.hello());

        ExtensionHelper.execute("chinese",()-> {
            String hello = helloService.hello();
            System.out.println(hello);
            return hello;
        });
    }

}
