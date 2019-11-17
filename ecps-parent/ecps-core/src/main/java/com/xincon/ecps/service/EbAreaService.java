package com.xincon.ecps.service;

import com.xincon.ecps.model.EbArea;

import java.util.List;

public interface EbAreaService {

    public List<EbArea> selectProv(Long parentId);

}
