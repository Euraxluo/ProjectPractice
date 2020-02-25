package cn.euraxluo.passbook.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.vo
 * GainPassTemplateRequest
 * 2019/12/29 22:40
 * author:Euraxluo
 * 用户领取优惠券的请求信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GainPassTemplateRequest {
    /** 用户Id*/
    private Long userId;
    /** PassTemplate 对象 */
    private PassTemplate passTemplate;
}
