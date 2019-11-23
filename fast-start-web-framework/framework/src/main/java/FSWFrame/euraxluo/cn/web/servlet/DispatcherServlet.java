package FSWFrame.euraxluo.cn.web.servlet;

import FSWFrame.euraxluo.cn.web.handler.HandlerManager;
import FSWFrame.euraxluo.cn.web.handler.RouteHandler;

import javax.servlet.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.web.servlet
 * DispatcherServlet
 * 2019/11/23 12:16
 * author:Euraxluo
 * TODO
 */
public class DispatcherServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        for (RouteHandler routeHandler: HandlerManager.routeHandlerList){
            try {
                if (routeHandler.handle(req,res)){
                    return;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }

}
