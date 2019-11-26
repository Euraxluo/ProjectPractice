package cn.euraxluo.FSWFrame.web.mvc;

import java.lang.annotation.*;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.web.mvc
 * Route
 * 2019/11/23 12:40
 * author:Euraxluo
 * TODO
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)//target is method
public @interface Route {
    String value();//save uri
}
