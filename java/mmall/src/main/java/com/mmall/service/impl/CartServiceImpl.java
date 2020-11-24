package com.mmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


/**
 * @ClassName CartService
 * @Description TODO
 * @Author Euraxluo
 * @Date 19-1-3 上午11:01
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    /**
     * @description 选择或者反选,根据传过来的productd是否为空决定时全选,还是单选
     * @param [userId, productId, checked]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.CartVo>
     * @author Euraxluo
     * @date 19-1-3
     */
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer productId,Integer checked){
        cartMapper.checkedOrUncheckedProduct(userId,productId,checked);
        return this.list(userId);
    }

    public ServerResponse<Integer> getCartProductCount(Integer userId){
        if(userId == null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }
    /**
     * @description 添加产品到购物车
     * @param [userId, count, productId]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.CartVo>
     * @author Euraxluo
     * @date 19-1-3
     */
    public ServerResponse<CartVo> add(Integer userId, Integer count, Integer productId){
        if(productId == null || count==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if(cart == null){
            //这个产品不在这个购物车,需要新增加这个产品的记录
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            cartMapper.insert(cartItem);
        }else {
            //如果产品存在,更新数量
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
            //库存联动
        }
        return this.list(userId);
    }
    /**
     * @description 查询购物车接口
     * @param [userId]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.CartVo>
     * @author Euraxluo
     * @date 19-1-3
     */
    public ServerResponse<CartVo> list(Integer userId){
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    /**
     * @description 更新购物车中的产品
     * @param [userId, count, productId]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.CartVo>
     * @author Euraxluo
     * @date 19-1-3
     */
    public ServerResponse<CartVo> update(Integer userId, Integer count, Integer productId){
        if(productId == null || count==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if(cart != null) {
            //需要更新这个产品的数量
            cart.setQuantity(count);
        }
        //更新购物车
        cartMapper.updateByPrimaryKeySelective(cart);
        //库存联动
        return this.list(userId);
    }

    /**
     * @description 在购物车中删除产品s
     * @param [userId, productIds]
     * @return com.mmall.common.ServerResponse<com.mmall.vo.CartVo>
     * @author Euraxluo
     * @date 19-1-3
     */
    public ServerResponse<CartVo> deleteProduct(Integer userId,String productIds){
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if(CollectionUtils.isEmpty(productList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        //删除购物车中的产品
        cartMapper.deleteByUserIdProductIds(userId,productList);
        //库存联动
        return this.list(userId);
    }

    /**
     * @description 购物车VO的填充方法,返回购物车总价,商品列表,是否全选,imagehost
     * @param [userId]
     * @return com.mmall.vo.CartVo
     * @author Euraxluo
     * @date 19-1-3
     */
    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");//一定要使用String构造器

        if(CollectionUtils.isNotEmpty(cartList)){//如果查询出来这个人的购物车不是空的,遍历他,填充CartProductVoVo
            for(Cart cartItem : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(cartItem.getUserId());
                cartProductVo.setProductId(cartItem.getProductId());
                //根据productId.查询这个产品,填充到Vo中
                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if(product != null){
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    //判断库存,产品库存不能小于购物车的数量
                    int buyLimitCount = 0;
                    if(product.getStock() >= cartItem.getQuantity()){
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartForQuantity.setId(cartItem.getId());
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算这个商品总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }
                if(cartItem.getChecked() == Const.Cart.CHECKED){
                    //如果勾选,增加到总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        //填充CartVo
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;
    }

    /**
     * @description 判断是否全部选择
     * @param [userId]
     * @return boolean
     * @author Euraxluo
     * @date 19-1-3
     */
    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
        //如果全选,返回true
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }












































































}
