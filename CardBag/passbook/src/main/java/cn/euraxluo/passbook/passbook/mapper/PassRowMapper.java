package cn.euraxluo.passbook.passbook.mapper;

import cn.euraxluo.passbook.passbook.constant.HBaseTables;
import cn.euraxluo.passbook.passbook.vo.Pass;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Date;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.mapper
 * PassRowMapper
 * 2019/12/29 14:31
 * author:Euraxluo
 * HBase Pass Row To Pass Object
 */
public class PassRowMapper  implements RowMapper<Pass> {
    private static byte[] FAMILY_I = HBaseTables.PassTable.FAMILY_I.getBytes();
    private static byte[] USER_ID = HBaseTables.PassTable.USER_ID.getBytes();
    private static byte[] TEMPLATE_ID = HBaseTables.PassTable.TEMPLATE_ID.getBytes();
    private static byte[] TOKEN = HBaseTables.PassTable.TOKEN.getBytes();
    private static byte[] ASSIGNED_DATE = HBaseTables.PassTable.ASSIGNED_DATE.getBytes();
    private static byte[] CON_DATE = HBaseTables.PassTable.CON_DATE.getBytes();

    @Override
    public Pass mapRow(Result result, int i) throws Exception {
        String[] patterns = new String[] {"yyyy-DD-dd"};
        Pass pass = Pass.builder()
                .userId(Bytes.toLong(result.getValue(FAMILY_I, USER_ID)))
                .templateId(Bytes.toString(result.getValue(FAMILY_I, TEMPLATE_ID)))
                .token(Bytes.toString(result.getValue(FAMILY_I, TOKEN)))
                .assignedDate(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILY_I, ASSIGNED_DATE)), patterns))
                .rowKey(Bytes.toString(result.getRow()))
                .build();

        String conDateStr = Bytes.toString(result.getValue(FAMILY_I, CON_DATE));
        if (conDateStr.equals("-1")){
            pass.setConDate(null);
        }else {
            pass.setConDate(DateUtils.parseDate(conDateStr,patterns));
        }
        return pass;
    }
}
