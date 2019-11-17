package com.xincon.ecps.service.impl;

import com.xincon.ecps.dao.EbBrandDao;
import com.xincon.ecps.model.EbBrand;
import com.xincon.ecps.service.EbBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EbBrandServiceImpl implements EbBrandService {

    @Autowired
    private EbBrandDao brandDao;

    @Override
    public void saveBrand(EbBrand brand) {
        brandDao.saveBrand(brand);
    }

    @Override
    public List<EbBrand> selectBrandByName(String brandName) {
        return brandDao.selectBrandByName(brandName);
    }

    @Override
    public void deleteBrand(Integer brandId) {
        brandDao.deleteBrand(brandId);
    }

    @Override
    public void updateBrand(EbBrand ebBrand) {
        brandDao.updateBrand(ebBrand);
    }

    @Override
    public EbBrand selectBrandById(Integer brandId) {
        return brandDao.selectBrandById(brandId);
    }

    @Override
    public List<EbBrand> selectBrandAll() {
        return brandDao.selectBrandAll();
    }
}
