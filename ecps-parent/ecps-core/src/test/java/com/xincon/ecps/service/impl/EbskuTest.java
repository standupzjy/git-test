package com.xincon.ecps.service.impl;

import com.xincon.ecps.dao.EbSkuDao;
import com.xincon.ecps.model.EbBrand;
import com.xincon.ecps.service.EbBrandService;
import com.xincon.ecps.service.EbIndexService;
import com.xincon.ecps.service.EbRedisServices;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:beans.xml"})
public class EbskuTest {

    @Autowired
    private EbRedisServices redisServices;

    @Test
    public void selectSkuList(){
        redisServices.importEbSkuRedis();
    }

}