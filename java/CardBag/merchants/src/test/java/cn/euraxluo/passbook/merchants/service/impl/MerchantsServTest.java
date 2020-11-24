package cn.euraxluo.passbook.merchants.service.impl;

import cn.euraxluo.passbook.merchants.service.IMerchantsServ;
import cn.euraxluo.passbook.merchants.vo.CreateMerchantsRequest;
import cn.euraxluo.passbook.merchants.vo.PassTemplate;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * merchants
 * cn.euraxluo.passbook.merchants.service.impl
 * MerchantsServTest
 * 2019/12/28 10:56
 * author:Euraxluo
 * TODO
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional//用于回滚
class MerchantsServTest {
    @Autowired
    private IMerchantsServ merchantsServ;

    @Test
    void buildMerchantsInfoById() {
        System.out.println(JSON.toJSONString(merchantsServ.buildMerchantsInfoById(26)));
    }

    @Test
    void createMerchants() {
        CreateMerchantsRequest request = new CreateMerchantsRequest();
        request.setName("测试");
        request.setLogoUrl("http://47.107.44.224:8081/wss/M00/00/00/rBEyeV2u48yAeenCAAQh-OB4f5g065.jpg");
        request.setBusinessLicenseUrl("Euraxluo.github.com");
        request.setPhone("1234567890");
        request.setAddress("上海市");
        System.out.println(JSON.toJSONString(merchantsServ.createMerchants(request)));
    }

    @Test
    void dropPassTemplate() {
        PassTemplate passTemplate = new PassTemplate();
        passTemplate.setId(9);
        passTemplate.setTitle("KFC");
        passTemplate.setSummary("简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介");
        passTemplate.setDesc("详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情详情");
        passTemplate.setLimit(10000L);
        passTemplate.setHasToken(false);
        passTemplate.setBackground(2);
        passTemplate.setStart(DateUtils.addDays(new Date(), -10));
        passTemplate.setEnd(DateUtils.addDays(new Date(), 10));

        System.out.println(JSON.toJSONString(
                merchantsServ.dropPassTemplate(passTemplate)
        ));
    }
}