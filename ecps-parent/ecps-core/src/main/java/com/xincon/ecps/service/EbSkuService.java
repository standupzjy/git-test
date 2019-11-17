package com.xincon.ecps.service;

import com.xincon.ecps.model.EbSku;

public interface EbSkuService {

    public EbSku getSkuById(Long skuId);

    public EbSku getSkuByIdWithRedis(Long skuId);

    public EbSku getSkuDetailWithRedis(Long skuId);

}
