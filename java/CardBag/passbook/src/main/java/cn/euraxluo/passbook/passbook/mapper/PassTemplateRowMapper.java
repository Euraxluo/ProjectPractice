package cn.euraxluo.passbook.passbook.mapper;

import cn.euraxluo.passbook.passbook.constant.HBaseTables;
import cn.euraxluo.passbook.passbook.vo.PassTemplate;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.mapper
 * PassTemplateRowMapper
 * 2019/12/29 13:18
 * author:Euraxluo
 * HBase PassTemplate Row To PassTemplate Object
 */
public class PassTemplateRowMapper implements RowMapper<PassTemplate> {

    private static byte[] FAMILY_B = HBaseTables.PassTemplateTable.FAMILY_B.getBytes();
    private static byte[] ID = HBaseTables.PassTemplateTable.ID.getBytes();
    private static byte[] TITLE = HBaseTables.PassTemplateTable.TITLE.getBytes();
    private static byte[] SUMMARY = HBaseTables.PassTemplateTable.SUMMARY.getBytes();
    private static byte[] DESC = HBaseTables.PassTemplateTable.DESC.getBytes();
    private static byte[] HAS_TOKEN = HBaseTables.PassTemplateTable.HAS_TOKEN.getBytes();
    private static byte[] BACKGROUND = HBaseTables.PassTemplateTable.BACKGROUND.getBytes();

    private static byte[] FAMILY_C = HBaseTables.PassTemplateTable.FAMILY_C.getBytes();
    private static byte[] LIMIT = HBaseTables.PassTemplateTable.LIMIT.getBytes();
    private static byte[] START = HBaseTables.PassTemplateTable.START.getBytes();
    private static byte[] END = HBaseTables.PassTemplateTable.END.getBytes();

    @Override
    public PassTemplate mapRow(Result result, int i) throws Exception {
        String[] patterns = new String[] {"yyyy-MM-dd"};
        PassTemplate passTemplate = PassTemplate.builder()
                .id(Bytes.toInt(result.getValue(FAMILY_B, ID)))
                .title(Bytes.toString(result.getValue(FAMILY_B, TITLE)))
                .summary(Bytes.toString(result.getValue(FAMILY_B, SUMMARY)))
                .desc(Bytes.toString(result.getValue(FAMILY_B, DESC)))
                .limit(Bytes.toLong(result.getValue(FAMILY_C, LIMIT)))
                .hasToken(Bytes.toBoolean(result.getValue(FAMILY_B, HAS_TOKEN)))
                .background(Bytes.toInt(result.getValue(FAMILY_B, BACKGROUND)))
                .start(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILY_C, START)), patterns))
                .end(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILY_C, END)), patterns))
                .build();
        return passTemplate;
    }
}
