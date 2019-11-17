package com.xincon.ecps.dao;

import com.xincon.ecps.model.EbArea;

import java.util.List;

public interface EbAreaDao {

    public List<EbArea> selectProv(Long parentId);

}
