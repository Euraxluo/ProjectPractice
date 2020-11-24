package com.euraxluo.product.service.impl;

import com.euraxluo.product.dataobject.ProductCategory;
import com.euraxluo.product.repository.ProductCategoryRepository;
import com.euraxluo.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * product
 * com.euraxluo.product.service.impl
 * CategoryServiceImpl
 * 2020/6/14 12:30
 * author:Euraxluo
 * TODO
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }
}
