package com.mmall.controller.portal;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.service.IShippingService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName ShippingController
 * @Description TODO
 * @Author Euraxluo
 * @Date 19-1-4 上午10:07
 */
@Controller
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    /**
     * @description 添加地址接口
     * @param [session, shipping]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 19-1-4
     */
    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpSession session, Shipping shipping) {
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        return iShippingService.add(user.getId(),shipping);
    }

    /**
     * @description 删除地址接口
     * @param [session, shippingId]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 19-1-4
     */
    @RequestMapping("del.do")
    @ResponseBody
    public ServerResponse<String> del(HttpSession session, Integer shippingId) {
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        return iShippingService.del(user.getId(),shippingId);
    }

    /**
     * @description 更新地址接口
     * @param [session, shipping]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 19-1-4
     */
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpSession session, Shipping shipping) {
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        return iShippingService.updat(user.getId(),shipping);
    }

    /**
     * @description 选择地址接口
     * @param [session, shippingId]
     * @return com.mmall.common.ServerResponse<com.mmall.pojo.Shipping>
     * @author Euraxluo
     * @date 19-1-4
     */
    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<Shipping> select(HttpSession session, Integer shippingId) {
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        return iShippingService.select(user.getId(),shippingId);
    }

    /**
     * @description 返回分页列表接口
     * @param [pageNum, pageSize, session]
     * @return com.mmall.common.ServerResponse<com.github.pagehelper.PageInfo>
     * @author Euraxluo
     * @date 19-1-4
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                         HttpSession session){
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        return iShippingService.list(user.getId(),pageNum,pageSize);
    }
}
