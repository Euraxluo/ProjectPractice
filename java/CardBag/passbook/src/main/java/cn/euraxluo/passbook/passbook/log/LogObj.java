package cn.euraxluo.passbook.passbook.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.log
 * LogObj
 * 2019/12/29 11:15
 * author:Euraxluo
 * 日志对象实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogObj {
    /** 日志动作类型 */
    private String action;

    /** 用户 id */
    private Long userId;

    /** 当前时间戳 */
    private Long timestamp;

    /** 客户端 ip 地址 */
    private String remoteIp;

    /** 日志信息 */
    private Object info = null;
}
