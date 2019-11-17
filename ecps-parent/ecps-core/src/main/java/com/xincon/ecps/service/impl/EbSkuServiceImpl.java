package com.xincon.ecps.service.impl;

import com.xincon.ecps.dao.EbSkuDao;
import com.xincon.ecps.model.EbItem;
import com.xincon.ecps.model.EbSku;
import com.xincon.ecps.model.EbSpecValue;
import com.xincon.ecps.service.EbSkuService;
import com.xincon.ecps.utils.ECPSutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EbSkuServiceImpl implements EbSkuService {

    @Autowired
    private EbSkuDao skuDao;

    @Override
    public EbSku getSkuById(Long skuId) {
        return skuDao.getSkuById(skuId);
    }

    @Override
    public EbSku getSkuByIdWithRedis(Long skuId) {
        EbSku sku = null;
        try {
            String redisIp = ECPSutils.readProp("redisIp");
            String redisPort = ECPSutils.readProp("redisPort");
            Jedis jedis = new Jedis(redisIp, Integer.parseInt(redisPort));

            String skuId1 = jedis.hget("sku:" + skuId, "skuId");
            String skuPrice = jedis.hget("sku:" + skuId, "skuPrice");
            String marketPrice = jedis.hget("sku:" + skuId, "marketPrice");
            String stockInventory = jedis.hget("sku:" + skuId, "stockInventory");
            String itemId = jedis.hget("sku:" + skuId, "itemId");

            sku = new EbSku();

            sku.setSkuId(skuId);
            sku.setSkuPrice(new BigDecimal(skuPrice));
            sku.setMarketPrice(new BigDecimal(marketPrice));
            sku.setStockInventory(Integer.parseInt(stockInventory));
            sku.setItemId(Long.parseLong(itemId));

        }catch (Exception e){
            e.printStackTrace();
        }
        return sku;
    }

    @Override
    public EbSku getSkuDetailWithRedis(Long skuId) {
        EbSku sku = null;
        try {
            String redisIp = ECPSutils.readProp("redisIp");
            //从redis中取sku数据
            String redisPort = ECPSutils.readProp("redisPort");
            Jedis jedis = new Jedis(redisIp, Integer.parseInt(redisPort));

            String skuId1 = jedis.hget("sku:" + skuId, "skuId");
            String skuPrice = jedis.hget("sku:" + skuId, "skuPrice");
            String marketPrice = jedis.hget("sku:" + skuId, "marketPrice");
            String stockInventory = jedis.hget("sku:" + skuId, "stockInventory");
            String itemId = jedis.hget("sku:" + skuId, "itemId");

            //从redis中取item数据
            String itemName = jedis.hget("sku:" + skuId + ":item:" + itemId, "itemName");
            String itemNo = jedis.hget("sku:" + skuId + ":item:" + itemId, "itemNo");
            String imgs = jedis.hget("sku:" + skuId + ":item:" + itemId, "imgs");

            EbItem item = new EbItem();
            item.setItemId(Long.parseLong(itemId));
            item.setItemName(itemName);
            item.setItemNo(itemNo);
            item.setImgs(imgs);

            //从redis中取规格数据
            Set<String> keys = jedis.keys("*");
            List<EbSpecValue> specValueList = new ArrayList<>();
            for (String key : keys) {
                EbSpecValue spec = new EbSpecValue();
                if(key.contains("sku:"+skuId+":spec:")){
                    String specValue = jedis.hget(key, "specValue");
                    String skuId2 = jedis.hget(key, "skuId");
                    String specId = jedis.hget(key, "specId");
                    spec.setSpecId(Long.parseLong(specId));
                    spec.setSpecValue(specValue);
                    spec.setSkuId(Long.parseLong(skuId2));
                    specValueList.add(spec);
                }
            }


            sku = new EbSku();

            sku.setSkuId(skuId);
            sku.setSkuPrice(new BigDecimal(skuPrice));
            sku.setMarketPrice(new BigDecimal(marketPrice));
            sku.setStockInventory(Integer.parseInt(stockInventory));
            sku.setItemId(Long.parseLong(itemId));
            sku.setItem(item);
            sku.setSpecList(specValueList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return sku;
    }
}
