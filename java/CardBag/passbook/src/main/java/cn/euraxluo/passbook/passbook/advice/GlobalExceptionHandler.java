package cn.euraxluo.passbook.passbook.advice;

import cn.euraxluo.passbook.passbook.vo.ErrorInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.advice
 * GlobalExceptionHandler
 * 2019/12/29 15:41
 * author:Euraxluo
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 错误处理Handler
     * @param request
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ErrorInfo<String> errorHandler(HttpServletRequest request, Exception ex) throws Exception {

        ErrorInfo<String> info = new ErrorInfo<String>();
        info.setCode(ErrorInfo.ERROR);
        info.setMessage(ex.getMessage());
        info.setData("Do Not Have Return Data");
        info.setUrl(request.getRequestURL().toString());

        return info;
    }
}
