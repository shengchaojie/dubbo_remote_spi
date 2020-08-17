package io.github.cmt.api.consumer;

import com.souche.extensionlite.business.BusinessContext;
import com.souche.extensionlite.business.ExtensionHelper;
import io.github.cmt.api.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author shengchaojie
 * @date 2020/8/16
 **/
public class ConsumerMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("consumer.xml");
        HelloService helloService = applicationContext.getBean("io.github.cmt.api.HelloService",HelloService.class);
        BusinessContext.setBizCode("chinese");
        System.out.println(helloService.hello());
        BusinessContext.setBizCode("american");
        System.out.println(helloService.hello());

        ExtensionHelper.execute("chinese",()-> {
            String hello = helloService.hello();
            System.out.println(hello);
            return hello;
        });
    }

}
