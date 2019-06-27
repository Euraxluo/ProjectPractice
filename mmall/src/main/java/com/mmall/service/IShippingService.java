package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

/**
 * @InterfaceName IShippingService
 * @Description TODO
 * @Author Euraxluo
 * @Date 19-1-4 上午10:10
 */
public interface IShippingService {
    ServerResponse add(Integer userId, Shipping shipping);
    ServerResponse del(Integer userId, Integer shippingId);
    ServerResponse updat(Integer userId, Shipping shipping);
    ServerResponse<Shipping> select(Integer userId, Integer shippingId);
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
