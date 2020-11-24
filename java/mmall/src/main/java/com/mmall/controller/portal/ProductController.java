package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName ProductController
 * @Description TODO
 * @Author Euraxluo
 * @Date 19-1-2 上午10:59
 */
@Controller
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    private IProductService iProductService;

    /**
     * @description 前台获取商品信息接口
     * @param [productId]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.ProductDetailVo>
     * @author Euraxluo
     * @date 19-1-2
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return iProductService.getProductDetail(productId);
    }
    /**
     * @description 利用keyword(producName)和categoryId查询结果根据orderBy动态排序并分页显示
     * @param [keyword, categoryId, pageNum, pageSize, orderBy]
     * @return com.mmall.common.ServerResponse<com.github.pagehelper.PageInfo>
     * @author Euraxluo
     * @date 19-1-2
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false) String keyword,
                                         @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                         @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "") String orderBy){
        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }

}
