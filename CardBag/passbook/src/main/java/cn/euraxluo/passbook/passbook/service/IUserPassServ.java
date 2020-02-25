package cn.euraxluo.passbook.passbook.service;

import cn.euraxluo.passbook.passbook.vo.Pass;
import cn.euraxluo.passbook.passbook.vo.Response;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service
 * IUserPassServ
 * 2019/12/29 22:49
 * author:Euraxluo
 * 获取用户个人优惠券信息
 */
public interface IUserPassServ {
    /**
     * 获取我的优惠券
     * @param userId
     * @return
     * @throws Exception
     */
    Response getUserPassInfo(Long userId) throws Exception;

    /**
     * 获取用户已经消费的优惠券
     * @param userId
     * @return
     * @throws Exception
     */
    Response getUserUsedPassInfo(Long userId) throws Exception;

    /**
     * 获取用户所有的优惠券
     * @param userId
     * @return
     * @throws Exception
     */
    Response getUserAllPassInfo(Long userId) throws Exception;

    /**
     * 获取用户所有的优惠券
     * @param pass
     * @return
     */
    Response userUsePass(Pass pass);
}
