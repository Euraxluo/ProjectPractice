package cn.euraxluo.passbook.passbook.service;

import cn.euraxluo.passbook.passbook.vo.Response;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service
 * IInventoryServ
 * 2019/12/29 22:45
 * author:Euraxluo
 * 获取库存信息接口,只返回用户没有领取的,即优惠券库存功能
 */
public interface IInventoryServ {
    /***
     * 获取库存信息
     * @param userId
     * @return
     * @throws Exception
     */
    Response getInventoryInfo(Long userId) throws Exception;
}
