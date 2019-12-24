package com.webflux.demo.validation;

import com.webflux.demo.exception.CheckException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * webFlux
 * com.webflux.demo.validation
 * UserNameValidator
 * 2019/11/28 8:30
 * author:Euraxluo
 * TODO
 */
public class UserNameValidator implements ConstraintValidator<UserName,String> {
    //todo 让在注解抛出异常怎么实现啊..
    private static final List<String> INVALID_NAMES = Arrays.asList("admin","hehe");
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;//cant be null
        if (!INVALID_NAMES.contains(value.toLowerCase())){
            return true;
        }
        return false;
    }

    private static final String[] NEED_CHECK_NAMES = {"admin","hehe"};
    public static void UserNameValidator(String value){
      Stream.of(NEED_CHECK_NAMES)
                .filter(name->value==null || name.equalsIgnoreCase(value))
                .findAny()//找到任何一个错误
                .ifPresent(name->{
                    throw new CheckException("name",value);
                });

    }
}
