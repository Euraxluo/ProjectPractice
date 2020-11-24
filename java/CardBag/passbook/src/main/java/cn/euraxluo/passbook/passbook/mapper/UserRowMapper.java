package cn.euraxluo.passbook.passbook.mapper;

import cn.euraxluo.passbook.passbook.constant.HBaseTables;
import cn.euraxluo.passbook.passbook.vo.User;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.mapper
 * UserRowMapper
 * 2019/12/29 12:52
 * author:Euraxluo
 * HBase User Row To User Object
 */
public class UserRowMapper implements RowMapper<User> {

    private static byte[] FAMILY_B = HBaseTables.UserTable.FAMILY_B.getBytes();
    private static byte[] NAME = HBaseTables.UserTable.NAME.getBytes();
    private static byte[] AGE = HBaseTables.UserTable.AGE.getBytes();
    private static byte[] SEX = HBaseTables.UserTable.SEX.getBytes();

    private static byte[] FAMILY_O = HBaseTables.UserTable.FAMILY_O.getBytes();
    private static byte[] PHONE = HBaseTables.UserTable.PHONE.getBytes();
    private static byte[] ADDRESS = HBaseTables.UserTable.ADDRESS.getBytes();

    @Override
    public User mapRow(Result result, int i) throws Exception {
        User.BaseInfo baseInfo = User.BaseInfo.builder()
                .name(Bytes.toString(result.getValue(FAMILY_B,NAME)))
                .age(Bytes.toInt(result.getValue(FAMILY_B, AGE)))
                .sex(Bytes.toString(result.getValue(FAMILY_B, SEX)))
                .build();
        User.OtherInfo otherInfo = User.OtherInfo.builder()
                .address(Bytes.toString(result.getValue(FAMILY_O, ADDRESS)))
                .phone(Bytes.toString(result.getValue(FAMILY_O, PHONE)))
                .build();
        User user = User.builder()
                .id(Bytes.toLong(result.getRow()))
                .otherInfo(otherInfo)
                .baseInfo(baseInfo)
                .build();
        return user;
    }
}
