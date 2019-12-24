package cn.euraxluo.wcf.Service.apis;

import cn.euraxluo.wcf.Service.entity.UserBean;
import cn.euraxluo.wcf.framework.ApiServer;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * webflux-client-framework
 * cn.euraxluo.wcf.Service.apis
 * IUserApi
 * 2019/11/28 19:03
 * author:Euraxluo
 * TODO
 */
@ApiServer("http://localhost:8080/user")
public interface IUserApi {
    @GetMapping("/")
    Flux<UserBean> getAllUser();

    @GetMapping("/{id}")
    Mono<UserBean> getUserById(@PathVariable("id") String id);

    @DeleteMapping("/{id}")
    Mono<UserBean> deleteUserById(@PathVariable("id") String id);

    @PostMapping("/")
    Mono<UserBean> createUser(@RequestBody Mono<UserBean> user);

}
