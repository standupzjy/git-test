package com.xincon.ecps.dao.impl;

import com.xincon.ecps.dao.TsPtlUserDao;
import com.xincon.ecps.model.TsPtlUser;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public class TsPtlUserDaoImpl extends SqlSessionDaoSupport implements TsPtlUserDao {
    private String ns = "com.xincon.ecps.mapper.TsPtlUserMapper.";
    @Override
    public TsPtlUser selectUser(Map<String, String> map) {
        return this.getSqlSession().selectOne(ns+"selectUser", map);
    }
}
