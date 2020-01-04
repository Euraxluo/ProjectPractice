package cn.euraxluo.passbook.passbook.vo;

import cn.euraxluo.passbook.passbook.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.vo
 * PassInfo
 * 2019/12/29 22:42
 * author:Euraxluo
 * 用户领取的优惠券信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassInfo {
    /** 优惠券 */
    private Pass pass;
    /** 优惠券模板 */
    private PassTemplate passTemplate;
    /** 优惠券队形的商户 */
    private Merchants merchants;
}
