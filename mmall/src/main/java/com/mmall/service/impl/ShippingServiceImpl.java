package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ShippingServiceImpl
 * @Description TODO
 * @Author Euraxluo
 * @Date 19-1-4 上午10:11
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    /**
     * @description 添加地址
     * @param [userId, shipping]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 19-1-4
     */
    public ServerResponse add(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if(rowCount > 0){
            Map result = Maps.newHashMap();
            result.put("shippingId",shipping.getId());
            return ServerResponse.createBySuccess("新建地址成功",result);
        }
        return ServerResponse.createByErrorMessage("新建地址失败");
    }

    /**
     * @description 刪除地址，需要解決橫向越權
     * @param [userId, shippingId]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 19-1-4
     */
    public ServerResponse<String> del(Integer userId, Integer shippingId){
        int rowCount = shippingMapper.deleteByShippingIdUserId(userId,shippingId);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    /**
     * @description 更新地址，需要解決橫向越權
     * @param [userId, shipping]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 19-1-4
     */
    public ServerResponse updat(Integer userId, Shipping shipping){
        shipping.setUserId(userId);//userid需要从session中获取
        int rowCount = shippingMapper.updateByShipping(shipping);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新地址成功");
        }
        return ServerResponse.createByErrorMessage("更新地址失败");
    }

    public ServerResponse<Shipping> select(Integer userId, Integer shippingId){
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId,shippingId);
        if(shipping == null){
            return ServerResponse.createByErrorMessage("无法查询到地址");
        }
        return ServerResponse.createBySuccess("查询成功",shipping);
    }

    public ServerResponse<PageInfo> list(Integer userId,int pageNum,int pageSize){
        //startPage--start 记录一个开始的位置
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
