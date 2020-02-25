package cn.euraxluo.passbook.passbook.service;

import cn.euraxluo.passbook.passbook.vo.Feedback;
import cn.euraxluo.passbook.passbook.vo.Response;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service
 * IFeedbackServ
 * 2019/12/29 22:16
 * author:Euraxluo
 * 用户评论接口
 */

public interface IFeedbackServ {
    /**
     * 创建评论
     * @param feedback
     * @return
     */
    Response createFeedback(Feedback feedback);

    /**
     * 获取用户评论
     * @param userId
     * @return
     */
    Response getFeedback(Long userId);
}
