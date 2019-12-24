package cn.euraxluo.wcf.framework;

//import cn.euraxluo.wcf.Service.apis.Test;
import cn.euraxluo.wcf.framework.ApiServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

import java.lang.annotation.Target;
import java.util.Map;

/**
 * webflux-client-framework
 * cn.euraxluo.wcf.Service
 * AnnotationScannerConfigurer
 * 2019/12/24 13:27
 * author:Euraxluo
 * TODO
 */
//@Component
//public class AnnotationScannerConfigurer implements BeanDefinitionRegistryPostProcessor {
//
//    @Override
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
//        // 创建一个bean的定义类的对象
//        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Test.class);
//        // 将Bean 的定义注册到Spring环境
//        beanDefinitionRegistry.registerBeanDefinition("itest", rootBeanDefinition);
//    }
//
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        // bean的名字为key, bean的实例为value
//        Map<String, Object> beanMap = configurableListableBeanFactory.getBeansWithAnnotation(ApiServer.class);
//    }
//}
