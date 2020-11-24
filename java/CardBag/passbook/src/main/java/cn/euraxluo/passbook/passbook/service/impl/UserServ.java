package cn.euraxluo.passbook.passbook.service.impl;

import cn.euraxluo.passbook.passbook.constant.Constants;
import cn.euraxluo.passbook.passbook.constant.HBaseTables;
import cn.euraxluo.passbook.passbook.service.IUserServ;
import cn.euraxluo.passbook.passbook.vo.Response;
import cn.euraxluo.passbook.passbook.vo.User;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service.impl
 * UserServ
 * 2019/12/29 18:04
 * author:Euraxluo
 * 用户相关服务实现
 */
@Slf4j
@Service
public class UserServ implements IUserServ {


    /** hbase 客户端*/
    private final HbaseTemplate hbaseTemplate;

    /** redis 客户端*/
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public UserServ(HbaseTemplate hbaseTemplate, StringRedisTemplate redisTemplate) {
        this.hbaseTemplate = hbaseTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Response createUser(User user) throws Exception {

        byte[] FAMILY_B = HBaseTables.UserTable.FAMILY_B.getBytes();
        byte[] NAME = HBaseTables.UserTable.NAME.getBytes();
        byte[] AGE = HBaseTables.UserTable.AGE.getBytes();
        byte[] SEX = HBaseTables.UserTable.SEX.getBytes();

        byte[] FAMILY_O = HBaseTables.UserTable.FAMILY_O.getBytes();
        byte[] PHONE = HBaseTables.UserTable.PHONE.getBytes();
        byte[] ADDRESS = HBaseTables.UserTable.ADDRESS.getBytes();

        /**人数+1*/
        Long curCount = redisTemplate.opsForValue().increment(Constants.USE_COUNT_REDIS_KEY,1);
        Long userId = genUserId(curCount);

        List<Mutation> datas = new ArrayList<Mutation>();
        Put put = new Put(Bytes.toBytes(userId));
        put.addColumn(FAMILY_B, NAME, Bytes.toBytes(user.getBaseInfo().getName()));
        put.addColumn(FAMILY_B, AGE, Bytes.toBytes(user.getBaseInfo().getAge()));
        put.addColumn(FAMILY_B, SEX, Bytes.toBytes(user.getBaseInfo().getSex()));

        put.addColumn(FAMILY_O, PHONE, Bytes.toBytes(user.getOtherInfo().getPhone()));
        put.addColumn(FAMILY_O, ADDRESS, Bytes.toBytes(user.getOtherInfo().getAddress()));

        datas.add(put);

        hbaseTemplate.saveOrUpdates(HBaseTables.UserTable.TABLE_NAME,datas);
        /** 返回userId*/
        user.setId(userId);
        return new Response(user);
    }

    /**
     * 生成userId
     * @param prefix 当前用户数
     * @return 生成的用户id
     */
    private Long genUserId(Long prefix){
        String suffix = RandomStringUtils.randomNumeric(5);
        return Long.valueOf(prefix+suffix);
    }
}
