package cn.euraxluo.FSWFrame.web.handler;

import cn.euraxluo.FSWFrame.log.LogFactory;
import cn.euraxluo.FSWFrame.web.mvc.Controller;
import cn.euraxluo.FSWFrame.web.mvc.Param;
import cn.euraxluo.FSWFrame.web.mvc.RestController;
import cn.euraxluo.FSWFrame.web.mvc.Route;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.web.handler
 * HandlerManager
 * 2019/11/23 17:55
 * author:Euraxluo
 * TODO
 */
public class HandlerManager {
    private static Logger logger = LogFactory.getLogger();
    public static List<RouteHandler> routeHandlerList = new ArrayList<>();
    public static void resolveRouteHandler(List<Class<?>> classList){
        for (Class<?> cls:classList) {
            if (cls.isAnnotationPresent(Controller.class) || cls.isAnnotationPresent(RestController.class)){
                parseHandlerFromController(cls);
            }
        }
        logger.info(LogFactory.fs("Route Handler add{size}",routeHandlerList.size()));
    }
    private static void parseHandlerFromController(Class<?> cls) {
        Method[] methods = cls.getDeclaredMethods();
        for (Method method: methods) {
            if(!method.isAnnotationPresent(Route.class)){
                continue;
            }
            //find all method to @Route
            String[] methodParams = method.getDeclaredAnnotation(Route.class).value().split(":");
            String uri = methodParams[0];
            String httpMethod = methodParams.length==2?methodParams[1]:"GET";
            List<String> paramNameList = new ArrayList<>();
            for (Parameter parameter:method.getParameters()){
                if(parameter.isAnnotationPresent(Param.class)){
                    paramNameList.add(parameter.getDeclaredAnnotation(Param.class).value());
                }
            }
            //param List to array
            String[] params = paramNameList.toArray(new String[paramNameList.size()]);
            //init a route handler
            RouteHandler routeHandler = new RouteHandler(uri,method,cls,params,httpMethod.toUpperCase());
            //add a route handler to routeHandlerList
            HandlerManager.routeHandlerList.add(routeHandler);
        }
    }
}
