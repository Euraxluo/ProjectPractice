package cn.euraxluo.passbook.passbook.controller;

import cn.euraxluo.passbook.passbook.log.LogConstans;
import cn.euraxluo.passbook.passbook.log.LogGenerator;
import cn.euraxluo.passbook.passbook.service.IFeedbackServ;
import cn.euraxluo.passbook.passbook.service.IGainPassTemplateServ;
import cn.euraxluo.passbook.passbook.service.IInventoryServ;
import cn.euraxluo.passbook.passbook.service.IUserPassServ;
import cn.euraxluo.passbook.passbook.vo.Feedback;
import cn.euraxluo.passbook.passbook.vo.GainPassTemplateRequest;
import cn.euraxluo.passbook.passbook.vo.Pass;
import cn.euraxluo.passbook.passbook.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.controller
 * PassbookController
 * 2020/2/25 16:56
 * author:Euraxluo
 * Passbook Rest Controller
 */
@Slf4j
@RestController
@RequestMapping("/passbook")
public class PassbookController {
    private final IUserPassServ userPassServ;
    private final IInventoryServ inventoryServ;
    private final IGainPassTemplateServ gainPassTemplateServ;
    private final IFeedbackServ feedbackServ;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public PassbookController(IUserPassServ userPassServ, IInventoryServ inventoryServ, IGainPassTemplateServ gainPassTemplateServ, IFeedbackServ feedbackServ, HttpServletRequest httpServletRequest) {
        this.userPassServ = userPassServ;
        this.inventoryServ = inventoryServ;
        this.gainPassTemplateServ = gainPassTemplateServ;
        this.feedbackServ = feedbackServ;
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * 获取用户个人的优惠券信息
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/userpassinfo")
    Response userPassInfo(Long userId) throws Exception{
        //使用日志生成器来记录接口日志
        LogGenerator.genLog(httpServletRequest,userId, LogConstans.ActionName.USER_PASS_INFO,null);
        return userPassServ.getUserPassInfo(userId);
    }

    /**
     * 获取用户已经使用的优惠券信息
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/userusedpassinfo")
    Response userUsedPassInfo(Long userId) throws Exception{
        LogGenerator.genLog(httpServletRequest,userId,LogConstans.ActionName.USER_USED_PASS_INFO,null);
        return userPassServ.getUserUsedPassInfo(userId);
    }

    /**
     * 用户使用优惠券
     * @param pass
     * @return
     */
    @ResponseBody
    @PostMapping("/userusepass")
    Response userUsePass(@RequestBody Pass pass){
        LogGenerator.genLog(
                httpServletRequest,
                pass.getUserId(),
                LogConstans.ActionName.USER_USE_PASS,
                pass
        );
        return userPassServ.userUsePass(pass);
    }

    /**
     * 获取库存信息
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("inventoryinfo")
    Response inventoryInfo(Long userId) throws Exception{
        LogGenerator.genLog(
                httpServletRequest,
                userId,
                LogConstans.ActionName.INVENTORY_INFO,
                null
        );
        return inventoryServ.getInventoryInfo(userId);
    }

    /**
     * 用户领取优惠券
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/gainpasstemplate")
    Response gainPassTemplate(@RequestBody GainPassTemplateRequest request) throws Exception{
        LogGenerator.genLog(
                httpServletRequest,
                request.getUserId(),
                LogConstans.ActionName.GAIN_PASS_TEMPLATE,
                request
        );
        return gainPassTemplateServ.gainPassTemplate(request);
    }

    /**
     * 用户创建评论
     * @param feedback
     * @return
     */
    @ResponseBody
    @PostMapping("/createfeedback")
    Response createFeedback(@RequestBody Feedback feedback){
        LogGenerator.genLog(
                httpServletRequest,
                feedback.getUserId(),
                LogConstans.ActionName.CREATE_FEEDBACK,
                feedback
        );
        return feedbackServ.createFeedback(feedback);
    }

    /**
     * 用户获取评论
     * @param userId
     * @return
     */
    @ResponseBody
    @GetMapping("/getfeedback")
    Response getFeedback(Long userId){
        LogGenerator.genLog(
                httpServletRequest,
                userId,
                LogConstans.ActionName.GET_FEEDBACK,
                null
        );
        return feedbackServ.getFeedback(userId);
    }

    /**
     * 异常演示
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/exception")
    Response exception() throws Exception{
        throw new Exception("全局异常捕获发现一个错误");
    }

}
