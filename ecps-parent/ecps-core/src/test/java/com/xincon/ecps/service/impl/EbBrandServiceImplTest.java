package com.xincon.ecps.service.impl;

import com.xincon.ecps.model.EbBrand;
import com.xincon.ecps.service.EbBrandService;
import com.xincon.ecps.service.EbIndexService;
import com.xincon.ecps.utils.ECPSutils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:beans.xml"})
public class EbBrandServiceImplTest {

    @Autowired
    private EbBrandService brandService;

    @Autowired
    private EbIndexService indexService;

    @Test
    public void saveBrand() {
        EbBrand brand = new EbBrand();
        brand.setBrandName("康佳");
        brand.setBrandDesc("真的好用");
        brand.setBrandSort(1);
        brand.setImgs("http://xx");
        brand.setWebsite("http://kangjia");
        brandService.saveBrand(brand);
    }

    @Test
    public void selectBrandAll() {
        List<EbBrand> ebBrands = brandService.selectBrandAll();
        for(EbBrand brand : ebBrands){
            System.out.println(brand.getBrandName());
        }
    }

    @Test
    public void index(){
        indexService.importIndex();
    }
    @Test
    public void chTi(){
       // Long timeStamp = System.currentTimeMillis();  //获取当前时间戳
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(1307123l);
        System.out.println("格式化结果：" + sd);


    }
}