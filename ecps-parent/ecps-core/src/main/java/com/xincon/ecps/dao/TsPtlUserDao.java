package com.xincon.ecps.dao;

import com.xincon.ecps.model.TsPtlUser;

import java.util.Map;

public interface TsPtlUserDao {

    public TsPtlUser selectUser(Map<String,String> map);

}
