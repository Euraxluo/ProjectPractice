package com.euraxluo.product.service;

import com.euraxluo.product.dataobject.ProductCategory;

import java.util.List;

/**
 * product
 * com.euraxluo.product.service
 * CategoryService
 * 2020/6/14 12:29
 * author:Euraxluo
 * TODO
 */
public interface CategoryService {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
