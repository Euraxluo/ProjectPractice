package cn.euraxluo.passbook.passbook.dao;

import cn.euraxluo.passbook.passbook.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * passbook
 * cn.euraxluo.passbook.passbook.dao
 * MerchantsDao
 * 2019/12/29 10:50
 * author:Euraxluo
 * Merchants Dao 接口
 */
public interface MerchantsDao  extends JpaRepository<Merchants,Integer> {
    /**
     * 通过id获取商户对象
     * @param name
     * @return
     */
    Merchants findByName(String name);

    /**
     * 根据商户ids获取商户对象
     * @param ids
     * @return
     */
    List<Merchants> findByIdIn(List<Integer> ids);
}
