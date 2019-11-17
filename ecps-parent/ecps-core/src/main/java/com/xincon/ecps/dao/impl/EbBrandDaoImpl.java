package com.xincon.ecps.dao.impl;

import com.xincon.ecps.dao.EbBrandDao;
import com.xincon.ecps.model.EbBrand;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EbBrandDaoImpl extends SqlSessionDaoSupport implements EbBrandDao {

    private String ns = "mapper.EbBrandMapper.";

    @Override
    public void deleteBrand(Integer brandId) {
        this.getSqlSession().delete(ns+"deleteByPrimaryKey", brandId);
    }

    @Override
    public void updateBrand(EbBrand ebBrand) {
        this.getSqlSession().update(ns+"updateByPrimaryKeySelective",ebBrand);
    }

    @Override
    public EbBrand selectBrandById(Integer brandId) {
        return this.getSqlSession().selectOne(ns+"selectBrandById",brandId);
    }

    @Override
    public void saveBrand(EbBrand brand) {

        this.getSqlSession().insert(ns+"insert", brand);
    }

    @Override
    public List<EbBrand> selectBrandByName(String brandName) {
        return this.getSqlSession().selectList(ns+"selectBrandByName",brandName);
    }

    @Override
    public List<EbBrand> selectBrandAll() {
        return this.getSqlSession().selectList(ns+"selectBrandAll");
    }
}
