package cn.euraxluo.passbook.merchants.service;

import cn.euraxluo.passbook.merchants.vo.CreateMerchantsRequest;
import cn.euraxluo.passbook.merchants.vo.PassTemplate;
import cn.euraxluo.passbook.merchants.vo.Response;

/**
 * merchants
 * cn.euraxluo.passbook.merchants.service
 * IMerchantsServ
 * 2019/12/28 10:13
 * author:Euraxluo
 * 商户服务接口定义
 */
public interface IMerchantsServ {
    /**
     * 创建商户
     * @param req
     * @return
     */
    Response createMerchants(CreateMerchantsRequest req);

    /**
     * 根据Id构造丧户信息
     * @param id
     * @return
     */
    Response buildMerchantsInfoById(Integer id);

    /**
     * 投放优惠券
     * @param template
     * @return
     */
    Response dropPassTemplate(PassTemplate template);

}
