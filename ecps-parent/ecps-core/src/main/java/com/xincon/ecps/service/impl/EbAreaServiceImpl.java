package com.xincon.ecps.service.impl;

import com.xincon.ecps.dao.EbAreaDao;
import com.xincon.ecps.model.EbArea;
import com.xincon.ecps.service.EbAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EbAreaServiceImpl implements EbAreaService {

    @Autowired
    private EbAreaDao areaDao;

    @Override
    public List<EbArea> selectProv(Long parentId) {
        return areaDao.selectProv(parentId);
    }
}
