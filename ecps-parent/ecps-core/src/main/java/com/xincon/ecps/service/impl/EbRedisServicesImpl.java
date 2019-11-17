package com.xincon.ecps.service.impl;

import com.xincon.ecps.dao.EbSkuDao;
import com.xincon.ecps.model.EbSku;
import com.xincon.ecps.model.EbSpecValue;
import com.xincon.ecps.service.EbRedisServices;
import com.xincon.ecps.utils.ECPSutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

@Service
public class EbRedisServicesImpl implements EbRedisServices {

    @Autowired
    private EbSkuDao ebSkuDao;

    @Override
    public void importEbSkuRedis() {
        try {
            String redisIp = ECPSutils.readProp("redisIp");
            String redisPort = ECPSutils.readProp("redisPort");
            Jedis jedis = new Jedis(redisIp,Integer.parseInt(redisPort));
            List<EbSku> skuList = ebSkuDao.selectSkuDetailList();

            for (EbSku ebSku : skuList) {
                //把sku的id存储在list中
                jedis.lpush("skuList", ebSku.getSkuId()+"");
                jedis.hset("sku:"+ebSku.getSkuId(), "skuId", ebSku.getSkuId()+"");
                jedis.hset("sku:"+ebSku.getSkuId(), "skuPrice", ebSku.getSkuPrice()+"");
                jedis.hset("sku:"+ebSku.getSkuId(), "marketPrice", ebSku.getMarketPrice()+"");
                jedis.hset("sku:"+ebSku.getSkuId(), "stockInventory", ebSku.getStockInventory()+"");
                jedis.hset("sku:"+ebSku.getSkuId(), "itemId", ebSku.getItemId()+"");
                //同步商品信息
                jedis.hset("sku:"+ebSku.getSkuId()+":item:"+ebSku.getItem().getItemId(), "itemId",ebSku.getItemId()+"" );
                jedis.hset("sku:"+ebSku.getSkuId()+":item:"+ebSku.getItem().getItemId(), "itemNo",ebSku.getItem().getItemNo()+"" );
                jedis.hset("sku:"+ebSku.getSkuId()+":item:"+ebSku.getItem().getItemId(), "itemName",ebSku.getItem().getItemName()+"" );
                jedis.hset("sku:"+ebSku.getSkuId()+":item:"+ebSku.getItem().getItemId(), "imgs",ebSku.getItem().getImgs()+"" );
                //规格信息同步
                List<EbSpecValue> specList = ebSku.getSpecList();
                for (EbSpecValue spec : specList) {
                    jedis.hset("sku:"+ebSku.getSkuId()+":spec:"+spec.getSpecId(),"specValue" ,spec.getSpecValue()+"" );
                    jedis.hset("sku:"+ebSku.getSkuId()+":spec:"+spec.getSpecId(),"specId" ,spec.getSpecId()+"" );
                    jedis.hset("sku:"+ebSku.getSkuId()+":spec:"+spec.getSpecId(),"skuId" ,spec.getSkuId()+"" );
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
