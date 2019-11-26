package cn.euraxluo.FSWFrame.web.handler;

import cn.euraxluo.FSWFrame.beans.BeanFactory;
import cn.euraxluo.FSWFrame.context.Json;
import cn.euraxluo.FSWFrame.context.Respond;
import cn.euraxluo.FSWFrame.context.WrapJson;
import cn.euraxluo.FSWFrame.context.Result;
import cn.euraxluo.FSWFrame.exception.BadRequestException;
import cn.euraxluo.FSWFrame.log.LogFactory;
import cn.euraxluo.FSWFrame.web.mvc.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.web.handler
 * RouteHandler
 * 2019/11/23 17:50
 * author:Euraxluo
 * TODO
 */
public class RouteHandler {
    private String uri;
    private Method method;
    private Class<?> controller;
    private String[] args;
    private String httpMethod;
    private static Logger logger = LogFactory.getLogger();

    public RouteHandler(String uri, Method method, Class<?> controller, String[] args,String httpMethod) {
        this.uri = uri;
        this.method = method;
        this.controller = controller;
        this.args = args;
        this.httpMethod = httpMethod;
    }

    public boolean handle(ServletRequest req, ServletResponse res) throws IllegalAccessException, InvocationTargetException, IOException, BadRequestException {
        req.setCharacterEncoding("UTF-8");
        String reqUri =  ((HttpServletRequest) req).getRequestURI();
        if (!uri.equals(reqUri)){
            return false;
        }
        if(!httpMethod.equals(((HttpServletRequest) req).getMethod())){
            throw new BadRequestException(LogFactory.fs("URI:{Uri},Method:{Method}",reqUri,((HttpServletRequest) req).getMethod()));
        }
        logger.info(LogFactory.fs("uri:{uri},method:{method},controller:{controller}",this.uri,this.method,this.controller));

        Object[] parameters = new Object[args.length];
        for (int i=0;i<args.length;i++){
            parameters[i] = req.getParameter(args[i]);
        }
        Object ctl = BeanFactory.getBean(controller);
        Object resp = method.invoke(ctl,parameters);

        //priority: RestController > WrapJson > Json
        if (controller.isAnnotationPresent(RestController.class)){
            Respond.json(res,resp);
        }else if (method.isAnnotationPresent(WrapJson.class)){
            Result result = new Result(200,true,"OK",resp);
            Respond.json(res,result);
        }else if(method.isAnnotationPresent(Json.class)){
            String key =  method.getDeclaredAnnotation(Json.class).value();
            Respond.json(res,new HashMap<String,Object>(){{
                put(key,resp);
            }});
        } else {
            Respond.html(res,resp);
        }

        return true;
    }
}
