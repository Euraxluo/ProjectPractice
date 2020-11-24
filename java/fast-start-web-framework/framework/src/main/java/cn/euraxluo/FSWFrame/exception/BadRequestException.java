package cn.euraxluo.FSWFrame.exception;

import cn.euraxluo.FSWFrame.log.LogFactory;

import java.util.logging.Logger;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.exception
 * BadRequestException
 * 2019/11/23 22:25
 * author:Euraxluo
 * TODO
 */
public class BadRequestException extends Exception {
    private static Logger logger = LogFactory.getLogger();
    public BadRequestException(String message) {
        super("400 Bad Request "+message);
        logger.warning(super.getMessage());
    }
}
