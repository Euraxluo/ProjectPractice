package cn.euraxluo.passbook.passbook.utils;

import cn.euraxluo.passbook.passbook.vo.Feedback;
import cn.euraxluo.passbook.passbook.vo.GainPassTemplateRequest;
import cn.euraxluo.passbook.passbook.vo.PassTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.utils
 * RowKeyGenUtil
 * 2019/12/29 15:27
 * author:Euraxluo
 * RowKey 生成公共类
 */
@Slf4j
public class RowKeyGenUtil {
    /**
     * 根据提供的领取优惠券亲求生成RowKey
     * Pass RowKey = reversed(userId)+inverse(timestamp) + passTemplate RowKey
     * @param request
     * @return
     */
    public static String genPassRowKey(GainPassTemplateRequest request){
        return new StringBuilder(String.valueOf(request.getUserId())).reverse().toString()
                + (Long.MAX_VALUE - System.currentTimeMillis())
                + genPassTemplateRowKey(request.getPassTemplate());
    }

    /**
     * 根据提供的PassTemplate 对象生成RowKey
     * @param passTemplate
     * @return
     */
    public static String genPassTemplateRowKey(PassTemplate passTemplate){
        /** id + title, 两个都是唯一的,再hash一下,生成唯一的KEY */
        String passInfo = String.valueOf(passTemplate.getId())
                + "_" + passTemplate.getTitle();
        String rowKey = DigestUtils.md5Hex(passInfo);
        log.info("GenPassTemplateRowKey:{} {}",passInfo,rowKey);
        return rowKey;
    }

    /**
     * 根据 Feedback 构造 RowKey
     * @param feedback
     * @return
     */
    public static String genFeedbackRowKey(Feedback feedback){
        /**  reverse 是存储时更加分散, 时间减的操作会让最新生成的RowKey更加在前面,便于搜索*/
        return new StringBuilder(String.valueOf(feedback.getUserId())).reverse().toString()
                + (Long.MAX_VALUE - System.currentTimeMillis());
    }
}
