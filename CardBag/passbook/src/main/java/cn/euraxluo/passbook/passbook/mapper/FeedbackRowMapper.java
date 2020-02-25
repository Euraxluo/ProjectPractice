package cn.euraxluo.passbook.passbook.mapper;

import cn.euraxluo.passbook.passbook.constant.HBaseTables;
import cn.euraxluo.passbook.passbook.vo.Feedback;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.mapper
 * FeedbackRowMapper
 * 2019/12/29 14:42
 * author:Euraxluo
 * Feedback HBase Row To Object
 */
public class FeedbackRowMapper implements RowMapper<Feedback> {
    private static byte[] FAMILY_I = HBaseTables.Feedback.FAMILY_I.getBytes();
    private static byte[] USER_ID = HBaseTables.Feedback.USER_ID.getBytes();
    private static byte[] TYPE = HBaseTables.Feedback.TYPE.getBytes();
    private static byte[] TEMPLATE_ID = HBaseTables.Feedback.TEMPLATE_ID.getBytes();
    private static byte[] COMMENT = HBaseTables.Feedback.COMMENT.getBytes();

    @Override
    public Feedback mapRow(Result result, int i) throws Exception {
        Feedback feedback = Feedback.builder()
                .userId(Bytes.toLong(result.getValue(FAMILY_I, USER_ID)))
                .type(Bytes.toString(result.getValue(FAMILY_I, TYPE)))
                .templateId(Bytes.toString(result.getValue(FAMILY_I, TEMPLATE_ID)))
                .comment(Bytes.toString(result.getValue(FAMILY_I, COMMENT)))
                .build();
        return null;
    }
}
