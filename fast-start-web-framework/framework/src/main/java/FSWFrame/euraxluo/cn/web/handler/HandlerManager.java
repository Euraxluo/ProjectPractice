package FSWFrame.euraxluo.cn.web.handler;

import FSWFrame.euraxluo.cn.web.mvc.Controller;
import FSWFrame.euraxluo.cn.web.mvc.Param;
import FSWFrame.euraxluo.cn.web.mvc.Route;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.web.handler
 * HandlerManager
 * 2019/11/23 17:55
 * author:Euraxluo
 * TODO
 */
public class HandlerManager {
    public static List<RouteHandler> routeHandlerList = new ArrayList<>();
    public static void resolveRouteHandler(List<Class<?>> classList){
        for (Class<?> cls:classList) {
            if (cls.isAnnotationPresent(Controller.class)){
                parseHandlerFromController(cls);
            }
        }
    }
    private static void parseHandlerFromController(Class<?> cls) {
        Method[] methods = cls.getDeclaredMethods();
        for (Method method: methods) {
            if(!method.isAnnotationPresent(Route.class)){
                continue;
            }
            //find all method to @Route
            String uri = method.getDeclaredAnnotation(Route.class).value();
//            System.out.println(uri);
            List<String> paramNameList = new ArrayList<>();
            for (Parameter parameter:method.getParameters()){
                if(parameter.isAnnotationPresent(Param.class)){
                    paramNameList.add(parameter.getDeclaredAnnotation(Param.class).value());
                }
            }
            //param List to array
            String[] params = paramNameList.toArray(new String[paramNameList.size()]);
            //init a route handler
            RouteHandler routeHandler = new RouteHandler(uri,method,cls,params);
            //add a route handler to routeHandlerList
            HandlerManager.routeHandlerList.add(routeHandler);
        }
    }
}
