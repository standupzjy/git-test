package com.xincon.ecps.dao;

import com.xincon.ecps.model.EbParaValue;
import com.xincon.ecps.model.EbSku;

import java.util.List;

public interface EbSkuDao {

    public void saveSku(List<EbSku> skuList, Long itemId);

    public EbSku getSkuById(Long skuId);

    public List<EbSku> selectRedis();

    public List<EbSku> selectSkuDetailList();

}
