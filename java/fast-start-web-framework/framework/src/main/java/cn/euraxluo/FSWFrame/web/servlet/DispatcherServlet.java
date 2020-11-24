package cn.euraxluo.FSWFrame.web.servlet;

import cn.euraxluo.FSWFrame.exception.BadRequestException;
import cn.euraxluo.FSWFrame.exception.FileNotFoundException;
import cn.euraxluo.FSWFrame.log.LogFactory;
import cn.euraxluo.FSWFrame.web.handler.HandlerManager;
import cn.euraxluo.FSWFrame.web.handler.RouteHandler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.web.servlet
 * DispatcherServlet
 * 2019/11/23 12:16
 * author:Euraxluo
 * TODO
 */
public class DispatcherServlet implements Servlet {
    private static Logger logger = LogFactory.getLogger();
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws IOException {
        boolean routeFindFlag = false;
        int sc = HttpServletResponse.SC_NOT_FOUND;
        for (RouteHandler routeHandler: HandlerManager.routeHandlerList){
            try {
                if (routeHandler.handle(req,res)){
                    routeFindFlag = true;
                    return;
                }
            } catch (IllegalAccessException e) {
                logger.warning(e.getMessage());
                sc = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                logger.warning(e.getMessage());
                sc = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                e.printStackTrace();
            }catch (BadRequestException e) {
                routeFindFlag = true;
                sc = HttpServletResponse.SC_BAD_REQUEST;
            }
        }

        if (!routeFindFlag){
            try {
                throw new FileNotFoundException(LogFactory.fs("URI:{uri}",((HttpServletRequest) req).getRequestURI()));
            } catch (FileNotFoundException e) {
            }
        }
        HttpServletResponse resp = (HttpServletResponse) res;
        resp.sendError(sc);


    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }

}
