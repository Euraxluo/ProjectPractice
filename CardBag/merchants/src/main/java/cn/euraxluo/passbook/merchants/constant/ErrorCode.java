package cn.euraxluo.passbook.merchants.constant;

/**
 * merchants
 * cn.euraxluo.passbook.merchants.constant
 * ErrorCode
 * 2019/12/26 11:37
 * author:Euraxluo
 * TODO
 */
public enum  ErrorCode {
    SUCCESS(0, ""),
    DUPLICATE_NAME(1, "商户名称重复"),
    EMPTY_NAME(2,"商户 logo 为空"),
    EMPTY_LOGO(3, "商户 logo 为空"),
    EMPTY_BUSINESS_LICENSE(4, "商户营业执照为空"),
    ERROR_PHONE(5, "商户联系电话错误"),
    EMPTY_ADDRESS(6, "商户地址为空"),
    MERCHANTS_NOT_EXIST(7, "商户不存在");

    /** 错误码 */
    private Integer code;

    /** 错误描述 */
    private String desc;

    ErrorCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
