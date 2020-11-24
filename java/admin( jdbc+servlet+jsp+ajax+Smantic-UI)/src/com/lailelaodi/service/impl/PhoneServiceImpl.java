package com.lailelaodi.service.impl;

import com.lailelaodi.dao.PhoneDao;
import com.lailelaodi.dao.impl.PhoneDaoImpl;
import com.lailelaodi.pojo.PageBean;
import com.lailelaodi.pojo.Phone;
import com.lailelaodi.service.PhoneService;

import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName PhoneServiceIpl
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-27 下午10:04
 */
public class PhoneServiceImpl implements PhoneService {
    @Override
    public void insert(Phone phone) throws SQLException {
        PhoneDao dao = new PhoneDaoImpl();
        dao.insert(phone);
    }

    @Override
    public Phone findPhoneById(int sid) throws SQLException {
        PhoneDao dao = new PhoneDaoImpl();
        return dao.findPhoneById(sid);
    }
    @Override
    public void delete(int id) throws SQLException{
        PhoneDao dao = new PhoneDaoImpl();
        dao.delete(id);
    }

    @Override
    public void update(Phone phone) throws SQLException {
        PhoneDao dao = new PhoneDaoImpl();
        dao.update(phone);
    }

    @Override
    public PageBean searchPhone(int id,String phoneName, String theme, int nums) throws SQLException {
        PageBean<Phone> pageBean = new PageBean<Phone>();
        PhoneDao dao = new PhoneDaoImpl();
        List<Phone> list =  dao.searchPhone(id, phoneName, theme, nums);
        pageBean.setList(list);
        return pageBean;

    }


    @Override
    public PageBean findPhoneByPage(int currentPage) throws SQLException {
        PageBean<Phone> pageBean = new PageBean<Phone>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(PhoneDao.PAGE_SIZE);

        PhoneDao dao = new PhoneDaoImpl();
        List<Phone> list = dao.findPhoneByPage(currentPage);
        pageBean.setList(list);

        int count = dao.findcount();
        pageBean.setTotalSize(count);
        pageBean.setTotalPage(count % PhoneDao.PAGE_SIZE == 0 ? (count / PhoneDao.PAGE_SIZE) :(count / PhoneDao.PAGE_SIZE + 1));

        return pageBean;
    }
}
