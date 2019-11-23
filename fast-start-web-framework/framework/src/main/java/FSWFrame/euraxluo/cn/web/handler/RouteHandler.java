package FSWFrame.euraxluo.cn.web.handler;

import FSWFrame.euraxluo.cn.beans.BeanFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    public RouteHandler(String uri, Method method, Class<?> controller, String[] args) {
        this.uri = uri;
        this.method = method;
        this.controller = controller;
        this.args = args;
    }

    public boolean handle(ServletRequest req, ServletResponse res) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        String reqUri =  ((HttpServletRequest) req).getRequestURI();
        if (!uri.equals(reqUri)){
            return false;
        }
        Object[] parameters = new Object[args.length];
        for (int i=0;i<args.length;i++){
            parameters[i] = req.getParameter(args[i]);
        }
        Object ctl = BeanFactory.getBean(controller);
        Object resp = method.invoke(ctl,parameters);
        res.getWriter().println(resp.toString());
        return true;
    }
}
