package com.webflux.demo.repository;

import com.webflux.demo.domain.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * webFlux
 * com.webflux.demo.repository
 * UserRepository
 * 2019/11/27 21:56
 * author:Euraxluo
 * Repository 存储层bean
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {
    /**
     * 根据年龄查找用户
     * @param start
     * @param end
     * @return
     */
    Flux<User> findByAgeBetween(int start,int end);

    @Query("{'age':{'$gte':25,'$lte':50}}")
    Flux<User> primeUser();
}
