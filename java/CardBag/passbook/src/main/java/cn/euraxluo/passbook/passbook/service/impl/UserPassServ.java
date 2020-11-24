package cn.euraxluo.passbook.passbook.service.impl;

import cn.euraxluo.passbook.passbook.constant.Constants;
import cn.euraxluo.passbook.passbook.constant.HBaseTables;
import cn.euraxluo.passbook.passbook.constant.PassStatus;
import cn.euraxluo.passbook.passbook.dao.MerchantsDao;
import cn.euraxluo.passbook.passbook.entity.Merchants;
import cn.euraxluo.passbook.passbook.mapper.PassRowMapper;
import cn.euraxluo.passbook.passbook.service.IUserPassServ;
import cn.euraxluo.passbook.passbook.vo.Pass;
import cn.euraxluo.passbook.passbook.vo.PassInfo;
import cn.euraxluo.passbook.passbook.vo.PassTemplate;
import cn.euraxluo.passbook.passbook.vo.Response;
import com.alibaba.fastjson.JSON;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import java.util.*;
import java.util.stream.Collectors;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service.impl
 * UserPassServ
 * 2019/12/30 9:49
 * author:Euraxluo
 * 获取用户个人优惠券相关服务实现
 */
@Slf4j
@Service
public class UserPassServ implements IUserPassServ {

    private final HbaseTemplate hbaseTemplate;

    private final MerchantsDao merchantsDao;

    @Autowired
    public UserPassServ(HbaseTemplate hbaseTemplate, MerchantsDao merchantsDao) {
        this.hbaseTemplate = hbaseTemplate;
        this.merchantsDao = merchantsDao;
    }

    @Override
    public Response getUserPassInfo(Long userId) throws Exception {
        //利用我们封装的函数,通过指定状态实现
        return getPassInfoByStatus(userId,PassStatus.UNUSED);
    }

    @Override
    public Response getUserUsedPassInfo(Long userId) throws Exception {
        return getPassInfoByStatus(userId,PassStatus.USED);
    }

    @Override
    public Response getUserAllPassInfo(Long userId) throws Exception {
        return getPassInfoByStatus(userId,PassStatus.ALL);
    }

    @Override
    public Response userUsePass(Pass pass) {
        //根据UserId构造行键前缀,通过行键前缀进行查询
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(
                String.valueOf(pass.getUserId())
        ).reverse().toString());
        Scan scan = new Scan();
        List<Filter> filters = new ArrayList<>();
        filters.add(new PrefixFilter(rowPrefix));
        //判断是否相等
        filters.add(new SingleColumnValueFilter(
                HBaseTables.PassTable.FAMILY_I.getBytes(),
                HBaseTables.PassTable.TEMPLATE_ID.getBytes(),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(pass.getTemplateId())
        ));
        filters.add(new SingleColumnValueFilter(
                HBaseTables.PassTable.FAMILY_I.getBytes(),
                HBaseTables.PassTable.CON_DATE.getBytes(),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("-1")//没有被消费设置为-1
        ));

        //传入优惠券信息,在数据库中寻找一个存在且未使用优惠券,然后设置CON_DATE,标志使用
        //设置过滤器
        scan.setFilter(new FilterList(filters));
        List<Pass> passes = hbaseTemplate.find(HBaseTables.PassTable.TABLE_NAME,
                scan,new PassRowMapper());//只会找出来一个值
        if (null == passes || 1 != passes.size()){
            log.error("UserUsePass Error: {}", JSON.toJSONString(pass));
            return Response.failure("UserUsePass Error");
        }
        byte[] FAMILY_I = HBaseTables.PassTable.FAMILY_I.getBytes();
        byte[] CON_DATE = HBaseTables.PassTable.CON_DATE.getBytes();

        List<Mutation> datas = new ArrayList<>();
        Put put = new Put(passes.get(0).getRowKey().getBytes());
        put.addColumn(FAMILY_I,CON_DATE, Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(new Date())));
        datas.add(put);

        hbaseTemplate.saveOrUpdates(HBaseTables.PassTable.TABLE_NAME,datas);

        return Response.success();
    }

    /**
     * 根据优惠券状态获取优惠券信息
     * @param userId
     * @param status
     * @return
     * @throws Exception
     */
    private Response getPassInfoByStatus(Long userId, PassStatus status) throws Exception {
        //根据userId 构造行键前缀
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(String.valueOf(userId)).reverse().toString());
        CompareFilter.CompareOp compareOp =
                status == PassStatus.UNUSED?
                        CompareFilter.CompareOp.EQUAL: CompareFilter.CompareOp.NOT_EQUAL;
        //扫描器
        Scan scan = new Scan();
        List<Filter> filters = new ArrayList<>();
        //1. 行键前缀过滤器,找到特定用户的优惠券
        filters.add(new PrefixFilter(rowPrefix));
        //2. 基于行单元值的过滤器,找到未使用的优惠券
        if(status != PassStatus.ALL){
            filters.add(new SingleColumnValueFilter(
                    HBaseTables.PassTable.FAMILY_I.getBytes(),
                    HBaseTables.PassTable.CON_DATE.getBytes(),
                    compareOp, Bytes.toBytes("-1")
            ));
        }
        //链式过滤
        scan.setFilter(new FilterList(filters));
        //find 操作
        List<Pass> passes = hbaseTemplate.find(HBaseTables.PassTable.TABLE_NAME,scan,new PassRowMapper());
        Map<String,PassTemplate> passTemplateMap = buildPassTemplateMap(passes);
        Map<Integer,Merchants> merchantsMap = buildMerchantsMap(new ArrayList<>(passTemplateMap.values()));
        //填充返回值
        List<PassInfo> res = new ArrayList<>();
        for (Pass pass:passes){
            PassTemplate passTemplate = passTemplateMap.getOrDefault(
                    pass.getTemplateId(),null
            );
            if (null == passTemplate){
                log.error("PassTemplate Null: {}",pass.getTemplateId());
                continue;
            }
            Merchants merchants = merchantsMap.getOrDefault(
                    passTemplate.getId(),null
            );
            if ( null ==  merchants){
                log.error("Merchants Null: {}",passTemplate.getId());
                continue;
            }
            //填充用户领取的优惠券信息
            res.add(new PassInfo(pass,passTemplate,merchants));
        }
        return new Response(res);

    }

    /**
     * 通过获取的Passes 对象构造Map
     * PassTemplate的map
     * @param passes
     * @return
     * @throws Exception
     */
    private Map<String, PassTemplate> buildPassTemplateMap(List<Pass> passes) throws Exception{
        String[] patterns = new String[] {"yyyy-MM-dd"};

        byte[] FAMILY_B = Bytes.toBytes(HBaseTables.PassTemplateTable.FAMILY_B);
        byte[] ID = Bytes.toBytes(HBaseTables.PassTemplateTable.ID);
        byte[] TITLE = Bytes.toBytes(HBaseTables.PassTemplateTable.TITLE);
        byte[] SUMMARY = Bytes.toBytes(HBaseTables.PassTemplateTable.SUMMARY);
        byte[] DESC = Bytes.toBytes(HBaseTables.PassTemplateTable.DESC);
        byte[] HAS_TOKEN = Bytes.toBytes(HBaseTables.PassTemplateTable.HAS_TOKEN);
        byte[] BACKGROUND = Bytes.toBytes(HBaseTables.PassTemplateTable.BACKGROUND);

        byte[] FAMILY_C = Bytes.toBytes(HBaseTables.PassTemplateTable.FAMILY_C);
        byte[] LIMIT = Bytes.toBytes(HBaseTables.PassTemplateTable.LIMIT);
        byte[] START = Bytes.toBytes(HBaseTables.PassTemplateTable.START);
        byte[] END = Bytes.toBytes(HBaseTables.PassTemplateTable.END);
        /** 收集ids */
        List<String> templateIds = passes.stream().map(
                Pass::getTemplateId
        ).collect(Collectors.toList());

        List<Get> templateGets = new ArrayList<>(templateIds.size());
        templateIds.forEach(t->templateGets.add(
                new Get(Bytes.toBytes(t))
        ));
        /** get => results */
        Result[] templateResults = hbaseTemplate.getConnection()
                .getTable(TableName.valueOf(HBaseTables.PassTemplateTable.TABLE_NAME))
                .get(templateGets);

        /** 构造PassTemplateId -> PassTemplate Object 的Map,用于构造PassInfo */
        Map<String,PassTemplate> templateMapIdObj = new HashMap<>();
        for (Result result : templateResults){
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
            /** map: row:passtemplate */
            templateMapIdObj.put(Bytes.toString(result.getRow()),passTemplate);
        }
        return templateMapIdObj;


    }

    /**
     * 通过获取的PassTemplate 对象构造Merchants Map
     * 商户map
     * @param passTemplates
     * @return
     */
    private Map<Integer, Merchants> buildMerchantsMap(List<PassTemplate> passTemplates){
        Map<Integer,Merchants> merchantsMap = new HashMap<>();
        List<Integer> merchantsIds = passTemplates.stream().map(
                PassTemplate::getId
        ).collect(Collectors.toList());
        /** 批量查询*/
        List<Merchants> merchants = merchantsDao.findByIdIn(merchantsIds);
        merchants.forEach(m->merchantsMap.put(m.getId(),m));
        return merchantsMap;
    }

}
