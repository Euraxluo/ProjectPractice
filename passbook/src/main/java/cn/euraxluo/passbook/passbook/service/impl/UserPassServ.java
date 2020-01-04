package cn.euraxluo.passbook.passbook.service.impl;

import cn.euraxluo.passbook.passbook.constant.HBaseTables;
import cn.euraxluo.passbook.passbook.dao.MerchantsDao;
import cn.euraxluo.passbook.passbook.entity.Merchants;
import cn.euraxluo.passbook.passbook.service.IUserPassServ;
import cn.euraxluo.passbook.passbook.vo.Pass;
import cn.euraxluo.passbook.passbook.vo.PassTemplate;
import cn.euraxluo.passbook.passbook.vo.Response;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service.impl
 * UserPassServ
 * 2019/12/30 9:49
 * author:Euraxluo
 * TODO
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
        return null;
    }

    @Override
    public Response getUserUsedPassInfo(Long userId) throws Exception {
        return null;
    }

    @Override
    public Response getUserAllPassInfo(Long userId) throws Exception {
        return null;
    }

    @Override
    public Response userUsePass(Pass pass) {
        return null;
    }


//    private Response getPassInfo

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
