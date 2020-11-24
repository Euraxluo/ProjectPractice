package com.euraxluo.product.repository;

import com.euraxluo.product.dataobject.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * product
 * com.euraxluo.product.repository
 * ProductCategoryRepositoryTest
 * 2020/6/14 11:53
 * author:Euraxluo
 * TODO
 */
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<ProductCategory> list = productCategoryRepository.findByCategoryTypeIn(Arrays.asList(11, 22));
        System.out.println(list.size());
    }

}