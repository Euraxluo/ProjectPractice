package com.euraxluo.eurekaclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaclientApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(EurekaclientApplication.class, args);
        while (true){
            Thread.sleep(10);
        }
    }

}
