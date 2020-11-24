package cn.euraxluo.wcf.Service.controllers;

import cn.euraxluo.wcf.Service.apis.IUserApi;
import cn.euraxluo.wcf.Service.entity.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;


/**
 * webflux-client-framework
 * cn.euraxluo.wcf.Service.controllers
 * TestController
 * 2019/11/28 21:03
 * author:Euraxluo
 * TODO
 */
@Controller
@RequestMapping("/user")
public class TestController {
    @Autowired
    private IUserApi iUserApi;

    @GetMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public void test(){
        Flux<UserBean> userBeanFlux = iUserApi.getAllUser();
        userBeanFlux.subscribe(System.out::println);
        return;
    }

}
