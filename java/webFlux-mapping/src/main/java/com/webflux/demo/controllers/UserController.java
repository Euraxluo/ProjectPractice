package com.webflux.demo.controllers;

import com.webflux.demo.domain.User;
import com.webflux.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * webFlux
 * com.webflux.demo.controllers
 * UserController
 * 2019/11/27 22:25
 * author:Euraxluo
 * TODO
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository repository;
    public UserController(UserRepository repository){
        this.repository = repository;
    }

    /**
     * 以数组的行驶返回数据
     * @return
     */
    @GetMapping("/")
    public Flux<User> getAll(){
        return repository.findAll();
    }

    /**
     * 以SSE的形式返回数据
     * @return
     */
    @GetMapping(value = "/stream/all",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll(){
        return repository.findAll();
    }

    /**
     * 添加数据
     * @param user
     * @return
     */
    @PostMapping("/")
    public Mono<User> createuser(@Valid @RequestBody User user){
        /**
         * spring data jpa 中,新整和修改都是save,有@Id字段是修改,@Id为空是新增
         */
        user.setId(null);
        return this.repository.save(user);
    }

    /**
     * 根据Id删除
     * @param id
     * @return 200 / 404 {ResponseEntity<Void>}
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id){
//        repository.deleteById(id);//没有返回值,不能判断数据是否存在

        //当你要操作数据,并返回一个Mono,这个时候使用flatMap
        //不操作数据,只是转换数据,使用map
        return repository.findById(id)//先找出这个数据
                .flatMap(user -> repository.delete(user)//然后删除它,这时没有返回值了
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))//直接使用Mono.just,创建一个Mono,返回200tatus
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));//默认使用404status
    }

    /**
     * 修改数据
     * @return 200 + User/404
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@Valid @PathVariable("id")String id, @RequestBody User user){
        return repository.findById(id)//对于查出来的这个User
            //修改数据,返回Mono
            .flatMap(u->{
                u.setAge(user.getAge());
                u.setName(user.getName());
                return this.repository.save(u);
            })//作出修改,并save(换@Id表示修改)
            //转换数据
            .map(u->new ResponseEntity<User>(u,HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 获取数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUser(@PathVariable("id")String id){
        return repository.findById(id)//对于查出来的这个User
                //转换数据
                .map(u->new ResponseEntity<User>(u,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据年龄获取User
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/age/{start}/{end}")
    public Flux<User> findByAge(@PathVariable("start") int start,@PathVariable("end") int end){
        return repository.findByAgeBetween(start,end);
    }


    /**
     * 根据年龄获取User
     * @param start
     * @param end
     * @return SSE
     */
    @GetMapping(value = "/stream/age/{start}/{end}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamFindByAge(@PathVariable("start") int start,@PathVariable("end") int end){
        return repository.findByAgeBetween(start,end);
    }

    /**
     * 获取壮年User
     * @return
     */
    @GetMapping("/prime")
    public Flux<User> findPrimeUser(){
        return repository.primeUser();
    }

    /**
     * 获取壮年User
     * @return SSE
     */
    @GetMapping(value = "/stream/prime",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamFindPrimeUser(){
        return repository.primeUser();
    }

}
