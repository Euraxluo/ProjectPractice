package com.wss.netty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Wss_netty
 * com.wss.netty.config
 * SwaggerConfig
 * 2019/10/11 17:29
 * author:Euraxluo
 * TODO
 */
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.wss.netty.controllers", "com.wss.netty.pojo"})
public class SwaggerConfig {

    /**
     * 初始化创建Swagger Api
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 详细信息定制
                .apiInfo(apiInfo())
                .select()
                // 指定当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.wss.netty.controllers"))
//                .apis(RequestHandlerSelectors.basePackage("com.wss.netty.pojo"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                .title("Spring Netty 文档")
                .description("描述：接口文档")
//                .contact(new Contact("Fame-springBoot-Swagger2", null, null))
                .version("版本号: 1.0")
                .build();
    }
}