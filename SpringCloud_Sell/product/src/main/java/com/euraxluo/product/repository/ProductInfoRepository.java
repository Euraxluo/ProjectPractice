package com.euraxluo.product.repository;

import com.euraxluo.product.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * product
 * com.euraxluo.product.repository
 * ProductInfoRepository
 * 2020/6/14 11:47
 * author:Euraxluo
 * TODO
 */

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);

    List<ProductInfo> findByProductIdIn(List<String> productIdList);
}
