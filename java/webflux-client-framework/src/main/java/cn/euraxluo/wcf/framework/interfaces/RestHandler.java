package cn.euraxluo.wcf.framework.interfaces;

import cn.euraxluo.wcf.framework.beans.MethodInfo;
import cn.euraxluo.wcf.framework.beans.ServerInfo;

/**
 * webflux-client-framework
 * cn.euraxluo.wcf.framework.interfaces
 * RestHandler
 * 2019/11/29 21:13
 * author:Euraxluo
 * rest 请求调用handler
 */
public interface RestHandler {
    /**
     * 调用Rest请求,返回结果
     * @param methodInfo
     * @return
     */
    Object invokeRest(MethodInfo methodInfo);

    /**
     * 初始化服务器信息
     * @param serverInfo
     */
    void init(ServerInfo serverInfo);
}
