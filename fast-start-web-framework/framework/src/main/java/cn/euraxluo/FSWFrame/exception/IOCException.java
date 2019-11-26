package cn.euraxluo.FSWFrame.exception;

import cn.euraxluo.FSWFrame.log.LogFactory;

import java.util.logging.Logger;

/**
 * fast-start-web-framework
 * cn.euraxluo.FSWFrame.exception
 * IOCException
 * 2019/11/24 17:38
 * author:Euraxluo
 * TODO
 */
public class IOCException extends Exception {
    private static Logger logger = LogFactory.getLogger();
    public IOCException(String message) {
        super("IOCException "+message);
        logger.warning(super.getMessage());
    }
}
