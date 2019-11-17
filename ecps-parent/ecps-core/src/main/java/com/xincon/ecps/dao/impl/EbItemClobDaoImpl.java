package com.xincon.ecps.dao.impl;

import com.xincon.ecps.dao.EbItemClobDao;
import com.xincon.ecps.model.EbItemClob;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class EbItemClobDaoImpl extends SqlSessionDaoSupport implements EbItemClobDao{
    private String ns = "com.xincon.ecps.mapper.EbItemClobMapper.";
    @Override
    public void saveItemClob(EbItemClob itemClob,Long itemId) {
        itemClob.setItemId(itemId);
        this.getSqlSession().insert(ns+"insert", itemClob);
    }
}
