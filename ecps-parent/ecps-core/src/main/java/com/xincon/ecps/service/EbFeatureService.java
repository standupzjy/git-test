package com.xincon.ecps.service;

import com.xincon.ecps.model.EbFeature;

import java.util.List;

public interface EbFeatureService {

    public List<EbFeature> selectCommFeature();

    public List<EbFeature> selectSpecFeature();

    public List<EbFeature> selectIsSelectFeature();

}
