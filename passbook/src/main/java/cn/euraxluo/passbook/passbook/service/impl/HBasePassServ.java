package cn.euraxluo.passbook.passbook.service.impl;

import cn.euraxluo.passbook.passbook.constant.Constants;
import cn.euraxluo.passbook.passbook.constant.HBaseTables;
import cn.euraxluo.passbook.passbook.service.IHBasePassServ;
import cn.euraxluo.passbook.passbook.utils.RowKeyGenUtil;
import cn.euraxluo.passbook.passbook.vo.PassTemplate;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.service.impl
 * HBasePassServ
 * 2019/12/29 17:39
 * author:Euraxluo
 * TODO
 */
@Slf4j
@Service
public class HBasePassServ implements IHBasePassServ {
    @Autowired
    private final HbaseTemplate hbaseTemplate;

    public HBasePassServ(HbaseTemplate hbaseTemplate) {
        this.hbaseTemplate = hbaseTemplate;
    }

    @Override
    public boolean dropPassTemplateToHBase(PassTemplate passTemplate) {
        if (null == passTemplate){
            return false;
        }
        String rowKey = RowKeyGenUtil.genPassTemplateRowKey(passTemplate);

        try {
            if (hbaseTemplate.getConnection()
                    .getTable(TableName.valueOf(HBaseTables.PassTemplateTable.TABLE_NAME))
                    .exists(new Get(Bytes.toBytes(rowKey)))){
                log.warn("RowKey {} is already exists!", rowKey);
                return false;
            }
        } catch (IOException e) {
            log.error("DropPassTemplateToHBase Error: {}",e.getMessage());
            return false;
        }
        /** 写入HBASE*/
        Put put = new Put(Bytes.toBytes(rowKey));
        /**B 族*/
        put.addColumn(
                Bytes.toBytes(HBaseTables.PassTemplateTable.FAMILY_B),
                Bytes.toBytes(HBaseTables.PassTemplateTable.ID),
                Bytes.toBytes(passTemplate.getId())
        );
        put.addColumn(
                Bytes.toBytes(HBaseTables.PassTemplateTable.FAMILY_B),
                Bytes.toBytes(HBaseTables.PassTemplateTable.TITLE),
                Bytes.toBytes(passTemplate.getTitle())
        );
        put.addColumn(
                Bytes.toBytes(HBaseTables.PassTemplateTable.FAMILY_B),
                Bytes.toBytes(HBaseTables.PassTemplateTable.SUMMARY),
                Bytes.toBytes(passTemplate.getSummary())
        );
        put.addColumn(
                Bytes.toBytes(HBaseTables.PassTemplateTable.FAMILY_B),
                Bytes.toBytes(HBaseTables.PassTemplateTable.DESC),
                Bytes.toBytes(passTemplate.getDesc())
        );
        put.addColumn(
                Bytes.toBytes(HBaseTables.PassTemplateTable.FAMILY_B),
                Bytes.toBytes(HBaseTables.PassTemplateTable.HAS_TOKEN),
                Bytes.toBytes(passTemplate.getHasToken())
        );
        put.addColumn(
                Bytes.toBytes(HBaseTables.PassTemplateTable.FAMILY_B),
                Bytes.toBytes(HBaseTables.PassTemplateTable.BACKGROUND),
                Bytes.toBytes(passTemplate.getBackground())
        );

        /**C族*/
        put.addColumn(
                Bytes.toBytes(HBaseTables.PassTemplateTable.FAMILY_C),
                Bytes.toBytes(HBaseTables.PassTemplateTable.LIMIT),
                Bytes.toBytes(passTemplate.getLimit())
        );
        put.addColumn(
                Bytes.toBytes(HBaseTables.PassTemplateTable.FAMILY_C),
                Bytes.toBytes(HBaseTables.PassTemplateTable.START),
                Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(passTemplate.getStart()))
        );
        put.addColumn(
                Bytes.toBytes(HBaseTables.PassTemplateTable.FAMILY_C),
                Bytes.toBytes(HBaseTables.PassTemplateTable.END),
                Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(passTemplate.getEnd()))
        );
        hbaseTemplate.saveOrUpdate(HBaseTables.PassTemplateTable.TABLE_NAME,put);
        return true;
    }
}
