package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName CartController
 * @Description TODO
 * @Author Euraxluo
 * @Date 19-1-3 上午10:57
 */
@Controller
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    private ICartService iCartService;

    /**
     * @description 添加某个产品到购物车中,更新列表
     * @param [session, count, productId]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.CartVo>
     * @author Euraxluo
     * @date 19-1-3
     */
    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse<CartVo> add(HttpSession session, Integer count, Integer productId) {
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        return iCartService.add(user.getId(),count,productId);
    }

    /**
     * @description 更新购物车中某个产品的数量,更新购物车
     * @param [session, count, productId]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.CartVo>
     * @author Euraxluo
     * @date 19-1-3
     */
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse<CartVo> update(HttpSession session, Integer count, Integer productId) {
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        return iCartService.update(user.getId(),count,productId);
    }

    /**
     * @description 删除购物车中某个产品,更新购物车
     * @param [session, productIds]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.CartVo>
     * @author Euraxluo
     * @date 19-1-3
     */
    @RequestMapping("delete_product.do")
    @ResponseBody
    public ServerResponse<CartVo> delete_product(HttpSession session,String productIds) {//productId 用,分割的字符串
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        return iCartService.deleteProduct(user.getId(),productIds);
    }

    /**
     * @description 获取购物车列表
     * @param [session, productIds]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.CartVo>
     * @author Euraxluo
     * @date 19-1-3
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<CartVo> list(HttpSession session,String productIds) {
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        return iCartService.list(user.getId());
    }

    /**
     * @description 全选
     * @param [session]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.CartVo>
     * @author Euraxluo
     * @date 19-1-3
     */
    @RequestMapping("select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> selectAll(HttpSession session) {
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        return iCartService.selectOrUnSelect(user.getId(),null,Const.Cart.CHECKED);
    }

    /**
     * @description 全反选
     * @param [session]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.CartVo>
     * @author Euraxluo
     * @date 19-1-3
     */
    @RequestMapping("un_select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> unSelectAll(HttpSession session) {
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        return iCartService.selectOrUnSelect(user.getId(),null,Const.Cart.UN_CHECKED);
    }

    /**
     * @description 单选
     * @param [session, productId]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.CartVo>
     * @author Euraxluo
     * @date 19-1-3
     */
    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<CartVo> select(HttpSession session,Integer productId) {
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        return iCartService.selectOrUnSelect(user.getId(),productId,Const.Cart.CHECKED);
    }

    /**
     * @description 反选
     * @param [session, productId]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.CartVo>
     * @author Euraxluo
     * @date 19-1-3
     */
    @RequestMapping("un_select.do")
    @ResponseBody
    public ServerResponse<CartVo> unSelect(HttpSession session,Integer productId) {
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        return iCartService.selectOrUnSelect(user.getId(),productId,Const.Cart.UN_CHECKED);
    }

    /**
     * @description 获取用户购物车中产品的数量
     * @param [session]
     * @return com.mmall.common.ServerResponse<java.lang.Integer>
     * @author Euraxluo
     * @date 19-1-3
     */
    @RequestMapping("get_cart_product_count.do")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(HttpSession session) {
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProductCount(user.getId());
    }
}
