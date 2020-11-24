package cn.euraxluo.wcf.framework;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * webflux-client-framework
 * cn.euraxluo.wcf.framework
 * ApiServer
 * 2019/11/28 19:05
 * author:Euraxluo
 * TODO
 */
//@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiServer {
//    @AliasFor(annotation = Component.class)
    String value() default "";
}
