package cn.euraxluo.FSWFrame.MybatisPlugin;

import java.lang.annotation.*;

/**
 * fast-start-web-framework
 * cn.euraxluo.FSWFrame.MybatisPlugin
 * MapperScan
 * 2019/11/26 14:09
 * author:Euraxluo
 * TODO
 */
@Documented //origin annotation
@Retention(RetentionPolicy.RUNTIME)//save on runtime
@Target(ElementType.TYPE)
public @interface MapperScan {
    String value();
}
