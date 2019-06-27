package com.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName CartVo
 * @Description TODO
 * @Author Euraxluo
 * @Date 19-1-3 上午11:30
 */
public class CartVo {
    private List<CartProductVo> cartProductVoList;//购物车有商品的集合
    private BigDecimal cartTotalPrice;//购物车总价
    private Boolean allChecked;//是否已经都勾选
    private String imageHost;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
