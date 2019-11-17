package com.xincon.ecps.dao.impl;

import com.xincon.ecps.dao.EbFeatureDao;
import com.xincon.ecps.model.EbFeature;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EbFeatureDaoImpl extends SqlSessionDaoSupport implements EbFeatureDao {
    String ns = "com.xincon.ecps.mapper.EbFeatureMapper.";
    @Override
    public List<EbFeature> selectCommFeature() {
        return this.getSqlSession().selectList(ns+"selectCommFeature");
    }

    @Override
    public List<EbFeature> selectSpecFeature() {
        return this.getSqlSession().selectList(ns+"selectSpecFeature");
    }

    @Override
    public List<EbFeature> selectIsSelectFeature() {
        return this.getSqlSession().selectList(ns+"selectIsSelectFeature");
    }
}
