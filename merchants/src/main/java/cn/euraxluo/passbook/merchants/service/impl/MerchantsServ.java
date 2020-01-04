package cn.euraxluo.passbook.merchants.service.impl;

import cn.euraxluo.passbook.merchants.constant.Constants;
import cn.euraxluo.passbook.merchants.constant.ErrorCode;
import cn.euraxluo.passbook.merchants.dao.MerchantsDao;
import cn.euraxluo.passbook.merchants.entity.Merchants;
import cn.euraxluo.passbook.merchants.service.IMerchantsServ;
import cn.euraxluo.passbook.merchants.vo.CreateMerchantsRequest;
import cn.euraxluo.passbook.merchants.vo.CreateMerchantsResponse;
import cn.euraxluo.passbook.merchants.vo.PassTemplate;
import cn.euraxluo.passbook.merchants.vo.Response;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * merchants
 * cn.euraxluo.passbook.merchants.service.impl
 * MerchantsServ
 * 2019/12/28 10:20
 * author:Euraxluo
 * TODO
 */
@Service
@Slf4j
public class MerchantsServ implements IMerchantsServ {

    /** 以构造函数的方式注入可以避免NPE*/
    private final MerchantsDao merchantsDao;

    private final KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    public MerchantsServ(MerchantsDao merchantsDao, KafkaTemplate<String, String> kafkaTemplate) {
        this.merchantsDao = merchantsDao;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Response createMerchants(CreateMerchantsRequest req) {
        Response response = new Response();
        CreateMerchantsResponse merchantsResponse = new CreateMerchantsResponse();

        /**校验传参数据是否完整*/
        ErrorCode errorCode = req.validate(merchantsDao);
        if (errorCode != ErrorCode.SUCCESS){
            merchantsResponse.setId(-1);//这里创建失败,返回-1
            response.setErrorCode(errorCode.getCode());
            response.setErrorMsg(errorCode.getDesc());
        }else {
            Integer id =  merchantsDao.save(req.toMerchants()).getId();
            merchantsResponse.setId(id);
        }
        response.setData(merchantsResponse);
        return response;
    }

    @Override
    public Response buildMerchantsInfoById(Integer id) {
        Response response = new Response();
        /**新版本需要 findById(id).get()
         * 直接这样写会报:NoSuchElementException: No value present
         * */
        Optional<Merchants> optional =  merchantsDao.findById(id);
        Merchants merchants = optional.isPresent()?optional.get():null;
        if (null == merchants){
            response.setErrorCode(ErrorCode.MERCHANTS_NOT_EXIST.getCode());
            response.setErrorMsg(ErrorCode.MERCHANTS_NOT_EXIST.getDesc());
        }
        response.setData(merchants);
        return response;
    }

    @Override
    public Response dropPassTemplate(PassTemplate template) {
        Response response = new Response();
        /**校验商户是否注册过*/
        ErrorCode errorCode = template.validate(merchantsDao);
        if (errorCode != ErrorCode.SUCCESS){
            response.setErrorCode(errorCode.getCode());
            response.setErrorMsg(errorCode.getDesc());
        }else {
            /**序列化*/
            String passTemplate = JSON.toJSONString(template);
            /**发送到kafka*/
            kafkaTemplate.send(
                    Constants.TEMPLATE_TOPIC,
                    Constants.TEMPLATE_TOPIC,
                    passTemplate
            );
            log.info("DropPassTemplate:{}",passTemplate);
        }
        return response;
    }
}
