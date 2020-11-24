package cn.euraxluo.wcf.Service;

import cn.euraxluo.wcf.Service.apis.IUserApi;
import cn.euraxluo.wcf.framework.interfaces.ProxyCreator;
import cn.euraxluo.wcf.framework.proxys.JDKProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScfTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScfTestApplication.class, args);
    }

    /**
     * 创建jdk代理工具类
     *
     * @return
     */
    @Bean
    ProxyCreator jdkProxyCreator() {
        return new JDKProxyCreator();
    }

    /**
     * 创建一个bean
     * @param proxyCreator
     * @return
     */
    @Bean
    FactoryBean<IUserApi> userApi(ProxyCreator proxyCreator) {
        return new FactoryBean<IUserApi>() {

            @Override
            public Class<?> getObjectType() {
                return IUserApi.class;
            }

            /**
             * 返回代理对象
             */
            @Override
            public IUserApi getObject() throws Exception {
                return (IUserApi) proxyCreator
                        .createProxy(this.getObjectType());
            }
        };
    }
}
