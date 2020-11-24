package com.webflux.demo.handlers;

import com.webflux.demo.domain.User;
import com.webflux.demo.exception.CheckException;
import com.webflux.demo.repository.UserRepository;
import com.webflux.demo.validation.UserNameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

/**
 * webFlux-mapping
 * com.webflux.demo.handlers
 * UserHandler
 * 2019/11/28 10:41
 * author:Euraxluo
 * TODO
 */
@Component
public class UserHandler {

    private  final UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 获取所有用户
     * @param request
     * @return
     */
    public Mono<ServerResponse> getAllUser(ServerRequest request){
        return ok().contentType(APPLICATION_JSON_UTF8)
                .body(this.userRepository.findAll(), User.class);
    }

    /**
     * 创建用户
     * @param request
     * @return
     */
    public Mono<ServerResponse> createUser(ServerRequest request){
        Mono<User> userMono = request.bodyToMono(User.class);
//        return ok().contentType(APPLICATION_JSON_UTF8)
//                .body(this.userRepository.saveAll(userMono), User.class);

        /**
         * 参数校验: 在整个操作中,不能使用消费者进行操作,需要使用流:flatMap/Map 来进行额外的操作(参数校验)
         * 注意 .body中this.userRepository.save(u)是保存的map的u
         */
        return userMono.flatMap(u->{
            //校验代码
            UserNameValidator.UserNameValidator(u.getName());
            return ok().contentType(APPLICATION_JSON_UTF8)
                    .body(this.userRepository.save(u), User.class);
        });
    }

    /**
     * 根据Id删除用户
     * @param request
     * @return
     */
    public Mono<ServerResponse> deleteUserById(ServerRequest request){
        String id =  request.pathVariable("id");
        return this.userRepository.findById(id)
                .flatMap(user->this.userRepository.delete(user))
                .then(ok().contentType(APPLICATION_JSON_UTF8).build())//么有body,调用build()
                .switchIfEmpty(notFound().build());
    }
}
