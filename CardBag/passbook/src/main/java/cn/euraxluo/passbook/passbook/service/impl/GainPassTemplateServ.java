package cn.euraxluo.passbook.passbook.service.impl;

import cn.euraxluo.passbook.passbook.constant.Constants;
import cn.euraxluo.passbook.passbook.constant.HBaseTables;
import cn.euraxluo.passbook.passbook.mapper.PassTemplateRowMapper;
import cn.euraxluo.passbook.passbook.service.IGainPassTemplateServ;
import cn.euraxluo.passbook.passbook.utils.RowKeyGenUtil;
import cn.euraxluo.passbook.passbook.vo.GainPassTemplateRequest;
import cn.euraxluo.passbook.passbook.vo.PassTemplate;
import cn.euraxluo.passbook.passbook.vo.Response;
import com.alibaba.fastjson.JSON;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import com.sun.media.sound.JARSoundbankReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.data.Json;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service.impl
 * GainPassTemplateServ
 * 2020/2/25 14:17
 * author:Euraxluo
 * 用户领取优惠券功能实现
 */
@Slf4j
@Service
public class GainPassTemplateServ implements IGainPassTemplateServ {

    @Autowired
    private final HbaseTemplate hbaseTemplate;
    @Autowired
    private final StringRedisTemplate redisTemplate;

    public GainPassTemplateServ(HbaseTemplate hbaseTemplate, StringRedisTemplate redisTemplate) {
        this.hbaseTemplate = hbaseTemplate;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public Response gainPassTemplate(GainPassTemplateRequest request) throws Exception {
        //从request 中获取以及构造需要的对象
        PassTemplate passTemplate;
        //通过对象,生成这个优惠券的RowKey
        String passTemplateId =  RowKeyGenUtil.genPassTemplateRowKey(
                request.getPassTemplate());
        try {
            //获取优惠券,如果为空,会直接catch
            passTemplate = hbaseTemplate.get(
                    HBaseTables.PassTemplateTable.TABLE_NAME,
                    passTemplateId,
                    new PassTemplateRowMapper()
            );
        }catch (Exception ex){
            log.error("Gain PassTemplate Error: {}", JSON.toJSONString(request.getPassTemplate()));
            return Response.failure("Gain PassTemplate Error!");
        }
        //判断limit
        if ( passTemplate.getLimit() <= 1 && -1 != passTemplate.getLimit()){
            log.error("PassTemplate Limit Max: {}",JSON.toJSONString(request.getPassTemplate()));
            return Response.failure("PassTemplate Limit Max");
        }
        //判断时效性
        Date curTime = new Date();
        if (!(curTime.getTime() >= passTemplate.getStart().getTime())){
            log.error("PassTemplate ValidTime Error: {}", JSON.toJSONString(request.getPassTemplate()));
            return Response.failure("PassTemplate ValidTime Error");
        }

        //减去优惠券的limit
        if (-1 != passTemplate.getLimit()){
            List<Mutation> datas = new ArrayList<>();
            byte[] FAMILY_C = HBaseTables.PassTemplateTable.FAMILY_C.getBytes();
            byte[] LIMIT = HBaseTables.PassTemplateTable.LIMIT.getBytes();

            Put put = new Put(Bytes.toBytes(passTemplateId));
            put.addColumn(FAMILY_C,LIMIT,
                    Bytes.toBytes(passTemplate.getLimit()-1));
            datas.add(put);
            hbaseTemplate.saveOrUpdates(HBaseTables.PassTemplateTable.TABLE_NAME,datas);
        }
        //将优惠券保存到用户优惠券表
        if (!addPassForUser(request,passTemplate.getId(),passTemplateId)){
            return Response.failure("GainPassTemplate Failure");
        }

        return Response.success();
    }


    /**
     * 给用户添加优惠券
     * @param request {@link GainPassTemplateRequest}
     * @param merchantsId
     * @param passTemplateId
     * @return true/false
     * @throws Exception
     */
    private boolean addPassForUser(GainPassTemplateRequest request,
                                   Integer merchantsId,
                                   String passTemplateId) throws Exception{
        byte[] FAMILY_I = HBaseTables.PassTable.FAMILY_I.getBytes();
        byte[] USER_ID = HBaseTables.PassTable.USER_ID.getBytes();
        byte[] TEMPLATE_ID = HBaseTables.PassTable.TEMPLATE_ID.getBytes();
        byte[] TOKEN = HBaseTables.PassTable.TOKEN.getBytes();
        byte[] ASSIGNED_DATE = HBaseTables.PassTable.ASSIGNED_DATE.getBytes();
        byte[] CON_DATE = HBaseTables.PassTable.CON_DATE.getBytes();
        List<Mutation> datas = new ArrayList<>();
        Put put = new Put(Bytes.toBytes(RowKeyGenUtil.genPassRowKey(request)));
        put.addColumn(FAMILY_I,USER_ID,Bytes.toBytes(request.getUserId()));
        put.addColumn(FAMILY_I,TEMPLATE_ID,Bytes.toBytes(passTemplateId));

        if (request.getPassTemplate().getHasToken()){
            //直接pop减少一个
            String token = redisTemplate.opsForSet().pop(passTemplateId);
            if (null == token){
                log.error("Token not exits: {}",passTemplateId);
                return false;
            }
            //然后写入文件
            recordTokenToFile(merchantsId,passTemplateId,token);
            put.addColumn(FAMILY_I,TOKEN,Bytes.toBytes(token));
        }else {
            put.addColumn(FAMILY_I,TOKEN,Bytes.toBytes("-1"));
        }
        //领取日期为当前
        put.addColumn(FAMILY_I,ASSIGNED_DATE,
                Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(new Date())));
        //消费日期为-1
        put.addColumn(FAMILY_I,CON_DATE,Bytes.toBytes("-1"));

        //保存或者更新操作
        datas.add(put);
        hbaseTemplate.saveOrUpdates(HBaseTables.PassTable.TABLE_NAME,datas);
        return true;
    }


    /**
     * 将已使用大的token 记录到文件中
     * @param merchantsId 商户Id
     * @param passTemplateId 优惠券Id
     * @param token  分配的优惠券token
     */
    private void recordTokenToFile(Integer merchantsId,
                                   String passTemplateId,
                                   String token) throws Exception{
        Files.write(
                Paths.get(Constants.TOKEN_DIR,
                        String.valueOf(merchantsId),
                        passTemplateId+ Constants.USED_TOKEN_SUFFIX),
                (token + "\n").getBytes(),
                StandardOpenOption.APPEND
        );

    }

}
