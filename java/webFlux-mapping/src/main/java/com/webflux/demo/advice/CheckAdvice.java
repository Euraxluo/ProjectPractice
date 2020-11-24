package com.webflux.demo.advice;

import com.webflux.demo.exception.CheckException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.WebExceptionHandler;

/**
 * webFlux
 * com.webflux.demo.advice
 * CheckAdvice
 * 2019/11/28 8:11
 * author:Euraxluo
 * 异常处理切面
 */
@ControllerAdvice
public class CheckAdvice {
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<String> handleBindException(WebExchangeBindException e){
        return new ResponseEntity<String>(toStr(e), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CheckException.class)
    public ResponseEntity<String> handleCheckException(CheckException e){
        return new ResponseEntity<String>(toStr(e), HttpStatus.BAD_REQUEST);
    }

    private String toStr(CheckException e) {
        return e.getFieldName()+":{ \"vlue\":"+e.getFieldValue()+";\"message\":\"不允许的值\"}";
    }

    /**
     * 把校验异常转换为字符串
     * @param e
     * @return
     */
    private String toStr(WebExchangeBindException e) {
        return e.getFieldErrors().stream()
                .map(ex->ex.getField()+":"+ex.getDefaultMessage())//把异常转为字符串
                .reduce("",(s1,s2)->s1+"\n"+s2);//数组转为字符串
    }

}
