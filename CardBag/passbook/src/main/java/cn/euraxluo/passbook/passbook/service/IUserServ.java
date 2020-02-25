package cn.euraxluo.passbook.passbook.service;

import cn.euraxluo.passbook.passbook.vo.Response;
import cn.euraxluo.passbook.passbook.vo.User;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service
 * IUserServ
 * 2019/12/29 18:01
 * author:Euraxluo
 * 用户服务
 */

public interface IUserServ {
    /**
     * 创建用户
     * @param user
     * @return
     */
    Response createUser(User user) throws Exception;
}
