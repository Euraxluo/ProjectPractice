package FSWFrame.euraxluo.cn.web.mvc;

import java.lang.annotation.*;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.web.mvc
 * Param
 * 2019/11/23 12:42
 * author:Euraxluo
 * TODO
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)//target is parame of method
public @interface Param {
    String value();//save parame's key
}
