package cn.euraxluo.passbook.passbook.controller;

import cn.euraxluo.passbook.passbook.log.LogConstans;
import cn.euraxluo.passbook.passbook.log.LogGenerator;
import cn.euraxluo.passbook.passbook.service.IUserPassServ;
import cn.euraxluo.passbook.passbook.service.IUserServ;
import cn.euraxluo.passbook.passbook.vo.Response;
import cn.euraxluo.passbook.passbook.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.controller
 * CreateUserController
 * 2020/2/25 17:19
 * author:Euraxluo
 * TODO
 */
@Slf4j
@RestController
@RequestMapping("/passbook")
public class CreateUserController {
    private final IUserServ userServ;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public CreateUserController(IUserServ userServ, HttpServletRequest httpServletRequest) {
        this.userServ = userServ;
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * 创建用户
     * @param user
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/createuser")
    Response createUser(@RequestBody User user) throws Exception{
        LogGenerator.genLog(
                httpServletRequest,
                -1L,
                LogConstans.ActionName.GET_FEEDBACK,
                user
        );
        return userServ.createUser(user);
    }

}
