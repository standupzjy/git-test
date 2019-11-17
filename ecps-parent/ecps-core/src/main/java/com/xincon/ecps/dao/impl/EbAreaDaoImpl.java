package com.xincon.ecps.dao.impl;

import com.xincon.ecps.dao.EbAreaDao;
import com.xincon.ecps.model.EbArea;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EbAreaDaoImpl extends SqlSessionDaoSupport implements EbAreaDao {

    private String ns = "com.xincon.ecps.mapper.EbAreaMapper.";

    @Override
    public List<EbArea> selectProv(Long parentId) {
        return this.getSqlSession().selectList(ns+"selectProv",parentId);
    }
}
