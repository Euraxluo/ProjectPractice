package cn.euraxluo.passbook.merchants.dao;

import cn.euraxluo.passbook.merchants.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * merchants
 * cn.euraxluo.passbook.merchants.dao
 * MerchantsDao
 * 2019/12/27 12:37
 * author:Euraxluo
 * TODO
 */
public interface MerchantsDao extends JpaRepository<Merchants, Integer> {

    /**
     * 根据商户名称获取商户对象
     * @param name
     * @return
     */
    Merchants findByName(String name);
}