package com.xincon.ecps.ftl;

import com.xincon.ecps.model.EbBrand;
import com.xincon.ecps.model.EbItem;
import com.xincon.ecps.service.EbBrandService;
import com.xincon.ecps.service.EbIndexService;
import com.xincon.ecps.service.EbItemService;
import com.xincon.ecps.utils.ECPSutils;
import com.xincon.ecps.utils.FMutil;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:beans.xml"})
public class ftlTest {

    @Autowired
    private EbItemService itemService;

    @Test
    public void ftlGener() {
        Map<String,Object> map = new HashMap<>();
        EbItem item = itemService.selectItemDetailById(3120l);
        map.put("item", item);
        map.put("path", "http://localhost:8083");
        map.put("request_file_path", "http://localhost:8082");
        try {
            FMutil.ouputFile("productDetail.ftl", item.getItemId()+".html",map );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}