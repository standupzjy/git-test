package com.xincon.ecps.dao.impl;

import com.xincon.ecps.dao.EbItemDao;
import com.xincon.ecps.dao.EbParaValueDao;
import com.xincon.ecps.model.EbItem;
import com.xincon.ecps.model.EbParaValue;
import com.xincon.ecps.query.ItemQueryByCondition;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EbParaValueDaoImpl extends SqlSessionDaoSupport implements EbParaValueDao {
    private String ns = "com.xincon.ecps.mapper.EbParaValueMapper.";

    @Override
    public void saveParaValue(List<EbParaValue> paraValueList, Long itemId) {
        /*
        *   1.传过去的是list
        *   2.如果用this.getSqlsession来insert会造成多次数据库连接，造成资源浪费
        *   3.通过SqlSession session = this.getSqlSession();获得session，用一个session来insert则不会多次连接
        * */
        SqlSession session = this.getSqlSession();
        for (EbParaValue ebParaValue : paraValueList) {
            ebParaValue.setItemId(itemId);
            session.insert(ns+"insert", ebParaValue);
        }

    }
}
