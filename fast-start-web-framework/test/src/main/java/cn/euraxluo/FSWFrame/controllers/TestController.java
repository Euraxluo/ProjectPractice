package cn.euraxluo.FSWFrame.controllers;

import cn.euraxluo.FSWFrame.MybatisPlugin.MybatisFactory;
import cn.euraxluo.FSWFrame.Service.TestService;
import cn.euraxluo.FSWFrame.beans.AutoWired;
import cn.euraxluo.FSWFrame.context.Json;
import cn.euraxluo.FSWFrame.context.WrapJson;
import cn.euraxluo.FSWFrame.core.ConfScanner;
import cn.euraxluo.FSWFrame.dao.AdminMapper;
import cn.euraxluo.FSWFrame.dao.TagsMapper;
import cn.euraxluo.FSWFrame.pojo.Admin;
import cn.euraxluo.FSWFrame.pojo.Tags;
import cn.euraxluo.FSWFrame.web.mvc.Controller;
import cn.euraxluo.FSWFrame.web.mvc.Param;
import cn.euraxluo.FSWFrame.web.mvc.RestController;
import cn.euraxluo.FSWFrame.web.mvc.Route;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
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
//@RestController
public class TestController {

    @AutoWired
    private TestService testService;

    @AutoWired
    private TagsMapper tagsMapper;

    /**
     * counter people Payroll
     * time+age>=25 => time*1000
     * time+age<25 => time*500
     * @param name
     * @param age
     * @param time
     * @return
     */
    @Route("/payroll:get")
//    @WrapJson
    @Json("Payroll")
    public Object Payroll(@Param("name") String name,@Param("age") String age,@Param("time") String time){
        System.out.println(name);
        Map<String,String> payroll = new HashMap<String, String>();
        payroll.put("name",name);
        payroll.put("age", ConfScanner.getConf("log.folder"));
        payroll.put("time",name);
        payroll.put("monry",""+testService.claSalary(age,time));
        return payroll;
    }

    @Route("/test:get")
//    @Json("html")
    public String test(@Param("name") String name){
        return " <html><body><p>"+name+"</p><script type='text/javascript'>alert('欢迎 Hello!');</script></body></html>";
    }

    @Route("/tags:get")
    @WrapJson
    @Json("tags")
    public List<Tags> tags(){
        /**
         * Autowired
         */
        List<Tags> tags1 = tagsMapper.selectAll();
        return tags1;
    }

    @Route("/admin:get")
    @WrapJson
    public List<Admin> admins(){
        AdminMapper adminMapper = (AdminMapper) MybatisFactory.getMapper(AdminMapper.class);
        List<Admin> admins = adminMapper.selectAll();
        return admins;
    }
}
