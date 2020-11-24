package cn.euraxluo.passbook.passbook.service.impl;

import cn.euraxluo.passbook.passbook.constant.HBaseTables;
import cn.euraxluo.passbook.passbook.dao.MerchantsDao;
import cn.euraxluo.passbook.passbook.entity.Merchants;
import cn.euraxluo.passbook.passbook.mapper.PassTemplateRowMapper;
import cn.euraxluo.passbook.passbook.service.IInventoryServ;
import cn.euraxluo.passbook.passbook.service.IUserPassServ;
import cn.euraxluo.passbook.passbook.utils.RowKeyGenUtil;
import cn.euraxluo.passbook.passbook.vo.*;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.LongComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service.impl
 * InventoryServ
 * 2020/2/20 14:16
 * author:Euraxluo
 * 获取库存信息,只返回用户没有领取
 */
@Slf4j
@Service
public class InventoryServ implements IInventoryServ {

    /**Hbase 客户端 */
    private final HbaseTemplate hbaseTemplate;
    private final MerchantsDao merchantsDao;
    private final IUserPassServ userPassServ;

    @Autowired
    public InventoryServ(HbaseTemplate hbaseTemplate, MerchantsDao merchantsDao, IUserPassServ userPassServ) {
        this.hbaseTemplate = hbaseTemplate;
        this.merchantsDao = merchantsDao;
        this.userPassServ = userPassServ;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Response getInventoryInfo(Long userId) throws Exception {
        /** 获取当前用户已经领取的所有优惠券信息 */
        Response allUserPass = userPassServ.getUserAllPassInfo(userId);
        List<PassInfo> passInfos = (List<PassInfo>) allUserPass.getData();
        /** 利用所有的优惠券信息构造出 excludeIds*/
        List<PassTemplate> excludeObj = passInfos.stream().map(PassInfo::getPassTemplate)
                .collect(Collectors.toList());
        List<String> excludeIds = new ArrayList<>();
        excludeObj.forEach(e->excludeIds.add(
                RowKeyGenUtil.genPassTemplateRowKey(e)));

        /**再利用 两个函数完成整个操作 */
        return new Response(new InventoryResponse(userId,buildPassTemplateInfo(getAvailablePassTemplate(excludeIds))));
    }

    /**
     * 获取系统中可用的优惠券 todo:优惠券很多的时候,需要进行缓存或者分页处理
     * @param excludeIds 需要排除的优惠券(已使用的)
     * @return
     */
    private List<PassTemplate> getAvailablePassTemplate(List<String> excludeIds){
        //OR过滤器
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        filterList.addFilter(
                new SingleColumnValueFilter(
                        Bytes.toBytes(HBaseTables.PassTemplateTable.FAMILY_C),
                        Bytes.toBytes(HBaseTables.PassTemplateTable.LIMIT),//通过limit进行过滤
                        CompareFilter.CompareOp.GREATER,//>
                        new LongComparator(0L)//0
                )
        );
        filterList.addFilter(
                new SingleColumnValueFilter(
                        Bytes.toBytes(HBaseTables.PassTemplateTable.FAMILY_C),
                        Bytes.toBytes(HBaseTables.PassTemplateTable.LIMIT),//通过limit进行过滤
                        CompareFilter.CompareOp.EQUAL,//=
                        Bytes.toBytes("-1")//-1
                )
        );
        Scan scan = new Scan();
        scan.setFilter(filterList);
        List<PassTemplate> validTemplates = hbaseTemplate.find(
                HBaseTables.PassTemplateTable.TABLE_NAME,scan,new PassTemplateRowMapper());
        List<PassTemplate> availablePassTemplates = new ArrayList<>();
        Date curTime = new Date();
        /** 对照出来的 validTemplates 进行循环*/
        for (PassTemplate validTemplate : validTemplates){
            /** 判断是否需要排除(是否已使用)  */
            if (excludeIds.contains(RowKeyGenUtil.genPassTemplateRowKey(validTemplate))){
                continue;
            }
            /** 判断是否过期(开始时间小于当前,结束时间大于当前时间) */
            if ( curTime.getTime() >= validTemplate.getStart().getTime()
            && curTime.getTime() <= validTemplate.getEnd().getTime()){
                availablePassTemplates.add(validTemplate);
            }
        }
        return availablePassTemplates;

    }

    /**
     * 构造优惠券的信息
     * @param passTemplates {@link PassTemplate}
     * @return {@link PassTemplateInfo}
     */
    private List<PassTemplateInfo> buildPassTemplateInfo(List<PassTemplate> passTemplates){
        Map<Integer, Merchants> merchantsMap = new HashMap<>();
        /** 先通过传进来的 List<PassTemplate>,获取所有的id,构造Ids*/
        List<Integer> merchantsIds = passTemplates.stream().map(
                PassTemplate::getId
        ).collect(Collectors.toList());
        /** 获取所有的优惠券对应的商户信息 */
        List<Merchants> merchants = merchantsDao.findByIdIn(merchantsIds);
        merchants.forEach(m -> merchantsMap.put(m.getId(),m));
        List<PassTemplateInfo> passTemplateInfos = new ArrayList<>(passTemplates.size());
        /** 填充和构造 */
        for (PassTemplate passTemplate:passTemplates){
            Merchants mc = merchantsMap.getOrDefault(passTemplate.getId(),null);
            if (null == mc  ){
                log.error("Merchants Error: {}",passTemplate.getId());
                continue;
            }
            passTemplateInfos.add(new PassTemplateInfo(passTemplate,mc));
        }
        return passTemplateInfos;
    }
}
