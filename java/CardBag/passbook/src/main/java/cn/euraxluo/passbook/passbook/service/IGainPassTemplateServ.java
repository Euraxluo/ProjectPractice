package cn.euraxluo.passbook.passbook.service;

import cn.euraxluo.passbook.passbook.vo.GainPassTemplateRequest;
import cn.euraxluo.passbook.passbook.vo.Response;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service
 * IGainPassTemplateServ
 * 2019/12/29 22:47
 * author:Euraxluo
 * 用户领取优惠券功能实现
 */
public interface IGainPassTemplateServ {

    /***
     * 用户领取优惠券
     * @param request
     * @return
     * @throws Exception
     */
    Response gainPassTemplate(GainPassTemplateRequest request) throws Exception;
}
