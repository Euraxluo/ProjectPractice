package cn.euraxluo.FSWFrame.starter;

import cn.euraxluo.FSWFrame.beans.BeanFactory;
import cn.euraxluo.FSWFrame.core.ClassScanner;
import cn.euraxluo.FSWFrame.core.ConfScanner;
import cn.euraxluo.FSWFrame.exception.IOCException;
import cn.euraxluo.FSWFrame.log.LogFactory;
import cn.euraxluo.FSWFrame.web.handler.HandlerManager;
import cn.euraxluo.FSWFrame.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.starter
 * FSWFrameApplication
 * 2019/10/29 22:11
 * author:Euraxluo
 * TODO
 */
public class FSWFrameApplication {
    private static Logger logger = LogFactory.getLogger();
    public static void run(Class<?> cls,String[] args){
        System.err.println("FSWFrameApplication starting...");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
            BeanFactory.initBean(classList);
            HandlerManager.resolveRouteHandler(classList);
        } catch (IOException e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        } catch (LifecycleException e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        } catch (IOCException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
    }
}
