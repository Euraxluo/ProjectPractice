package FSWFrame.euraxluo.cn.Service;

import FSWFrame.euraxluo.cn.beans.Bean;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn
 * TestService
 * 2019/11/23 19:04
 * author:Euraxluo
 * TODO
 */
@Bean
public class TestService {
    public Integer claSalary(String age,String time){
        int money;
        if( Integer.parseInt(time)+Integer.parseInt(age)>=25){
            money = Integer.parseInt(time)*1000;
        }else {
            money = Integer.parseInt(time)*500;
        }
        return money;
    }
}
