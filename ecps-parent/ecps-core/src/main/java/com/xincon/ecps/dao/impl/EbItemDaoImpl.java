package com.xincon.ecps.dao.impl;

import com.xincon.ecps.dao.EbItemDao;
import com.xincon.ecps.model.EbItem;
import com.xincon.ecps.query.ItemQueryByCondition;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EbItemDaoImpl extends SqlSessionDaoSupport implements EbItemDao {
    private String ns = "com.xincon.ecps.mapper.EbItemMapper.";

    @Override
    public List<EbItem> selectIsItemList() {
        return this.getSqlSession().selectList(ns+"selectIsItemList");
    }

    @Override
    public List<EbItem> queryByCondition(ItemQueryByCondition qc) {
        return this.getSqlSession().selectList(ns+"itemQueryByCondition",  qc);
    }

    @Override
    public void saveItem(EbItem item) {
        this.getSqlSession().insert(ns+"insert", item);
    }

    @Override
    public void updateItem(EbItem item) {
        this.getSqlSession().update(ns+"updateByPrimaryKeySelective",item);
    }

    @Override
    public Integer queryByConditionCount(ItemQueryByCondition qc) {
        return this.getSqlSession().selectOne(ns+"itemQueryByConditionCount", qc);
    }

    @Override
    public EbItem selectItemDetailById(Long itemId) {
        return this.getSqlSession().selectOne(ns+"selectItemDetailById", itemId);
    }
}
