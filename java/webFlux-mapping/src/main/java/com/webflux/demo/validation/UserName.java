package com.webflux.demo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Arrays;
import java.util.List;

/**
 * webFlux
 * com.webflux.demo.validation
 * UserName
 * 2019/11/28 8:30
 * author:Euraxluo
 * TODO
 */
@Documented
@Constraint(validatedBy = UserNameValidator.class)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserName {
    String message() default "Error value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
