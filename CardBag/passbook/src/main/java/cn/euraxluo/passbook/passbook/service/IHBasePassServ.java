package cn.euraxluo.passbook.passbook.service;

import cn.euraxluo.passbook.passbook.vo.PassTemplate;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service
 * IHBasePassServ
 * 2019/12/29 15:55
 * author:Euraxluo
 * HBase 服务
 */
public interface IHBasePassServ {

    /**
     * 将PassTemplate 写入HBase
     * @param passTemplate
     * @return
     */
    boolean dropPassTemplateToHBase(PassTemplate passTemplate);
}
