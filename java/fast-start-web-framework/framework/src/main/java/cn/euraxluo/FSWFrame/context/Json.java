package cn.euraxluo.FSWFrame.context;

import java.lang.annotation.*;

/**
 * fast-start-web-framework
 * cn.euraxluo.FSWFrame.context
 * WrapJson
 * 2019/11/25 13:29
 * author:Euraxluo
 * TODO
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)//target is method
public @interface Json {
    String value();//save uri
}
