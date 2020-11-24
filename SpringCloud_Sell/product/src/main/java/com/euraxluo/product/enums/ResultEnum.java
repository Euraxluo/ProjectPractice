package com.euraxluo.product.enums;

import lombok.Getter;

/**
 * product
 * com.euraxluo.product.enums
 * ResultEnum
 * 2020/6/14 11:51
 * author:Euraxluo
 * TODO
 */
@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(1, "商品不存在"),
    PRODUCT_STOCK_ERROR(2, "库存有误"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}