package FSWFrame.euraxluo.cn.controllers;

import FSWFrame.euraxluo.cn.Service.TestService;
import FSWFrame.euraxluo.cn.beans.AutoWired;
import FSWFrame.euraxluo.cn.web.mvc.Controller;
import FSWFrame.euraxluo.cn.web.mvc.Param;
import FSWFrame.euraxluo.cn.web.mvc.Route;

import java.util.HashMap;
import java.util.Map;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.controllers
 * TestController
 * 2019/11/23 12:44
 * author:Euraxluo
 * TODO
 */

@Controller
public class TestController {
    @AutoWired
    private TestService testService;


    /**
     * counter people Payroll
     * time+age>=25 => time*1000
     * time+age<25 => time*500
     * @param name
     * @param age
     * @param time
     * @return
     */
    @Route("/payroll")
    public String Payroll(@Param("name") String name,@Param("age") String age,@Param("time") String time){
        System.out.println(name);
        Map<String,String> payroll = new HashMap<String, String>();
        payroll.put("name",name);
        payroll.put("age",age);
        payroll.put("time",name);
        payroll.put("monry",""+testService.claSalary(age,time));


        return payroll.toString();
    }
}
