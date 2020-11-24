package com.wss.netty.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Wss_netty
 * com.wss.netty.demo.controllers
 * HelloController
 * 2019/10/10 18:08
 * author:Euraxluo
 * TODO
 */
@RestController
@RequestMapping("u")
@Api(value = "用户信息", tags = { "用户" })
public class HelloController {
    private static Logger log = Logger.getLogger(HelloController.class);
    @GetMapping("/hello")
    @ApiOperation(value = "用户信息分页查询")
    public String hello(@RequestParam @NotBlank String msg){
    
        return msg;
    }

    @GetMapping("/he")
    @ApiOperation(value = "222")
    public String he(){
        return "Hello World";
    }
}
