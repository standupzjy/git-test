package com.xincon.ecps.dao;

import com.xincon.ecps.model.EbFeature;

import java.util.List;

public interface EbFeatureDao {

    public List<EbFeature> selectCommFeature();

    public List<EbFeature> selectSpecFeature();

    public List<EbFeature> selectIsSelectFeature();

}
