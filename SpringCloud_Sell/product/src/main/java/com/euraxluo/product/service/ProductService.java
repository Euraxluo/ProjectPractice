package com.euraxluo.product.service;

import com.euraxluo.product.dataobject.ProductInfo;

import java.util.List;

/**
 * product
 * com.euraxluo.product.service
 * ProductService
 * 2020/6/14 12:30
 * author:Euraxluo
 * TODO
 */
public interface ProductService {

    /**
     * 查询所有在架商品列表
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询商品列表
     * @param productIdList
     * @return
     */
//    List<ProductInfoOutput> findList(List<String> productIdList);

    /**
     * 扣库存
     * @param decreaseStockInputList
     */
//    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}

