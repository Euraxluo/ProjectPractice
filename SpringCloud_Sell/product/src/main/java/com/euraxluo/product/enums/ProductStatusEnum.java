package com.euraxluo.product.enums;

import lombok.Getter;

/**
 * product
 * com.euraxluo.product.enums
 * ProductStatusEnum
 * 2020/6/14 11:50
 * author:Euraxluo
 * TODO
 */
@Getter
public enum ProductStatusEnum {
    UP(0, "在架"),
    DOWN(1, "下架"),
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
