package cn.euraxluo.passbook.passbook.constant;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.constant
 * PassStatus
 * 2019/12/29 10:43
 * author:Euraxluo
 * 优惠券的状态枚举
 */
public enum  PassStatus {

    UNUSED(1, "未被使用的"),
    USED(2, "已经使用的"),
    ALL(3, "全部领取的");

    /** 状态码 */
    private Integer code;

    /** 状态描述 */
    private String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    PassStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
