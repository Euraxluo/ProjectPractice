package com.mmall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ProductServiceImpl
 * @Description TODO
 * @Author Euraxluo
 * @Date 19-1-1 下午2:30
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ICategoryService iCategoryService;


    /**
     * @description 后台,新增或者更新产品service实现
     * @param [product]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 19-1-1
     */
    public ServerResponse saveOrUpdateProduct(Product product){
        if(product != null){//判斷product對象是否爲空
            if(StringUtils.isNotBlank(product.getSubImages())){//判斷子圖是否爲空，不爲空，就把子圖的第一個作爲主圖
                String[] subImageArray = product.getSubImages().split(",");
                if(subImageArray.length>0){
                    product.setMainImage(subImageArray[0]);
                }
                if(product.getId() != null){//更新时需要传ID,判断有无id
                    int rowCount = productMapper.updateByPrimaryKey(product);
                    if(rowCount > 0){
                        return ServerResponse.createBySuccessMessage("更新产品成功");
                    }
                    return ServerResponse.createBySuccessMessage("更新产品失败");
                }else {
                    int rowCount = productMapper.insert(product);
                    if(rowCount > 0){
                        return ServerResponse.createBySuccessMessage("新增产品成功");
                    }
                    return ServerResponse.createBySuccessMessage("新增产品失败");
                }
            }

        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }

    /**
     * @description 修改产品销售状态
     * @param [productId, status]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 19-1-1
     */
    public ServerResponse<String> setSaleStatus(Integer productId,Integer status){
        if(productId == null || status == null){//判断参数是否为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if(rowCount > 0){
            return ServerResponse.createBySuccessMessage("修改产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败");
    }

    /**
     * @description 获取商品详情描述
     * @param [productId]
     * @return com.mmall.common.ServerResponse<java.lang.Object>
     * @author Euraxluo
     * @date 19-1-1
     */
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId){
        if(productId == null){//判断参数是否为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下嫁或删除");
        }
        //VO(value-Obj)操作
        //申明一个assemble的对象,通过product来把productetailVo组装上
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * @description assembleProductDetailVo的组装方法
     * @param [product]
     * @return com.mmall.vo.ProductDetailVo
     * @author Euraxluo
     * @date 19-1-1
     */
    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
        //imageHost ==> 通过配置文件读取,可以演进为配置中心,可以热部署配置
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));//如果没有配置ftp服务器,就是用defaultValue
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());//通过分类id查找父节点
        if(category == null){
            productDetailVo.setParentCategoryId(0);//如果没找到父节点,就把这个设置为根结点
        }else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        //createtime,updatetime需要转化
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }
    /**
     * @description 使用pageHelper实现的分页接口service实现
     * @param [pageNum, pageSize]
     * @return com.mmall.common.ServerResponse<com.github.pagehelper.PageInfo>
     * @author Euraxluo
     * @date 19-1-1
     */
    public ServerResponse<PageInfo> getProductList(int pageNum,int pageSize){
        //startPage--start 记录一个开始的位置
        PageHelper.startPage(pageNum,pageSize);

        //写自己的sql查询逻辑
        List<Product> productList = productMapper.selectList();

        //productlist,只需要一些字段,因此创建一个vo
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product productItem : productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);

        }
        //pageHelper 接尾
        PageInfo pageResult = new PageInfo(productList);
        //把前端显示的集合换成ProductListVo
        pageResult.setList(productListVoList);
        //返回结果
        return ServerResponse.createBySuccess(pageResult);
    }

    /**
     * @description assembleProductListVo的组装方法
     * @param [product]
     * @return com.mmall.vo.ProductListVo
     * @author Euraxluo
     * @date 19-1-1
     */
    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));//如果没有配置ftp服务器,就是用defaultValue
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setPrice(product.getPrice());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }

    public ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        if(StringUtils.isNotBlank(productName)){//判断是否为空,如果不为空,就构造成模糊查询
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName,productId);

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product productItem : productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);

        }
        //pageHelper 接尾
        PageInfo pageResult = new PageInfo(productList);
        //把前端显示的集合换成ProductListVo
        pageResult.setList(productListVoList);
        //返回结果
        return ServerResponse.createBySuccess(pageResult);
    }
    /**
     * @description 前台用户的查看商品详情service实现
     * @param [productId]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.ProductDetailVo>
     * @author Euraxluo
     * @date 19-1-2
     */
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId){
        if(productId == null){//判断参数是否为空
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下嫁或删除");
        }
        if(product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()){
            return ServerResponse.createByErrorMessage("产品已下嫁或删除");
        }
        //VO(value-Obj)操作
        //申明一个assemble的对象,通过product来把productetailVo组装上
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * @description 利用keyword(producName)和categoryId查询结果动态排序并分页显示
     * @param [keyword, categoryId, pageNum, pageSize, orderBy]
     * @return com.mmall.common.ServerResponse<com.github.pagehelper.PageInfo>
     * @author Euraxluo
     * @date 19-1-2
     */
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy){
        if(StringUtils.isBlank(keyword) && categoryId == null){//如果keyword和categoryId为空,判断为参数错误
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList = new ArrayList<Integer>();//用来保存递归查询出来的品类id集合
        if(categoryId != null){//如果品类id不为空
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if(category == null && StringUtils.isBlank(keyword)){//如果没有找到这个categoryId,也没有keyword,不报错,返回一个空的结果集合
                //按照分页的流程
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo> productListVoList = Lists.newArrayList();//创建一个空的集合
                PageInfo pageInfo = new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();//递归查询
        }
        if(StringUtils.isNotBlank(keyword)){
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();//构造为迷糊查询
        }
        //分页
        PageHelper.startPage(pageNum,pageSize);
        ////动态排序处理
        if(StringUtils.isNotBlank(orderBy)){//如果排序规则不为空
            if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                //分页插件要求使用空格
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        //categoryList不能传空值过去,不然查询不出来
        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword,categoryIdList.size()==0?null:categoryIdList);
        //从product填充数据productListVoList
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product product: productList){
            ProductListVo productListVo = assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        //分页
        PageInfo pageInfo = new PageInfo(productList);//根据我们查询出来的结果计算分页
        pageInfo.setList(productListVoList);//把数据设置为我们的VO
        return ServerResponse.createBySuccess(pageInfo);
    }

































































}
