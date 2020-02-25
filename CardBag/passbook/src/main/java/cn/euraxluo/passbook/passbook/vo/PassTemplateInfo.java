package cn.euraxluo.passbook.passbook.vo;

import cn.euraxluo.passbook.passbook.entity.Merchants;
import lombok.*;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.vo
 * PassTemplateInfo
 * 2019/12/29 22:36
 * author:Euraxluo
 * 优惠券模板信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassTemplateInfo extends PassTemplate {
    /** 优惠券模板 */
    private PassTemplate passTemplate;

    /** 投放优惠券对应的商户 */
    private Merchants merchants;
}
