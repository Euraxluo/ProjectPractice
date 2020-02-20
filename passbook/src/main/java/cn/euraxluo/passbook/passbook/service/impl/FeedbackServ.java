package cn.euraxluo.passbook.passbook.service.impl;

import cn.euraxluo.passbook.passbook.constant.HBaseTables;
import cn.euraxluo.passbook.passbook.mapper.FeedbackRowMapper;
import cn.euraxluo.passbook.passbook.service.IFeedbackServ;
import cn.euraxluo.passbook.passbook.utils.RowKeyGenUtil;
import cn.euraxluo.passbook.passbook.vo.Feedback;
import cn.euraxluo.passbook.passbook.vo.Response;
import com.alibaba.fastjson.JSON;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.hadoop.hbase.client.Scan;

import java.util.List;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service.impl
 * FeedbackServ
 * 2019/12/29 22:18
 * author:Euraxluo
 * 评论功能服务实现
 */
@Slf4j
@Service
public class FeedbackServ implements IFeedbackServ {
    /** HBase 客户端*/
    private final HbaseTemplate hbaseTemplate;

    @Autowired
    public FeedbackServ(HbaseTemplate hbaseTemplate) {
        this.hbaseTemplate = hbaseTemplate;
    }

    @Override
    public Response createFeedback(Feedback feedback) {
        if (!feedback.validate()){
            log.error("Feedback Error:{}", JSON.toJSONString(feedback));
            return Response.failure("Feedback Type Error");
        }
        Put put = new Put(Bytes.toBytes(RowKeyGenUtil.genFeedbackRowKey(feedback)));
        put.addColumn(
                Bytes.toBytes(HBaseTables.Feedback.FAMILY_I),
                Bytes.toBytes(HBaseTables.Feedback.USER_ID),
                Bytes.toBytes(feedback.getUserId())
        );
        put.addColumn(
                Bytes.toBytes(HBaseTables.Feedback.FAMILY_I),
                Bytes.toBytes(HBaseTables.Feedback.TYPE),
                Bytes.toBytes(feedback.getType())
        );
        put.addColumn(
                Bytes.toBytes(HBaseTables.Feedback.FAMILY_I),
                Bytes.toBytes(HBaseTables.Feedback.TEMPLATE_ID),
                Bytes.toBytes(feedback.getTemplateId())
        );
        put.addColumn(
                Bytes.toBytes(HBaseTables.Feedback.FAMILY_I),
                Bytes.toBytes(HBaseTables.Feedback.COMMENT),
                Bytes.toBytes(feedback.getComment())
        );
        hbaseTemplate.saveOrUpdate(HBaseTables.Feedback.TABLE_NAME,put);
        return Response.success();
    }

    @Override
    public Response getFeedback(Long userId) {
        /** 反转的userId*/
        byte[] reverseUserId = new StringBuilder(String.valueOf(userId)).reverse().toString().getBytes();
        Scan scan = new Scan();
        /** 前缀过滤器*/
        scan.setFilter(new PrefixFilter(reverseUserId));
        /** 利用scan规则查找前缀相同的数据*/
        List<Feedback> feedbacks = hbaseTemplate.find(HBaseTables.Feedback.TABLE_NAME,scan,new FeedbackRowMapper());
        return new Response(feedbacks);
    }
}
