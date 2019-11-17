package com.xincon.ecps.service.impl;

import com.xincon.ecps.dao.TsPtlUserDao;
import com.xincon.ecps.model.TsPtlUser;
import com.xincon.ecps.service.TsPtlUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class TsPtlUserServiceImpl implements TsPtlUserService {

    @Autowired
    private TsPtlUserDao userDao;

    @Override
    public TsPtlUser selectUser(Map<String, String> map) {
        return userDao.selectUser(map);
    }
}
