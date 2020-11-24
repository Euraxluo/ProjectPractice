package com.euraxluo.product.repository;

import com.euraxluo.product.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * product
 * com.euraxluo.product.repository
 * ProductCategoryRepository
 * 2020/6/14 11:45
 * author:Euraxluo
 * TODO
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}