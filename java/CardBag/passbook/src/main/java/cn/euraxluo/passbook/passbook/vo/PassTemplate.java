package cn.euraxluo.passbook.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.vo
 * PassTemplate
 * 2019/12/29 12:38
 * author:Euraxluo
 * 投放的优惠券对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassTemplate {
    /** 所属商户 id */
    private Integer id;

    /** 优惠券标题 */
    private String title;

    /** 优惠券摘要 */
    private String summary;

    /** 优惠券详细信息 */
    private String desc;

    /** 最大个数限制 */
    private Long limit;

    /** 优惠券是否有 Token, 用于商户核销 */
    private Boolean hasToken;

    /** 优惠券背景色 */
    private Integer background;

    /** 优惠券开始时间 */
    private Date start;

    /** 优惠券结束时间 */
    private Date end;
}
