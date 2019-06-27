package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @ClassName CategoryServiceImpl
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-16 下午4:28
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {
    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @description 添加品类service实现类
     * @param [categoryName, parentId]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 18-12-16
     */
    public ServerResponse addCategory(String categoryName,Integer parentId){
        if(parentId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("添加品类参数失败");
        }
        //把数据持久化
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);//把这个品类设置为可用

        int rowCount = categoryMapper.insert(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccessMessage("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    /**
     * @description 更新品类名陈
     * @param [categoryId, categoryName]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 18-12-16
     */
    public ServerResponse updateCategoryName(Integer categoryId,String categoryName){
        if(categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("更新品类参数失败");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);//有选择的更新
        if(rowCount > 0){
            return ServerResponse.createBySuccessMessage("更新品类名称成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名称失败");
    }

    /**
     * @description 非递归查询子节点
     * @param [categoryId]
     * @return com.mmall.common.ServerResponse<java.util.List<com.mmall.pojo.Category>>
     * @author Euraxluo
     * @date 18-12-16
     */
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId){
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentTd(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){//isEmpty 判断他是否为空,以及集合是否为空
            //打印日志
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * @description 递归查询子节点
     * @param [categoryId]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 18-12-16
     */
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId){
        //初始化
        Set<Category> categorySet = Sets.newHashSet();
        //调用递归,然后categorySet被填充好
        findChildCategory(categorySet,categoryId);

        //取出categorySet中的Id放到categoryIdList中
        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
            for(Category categoryItem : categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    /**
     * @description 递归算法,找出某节点的全部子节点信息
     * @param [categorySet, categoryId]
     * @return java.util.Set<com.mmall.pojo.Category>
     * @author Euraxluo
     * @date 18-12-16
     */
    public Set<Category> findChildCategory(Set<Category> categorySet,Integer categoryId){//不是基本数据类型,我们需要自己重写set方法中的hashCode以及equals方法
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        //填充categorySet
        if(category != null){
            categorySet.add(category);
        }
        //先写递归退出条件,查找子节点,如果为空,退出递归
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentTd(categoryId);

        for(Category categoryItem : categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }

}
