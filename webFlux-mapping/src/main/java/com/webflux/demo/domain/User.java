package com.webflux.demo.domain;

import com.webflux.demo.validation.UserName;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

/**
 * webFlux
 * com.webflux.demo.domain
 * User
 * 2019/11/27 21:49
 * author:Euraxluo
 * @Data 简单生成实体类
 */
@Data
@Document(collection = "user")
public class  User {
    @Id
    private String id;
    @UserName
    private String name;
    @Range(min = 18,max = 100)
    private int age;
}
