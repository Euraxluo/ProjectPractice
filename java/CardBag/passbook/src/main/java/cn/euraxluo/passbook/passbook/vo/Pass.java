package cn.euraxluo.passbook.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.vo
 * Pass
 * 2019/12/29 12:44
 * author:Euraxluo
 * 用户领取的优惠券
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pass {
    /** 用户 id */
    private Long userId;

    /** pass 在 HBase 中的 RowKey */
    private String rowKey;

    /** PassTemplate 在 HBase 中的 RowKey */
    private String templateId;

    /** 优惠券 token, 有可能是 null, 则填充 -1 */
    private String token;

    /** 领取日期 */
    private Date assignedDate;

    /** 消费日期, 不为空代表已经被消费了 */
    private Date conDate;
}
