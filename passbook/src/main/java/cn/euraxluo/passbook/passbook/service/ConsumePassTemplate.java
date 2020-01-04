package cn.euraxluo.passbook.passbook.service;

import cn.euraxluo.passbook.passbook.constant.Constants;
import cn.euraxluo.passbook.passbook.vo.PassTemplate;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service
 * ConsumePassTemplate
 * 2019/12/29 17:28
 * author:Euraxluo
 * TODO
 */
@Slf4j
@Component
public class ConsumePassTemplate {
    private final IHBasePassServ ihBasePassServ;

    @Autowired
    public ConsumePassTemplate(IHBasePassServ ihBasePassServ) {
        this.ihBasePassServ = ihBasePassServ;
    }

    @KafkaListener(topics = {Constants.TEMPLATE_TOPIC})
    public void receive(@Payload String passTemplate,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){
        log.info("ConsumePassTemplate receive: {}",passTemplate);
        PassTemplate pt;
        try{
            pt = JSON.parseObject(passTemplate,PassTemplate.class);
            log.info("DropPassTemplateToHBase: {}",ihBasePassServ.dropPassTemplateToHBase(pt));
        }catch (Exception e){
            log.error("Parse PassTemplate Error: {}",e.getMessage());
            return;
        }
    }

}
