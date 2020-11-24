package cn.euraxluo.FSWFrame;

import cn.euraxluo.FSWFrame.MybatisPlugin.MapperScan;
import cn.euraxluo.FSWFrame.starter.FSWFrameApplication;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn
 * Application
 * 2019/10/29 21:57
 * author:Euraxluo
 * TODO
 */
@MapperScan("cn.euraxluo.FSWFrame.dao")
public class Application {
    public static void main(String[] args) {
        System.err.println("FrameWork test starting...");
        FSWFrameApplication.run(Application.class,args);
    }
}
