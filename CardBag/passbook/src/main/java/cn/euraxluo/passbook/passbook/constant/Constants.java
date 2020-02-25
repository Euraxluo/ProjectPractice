package cn.euraxluo.passbook.passbook.constant;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.constant
 * Constants
 * 2019/12/29 10:36
 * author:Euraxluo
 * 常用常量定义
 */
public class Constants {
    /** 商户优惠券 Kafka Topic */
    public static final String TEMPLATE_TOPIC = "merchants-template";

    /** token 文件存储目录 */
    public static final String TOKEN_DIR = "/tmp/token/";

    /** 已使用的 token 文件名后缀 */
    public static final String USED_TOKEN_SUFFIX = "_";

    /** 用户数的 redis key */
    public static final String USE_COUNT_REDIS_KEY = "passbook_user-count";

}
