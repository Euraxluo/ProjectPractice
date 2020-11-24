package com.wss.netty;

import com.wss.netty.utils.SpringUtil;
import org.n3r.idworker.Sid;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "com.wss.netty.dao")
//@ComponentScan(basePackages = {"com.wss.netty","org.n3r.idworker"})
public class Application {
    @Bean
    public SpringUtil getSpringUtil(){
        return new SpringUtil();
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
