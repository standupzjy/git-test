package com.xincon.ecps.dao.impl;

import com.xincon.ecps.dao.EbConsoleLogDao;
import com.xincon.ecps.model.EbConsoleLog;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EbConsoleLogDaoImpl extends SqlSessionDaoSupport implements EbConsoleLogDao {

    private String ns = "com.xincon.ecps.mapper.EbConsoleLogMapper.";

    @Override
    public void saveConsoleLog(EbConsoleLog consoleLog) {
        this.getSqlSession().insert(ns+"insert", consoleLog);
    }
}
