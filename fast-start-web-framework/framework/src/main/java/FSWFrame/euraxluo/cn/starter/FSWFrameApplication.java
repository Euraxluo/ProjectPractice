package FSWFrame.euraxluo.cn.starter;

import FSWFrame.euraxluo.cn.beans.Bean;
import FSWFrame.euraxluo.cn.beans.BeanFactory;
import FSWFrame.euraxluo.cn.core.ClassScanner;
import FSWFrame.euraxluo.cn.web.handler.HandlerManager;
import FSWFrame.euraxluo.cn.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.util.List;
/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.starter
 * FSWFrameApplication
 * 2019/10/29 22:11
 * author:Euraxluo
 * TODO
 */
public class FSWFrameApplication {
    public static void run(Class<?> cls,String[] args){
        System.err.println("FSWFrameApplication starting...");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
            BeanFactory.initBean(classList);
            HandlerManager.resolveRouteHandler(classList);
        } catch (LifecycleException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
