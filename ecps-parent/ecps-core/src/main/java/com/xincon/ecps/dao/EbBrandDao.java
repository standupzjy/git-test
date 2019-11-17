package com.xincon.ecps.dao;

import com.xincon.ecps.model.EbBrand;

import java.util.List;

public interface EbBrandDao {

    public void saveBrand(EbBrand brand);

    public List<EbBrand> selectBrandAll();

    public List<EbBrand> selectBrandByName(String brandName);

    public EbBrand selectBrandById(Integer brandId);

    public void updateBrand(EbBrand ebBrand);

    public void deleteBrand(Integer brandId);

}
