package io.github.shengchaojie.drs.provider.dubbo;

import io.github.shengchaojie.drs.common.DubboConfigUtils;
import io.github.shengchaojie.drs.common.ExtensionRegistry;
import io.github.shengchaojie.drs.common.Mode;
import io.github.shengchaojie.drs.common.SpiException;
import io.github.shengchaojie.drs.common.annotation.DefaultExtension;
import io.github.shengchaojie.drs.common.annotation.SPI;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ServiceConfig;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * dubbo服务暴露
 *
 * @author yonghuang
 */
@Slf4j
public class DubboServiceExporter {

    @Getter
    private static final DubboServiceExporter INSTNACE = new DubboServiceExporter();

    Boolean isSPIInterface(Class<?> clazz){
        return clazz.isAnnotationPresent(SPI.class);
    }

    public void export(Object bean, String bizCode, List<Class<?>> spiInterfaces) {
        for (Class<?> spiInterface: spiInterfaces){
            if(isGroupExport(spiInterface)){
                groupExport(bean,bizCode,spiInterface);
            }else{
                tagExport(bean,bizCode,spiInterface);
            }
        }
    }

    private boolean isGroupExport(Class<?> spiInterfaces){
        SPI spi = AnnotationUtils.getAnnotation(spiInterfaces,SPI.class);
        return  Mode.GROUP.equals(spi.mode());
    }

    private void groupExport(Object bean, String bizCode, Class<?> spiInterfaces) {
        ServiceConfig<Object> serviceConfig = new ServiceConfig<>();
        serviceConfig.setGroup(bizCode);
        serviceConfig.setInterface(spiInterfaces);
        serviceConfig.setApplication(DubboConfigUtils.getApplicationConfig());
        serviceConfig.setProvider(DubboConfigUtils.getProviderConfig());
        serviceConfig.setRegistries(DubboConfigUtils.getRegestries());
        serviceConfig.setProtocols(DubboConfigUtils.getProtocolConfigs());
        serviceConfig.setRef(bean);
        serviceConfig.export();

        ExtensionRegistry.getINSTANCE().registry(spiInterfaces,bizCode,bean);

        DefaultExtension defaultExtension = AopUtils.getTargetClass(bean).getAnnotation(DefaultExtension.class);
        if(Objects.nonNull(defaultExtension)){
            ExtensionRegistry.getINSTANCE().registryDefault(spiInterfaces.getName(),bean);
        }

    }

    private void tagExport(Object bean, String bizCode, Class<?> spiInterfaces) {
        ServiceConfig<Object> serviceConfig = new ServiceConfig<>();
        serviceConfig.setTag(bizCode);
        serviceConfig.setInterface(spiInterfaces);
        serviceConfig.setApplication(DubboConfigUtils.getApplicationConfig());
        serviceConfig.setProvider(DubboConfigUtils.getProviderConfig());
        serviceConfig.setRegistries(DubboConfigUtils.getRegestries());
        serviceConfig.setProtocol(DubboConfigUtils.getTagRouteProtocol());
        serviceConfig.setRef(bean);
        serviceConfig.export();

        ExtensionRegistry.getINSTANCE().registry(spiInterfaces,bizCode,bean);

        DefaultExtension defaultExtension = AopUtils.getTargetClass(bean).getAnnotation(DefaultExtension.class);
        if(Objects.nonNull(defaultExtension)){
            ExtensionRegistry.getINSTANCE().registryDefault(spiInterfaces.getName(),bean);
        }
    }
}
