package com.xincon.ecps.ws.service.impl;

import com.xincon.ecps.dao.EbItemDao;
import com.xincon.ecps.model.EbItem;
import com.xincon.ecps.utils.ECPSutils;
import com.xincon.ecps.utils.FMutil;
import com.xincon.ecps.ws.service.EbWsItemSersvice;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EbWsItemServiceImpl implements EbWsItemSersvice {

    @Autowired
    private EbItemDao itemDao;

    @Override
    public String publishItem(Long itemId, String password) {

        String result = "success";
        try {
            String ws_pass = ECPSutils.readProp("ws_pass");
            if(StringUtils.equals(password,ws_pass)) {
                Map<String, Object> map = new HashMap<>();
                EbItem item = itemDao.selectItemDetailById(itemId);
                map.put("item", item);
                map.put("path", ECPSutils.readProp("protal_path"));
                map.put("request_file_path", ECPSutils.readProp("upload_file_path"));
                FMutil.ouputFile("productDetail.ftl", item.getItemId() + ".html", map);
            }else {
                result  = "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
