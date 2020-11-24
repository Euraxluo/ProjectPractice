package cn.euraxluo.passbook.merchants.constant;

/**
 * merchants
 * cn.euraxluo.passbook.merchants.constant
 * ColorCode
 * 2019/12/26 11:41
 * author:Euraxluo
 * TODO
 */
public enum  ColorCode {
    RED(1, "红色"),
    GREEN(2, "绿色"),
    BLUE(3, "蓝色");

    /** 颜色代码 */
    private Integer code;

    /** 颜色描述 */
    private String color;

    ColorCode(Integer code, String color) {
        this.code = code;
        this.color = color;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getColor() {
        return this.color;
    }
}
