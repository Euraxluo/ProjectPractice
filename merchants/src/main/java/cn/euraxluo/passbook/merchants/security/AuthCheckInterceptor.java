package cn.euraxluo.passbook.merchants.security;

import cn.euraxluo.passbook.merchants.constant.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * merchants
 * cn.euraxluo.passbook.merchants.security
 * AuthCheckInterceptor
 * 2019/12/26 11:49
 * author:Euraxluo
 * TODO
 */
@Component
public class AuthCheckInterceptor implements HandlerInterceptor {
    /**
     * 拦截之前的操作
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(Constants.TOKEN_STRING);
        if (StringUtils.isEmpty(token)){
            throw new Exception("Header 中缺少 " + Constants.TOKEN_STRING + "!");
        }
        if (!token.equals(Constants.TOKEN)){
            throw new Exception("Header 中 " + Constants.TOKEN_STRING + "错误!");
        }
        AccessContext.setThreadToken(token);
        return true;
    }

    /**
     * 请求运行结束后执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AccessContext.clearThreadToken();
    }
}
