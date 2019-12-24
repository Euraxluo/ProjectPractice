package cn.euraxluo.wcf.framework.interfaces;

import org.springframework.stereotype.Service;

/**
 * webflux-client-framework
 * cn.euraxluo.wcf.framework.interfaces
 * ProxyCreator
 * 2019/11/29 20:42
 * author:Euraxluo
 * 创建代理类接口
 */
public interface ProxyCreator {
    /**
     * 创建代理类
     * @param type
     * @return
     */
      Object createProxy(Class<?> type);
}
