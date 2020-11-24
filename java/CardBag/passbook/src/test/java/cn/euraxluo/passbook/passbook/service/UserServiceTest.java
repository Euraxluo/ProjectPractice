package cn.euraxluo.passbook.passbook.service;

import cn.euraxluo.passbook.passbook.vo.User;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service
 * UserServiceTest
 * 2020/2/25 18:46
 * author:Euraxluo
 * 用户服务测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private IUserServ userServ;
    @Test
    public void testCreateUser(){
        User user = new User();
        user.setBaseInfo(new User.BaseInfo("Euraxluo",23,"m"));
        user.setOtherInfo(new User.OtherInfo("15520713306","上海嘉定"));
        try {
            System.err.println(JSON.toJSONString(userServ.createUser(user)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
