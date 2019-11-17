package com.xincon.ecps.dao.impl;

import com.xincon.ecps.dao.EbParaValueDao;
import com.xincon.ecps.dao.EbSkuDao;
import com.xincon.ecps.model.EbParaValue;
import com.xincon.ecps.model.EbSku;
import com.xincon.ecps.model.EbSpecValue;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EbSkuDaoImpl extends SqlSessionDaoSupport implements EbSkuDao {
    private String ns = "com.xincon.ecps.mapper.EbSkuMapper.";
    private String ns2 = "com.xincon.ecps.mapper.EbSpecValueMapper.";

    @Override
    public void saveSku(List<EbSku> skuList, Long itemId) {
        SqlSession session = this.getSqlSession();
        for (EbSku sku : skuList) {
            sku.setItemId(itemId);
            session.insert(ns+"insert", sku);
            List<EbSpecValue> specList = sku.getSpecList();
            for (EbSpecValue ebSpecValue : specList) {
                ebSpecValue.setSkuId(sku.getSkuId());
                session.insert(ns2+"insert", ebSpecValue);
            }
        }
    }

    @Override
    public EbSku getSkuById(Long skuId) {
        return this.getSqlSession().selectOne(ns+"selectByPrimaryKey", skuId);
    }

    @Override
    public List<EbSku> selectRedis() {
        return this.getSqlSession().selectList(ns+"selectRedis");
    }

    @Override
    public List<EbSku> selectSkuDetailList() {
        return this.getSqlSession().selectList(ns+"selectSkuDetailList");
    }
}
