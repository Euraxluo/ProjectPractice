package cn.euraxluo.passbook.merchants.controller;

import cn.euraxluo.passbook.merchants.service.IMerchantsServ;
import cn.euraxluo.passbook.merchants.vo.CreateMerchantsRequest;
import cn.euraxluo.passbook.merchants.vo.PassTemplate;
import cn.euraxluo.passbook.merchants.vo.Response;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * merchants
 * cn.euraxluo.passbook.merchants.controller
 * MerchantsCtl
 * 2019/12/28 16:06
 * author:Euraxluo
 * TODO
 */
@Slf4j
@RestController
@RequestMapping("/merchants")
public class MerchantsCtl {
    private final IMerchantsServ merchantsServ;

    @Autowired
    public MerchantsCtl(IMerchantsServ merchantsServ) {
        this.merchantsServ = merchantsServ;
    }
    @ResponseBody
    @RequestMapping("/create")
    public Response create(@RequestBody CreateMerchantsRequest request){
        log.info("CreteMerchants:{}", JSON.toJSONString(request));
        return merchantsServ.createMerchants(request);
    }
    @ResponseBody
    @GetMapping("/{id}")
    public Response getMerchantsInfo(@PathVariable Integer id){
        log.info("getMerchantsInfo:{}",id);
        return merchantsServ.buildMerchantsInfoById(id);
    }

    @ResponseBody
    @PostMapping("/drop")
    public Response dropPassTemplate(@RequestBody PassTemplate passTemplate){
        log.info("Drop PassTemplate:{}",passTemplate);
        return merchantsServ.dropPassTemplate(passTemplate);
    }
}
