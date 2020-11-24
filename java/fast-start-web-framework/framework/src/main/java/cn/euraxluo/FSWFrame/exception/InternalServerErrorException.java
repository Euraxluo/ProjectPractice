package cn.euraxluo.FSWFrame.exception;

import cn.euraxluo.FSWFrame.log.LogFactory;

import java.util.logging.Logger;

/**
 * fast-start-web-framework
 * cn.euraxluo.FSWFrame.exception
 * InternalServerErrorException
 * 2019/11/24 18:38
 * author:Euraxluo
 * TODO
 */
public class InternalServerErrorException extends Exception {
    private static Logger logger = LogFactory.getLogger();
    public InternalServerErrorException(String message) {
        super("500 Internal Server Error "+message);
        logger.warning(super.getMessage());
    }
}
