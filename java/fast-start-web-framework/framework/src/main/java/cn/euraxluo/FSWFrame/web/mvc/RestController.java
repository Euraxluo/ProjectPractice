package cn.euraxluo.FSWFrame.web.mvc;

import cn.euraxluo.FSWFrame.context.WrapJson;

import java.lang.annotation.*;

/**
 * fast-start-web-framework
 * cn.euraxluo.FSWFrame.web.mvc
 * RestController
 * 2019/11/25 13:50
 * author:Euraxluo
 * TODO
 * Imitating spring
 * WrapJson(vale = "type")
 * WrapJson(vale = "method"):default method
 * WrapJson have some Subclass
 * the about 'type' sub Class can add WrapJson(vale = "method") to every methods
 *
 * my method :
 * if cls.isA.. have @RestController ,the every method do the @WrapJson
 */
@Documented //origin annotation
@Retention(RetentionPolicy.RUNTIME)//save on runtime
@Target(ElementType.TYPE)
@Controller
//@WrapJson
public @interface RestController {
}
