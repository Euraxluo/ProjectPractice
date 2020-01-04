package cn.euraxluo.passbook.merchants.config;

import cn.euraxluo.passbook.merchants.security.AuthCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * merchants
 * cn.euraxluo.passbook.merchants.config
 * AuthCheckConfig
 * 2019/12/28 16:27
 * author:Euraxluo
 * 拦截器配置
 */
@SpringBootConfiguration
public class AuthCheckConfig extends WebMvcConfigurationSupport {
    @Autowired
    private AuthCheckInterceptor authCheckInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authCheckInterceptor)
                .addPathPatterns("/merchants/**");
        super.addInterceptors(registry);
    }

}
