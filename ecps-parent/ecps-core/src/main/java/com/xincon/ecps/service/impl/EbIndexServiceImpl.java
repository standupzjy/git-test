package com.xincon.ecps.service.impl;

import com.xincon.ecps.dao.EbItemDao;
import com.xincon.ecps.model.EbItem;
import com.xincon.ecps.model.EbParaValue;
import com.xincon.ecps.service.EbIndexService;
import com.xincon.ecps.utils.ECPSutils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EbIndexServiceImpl implements EbIndexService {

    @Autowired
    private EbItemDao itemDao;

    @Override
    public void importIndex() {
        List<EbItem> itemList = itemDao.selectIsItemList();
        try {
            SolrServer solrServer = ECPSutils.getSolrServer();

                for (EbItem item : itemList) {
                    SolrInputDocument sd = new SolrInputDocument();
                sd.addField("id", item.getItemId());
                sd.addField("item_name", item.getItemName());
                sd.addField("imgs", item.getImgs());
                sd.addField("sku_price", item.getSkuPrice());
                sd.addField("promotion", item.getPromotion());
                sd.addField("brand_id", item.getBrandId());
                sd.addField("item_keywords", item.getKeywords());
                String paraVals = "";
                for(EbParaValue paraValue : item.getParaValueList()){
                    paraVals = paraVals + paraValue.getParaValue()+",";
                }
                sd.addField("para_vals", paraVals);
                solrServer.add(sd);
            }
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addIndex() {

    }

    @Override
    public void deleteIndex(String id) {

    }
}
