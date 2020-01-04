package cn.euraxluo.passbook.passbook.log;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.log
 * LogGenerator
 * 2019/12/29 11:13
 * author:Euraxluo
 * 日志生成器
 */
@Slf4j
public class LogGenerator {
    /**
     * 生成日志
     * @param request HttpServletRequest
     * @param userId 用户 id
     * @param action 日志类型
     * @param info 日志信息, 可以是 null
     */
    public static void genLog(HttpServletRequest request,Long userId,String action,Object info){
        log.info(
                JSON.toJSONString(
                        new LogObj(action,userId,System.currentTimeMillis(),request.getRemoteAddr(),info)
                )
        );
    }
}
