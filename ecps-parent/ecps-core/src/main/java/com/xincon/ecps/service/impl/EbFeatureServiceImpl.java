package com.xincon.ecps.service.impl;

import com.xincon.ecps.dao.EbFeatureDao;
import com.xincon.ecps.model.EbFeature;
import com.xincon.ecps.service.EbFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EbFeatureServiceImpl implements EbFeatureService {
    @Autowired
    private EbFeatureDao featureDao;
    @Override
    public List<EbFeature> selectCommFeature() {
        return featureDao.selectCommFeature();
    }

    @Override
    public List<EbFeature> selectSpecFeature() {
        return featureDao.selectSpecFeature();
    }

    @Override
    public List<EbFeature> selectIsSelectFeature() {
        return featureDao.selectIsSelectFeature();
    }
}
