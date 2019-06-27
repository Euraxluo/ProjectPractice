package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * @InterfaceName ICategoryService
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-16 下午2:51
 */
public interface ICategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);
    ServerResponse updateCategoryName(Integer categoryId,String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
