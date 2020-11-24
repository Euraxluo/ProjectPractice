package com.webflux.demo.handlers.exception;

import com.webflux.demo.exception.CheckException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * webFlux-mapping
 * com.webflux.demo.handlers
 * ExceptionHandler
 * 2019/11/28 11:50
 * author:Euraxluo
 * TODO
 */
@Component
@Order(-2)//调整优先级,越小越好
public class ExceptionHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        //设置响应头
        ServerHttpResponse response = serverWebExchange.getResponse();

        response.setStatusCode(HttpStatus.BAD_REQUEST);
        //设置返回类型
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        String errorMsg = toStr(throwable);
        DataBuffer dataBuffer =  response.bufferFactory().wrap(errorMsg.getBytes());

        return response.writeWith(Mono.just(dataBuffer));
    }

    private String toStr(Throwable throwable) {
        if (throwable  instanceof CheckException){
            CheckException e = (CheckException) throwable;
            return e.getFieldName()+":invalid value "+e.getFieldValue();
        }else {
            throwable.printStackTrace();
            return throwable.toString();
        }
    }
}
