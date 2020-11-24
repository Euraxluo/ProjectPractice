package cn.euraxluo.FSWFrame.web.server;

import cn.euraxluo.FSWFrame.log.LogFactory;
import cn.euraxluo.FSWFrame.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import java.util.logging.Logger;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.web.server
 * TomcatServer
 * 2019/11/23 11:46
 * author:Euraxluo
 * TODO
 */
public class TomcatServer {
    private int port=6699;
    private Tomcat tomcat;
    private String[] args;//启动参数
    private Logger logger =LogFactory.getLogger();
    public TomcatServer(String[] args){
        this.args = args;
    }
    public void startServer() throws LifecycleException {
        //tomcat init
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.start();
        //context init
        Context context = new StandardContext();
        context.setPath("");//set path null
        //init addLifecycleListener
        context.addLifecycleListener(new Tomcat.FixContextListener());
        //init servlet
        DispatcherServlet servlet = new DispatcherServlet();
        //servlet add to context
        Tomcat.addServlet(context,"dispatcherServlet",servlet).setAsyncSupported(true);
        context.addServletMappingDecoded("/","dispatcherServlet");
        // context add to(rely on) Host
        tomcat.getHost().addChild(context);
        Thread awaitThread = new Thread("tomcat_await_thread"){
            @Override
            public void run(){
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        awaitThread.setDaemon(false);//设置为非守护类型
        awaitThread.start();
        logger.info(LogFactory.fs("Tomcat Server running at port{port},pattern{pattern}",port,"/"));
    }
}
