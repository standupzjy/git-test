package com.xincon.ecps.dao.impl;

import com.xincon.ecps.dao.EbBrandDao;
import com.xincon.ecps.dao.EbShipAddrDao;
import com.xincon.ecps.model.EbBrand;
import com.xincon.ecps.model.EbShipAddr;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EbShipAddrImpl extends SqlSessionDaoSupport implements EbShipAddrDao {

    private String ns = "com.xincon.ecps.mapper.EbShipAddrMapper.";

    @Override
    public List<EbShipAddr> selectShipAddrByUserId(Long ptlUserId) {
        return this.getSqlSession().selectList(ns+"selectShipAddrByUserId",ptlUserId);
    }

    @Override
    public EbShipAddr selectShipAddrById(Long shipAddrId) {
        return this.getSqlSession().selectOne(ns+"selectByPrimaryKey", shipAddrId);
    }

    @Override
    public void saveAddr(EbShipAddr addr) {
        this.getSqlSession().insert(ns+"insert", addr);
    }

    @Override
    public void updateAddr(EbShipAddr addr) {
        this.getSqlSession().update(ns+"updateByPrimaryKeySelective", addr);
    }

    @Override
    public void updateDefaltAddr(Long userId) {
        this.getSqlSession().update(ns+"updateDefaltAddr", userId);
    }
}
