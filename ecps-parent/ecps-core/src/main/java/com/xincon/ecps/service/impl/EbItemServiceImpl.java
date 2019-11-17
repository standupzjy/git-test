package com.xincon.ecps.service.impl;

import com.xincon.ecps.dao.*;
import com.xincon.ecps.model.*;
import com.xincon.ecps.query.ItemQueryByCondition;
import com.xincon.ecps.service.EbItemService;
import com.xincon.ecps.stub.EbWsItemSersvice;
import com.xincon.ecps.stub.EbWsItemSersviceService;
import com.xincon.ecps.utils.ECPSutils;
import com.xincon.ecps.utils.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class EbItemServiceImpl implements EbItemService {

    @Autowired
    private EbItemDao itemDao;

    @Autowired
    private EbItemClobDao itemClobDao;

    @Autowired
    private EbParaValueDao paraValueDao;

    @Autowired
    private EbSkuDao skuDao;

    @Autowired
    private EbConsoleLogDao consoleLogDao;

    @Override
    public Page selectItemByCondition(ItemQueryByCondition qc) {

        //查询当前查询条件下的总记录数
        Integer totalCount = itemDao.queryByConditionCount(qc);

        Page page = new Page();
        page.setTotalCount(totalCount);
        page.setPageNo(qc.getPageNo());

        int startNum = page.getStartNum();
        int endNum = page.getEndNum();
        qc.setStartNum(startNum);
        qc.setEndNum(endNum);
        List<EbItem> ebItems = itemDao.queryByCondition(qc);
        page.setLists(ebItems);
        return page;
    }

    @Override
    public void saveItem(EbItem item, EbItemClob itemClob, List<EbParaValue> paraValueList,
                         List<EbSku> skuList) {
        itemDao.saveItem(item);
        itemClobDao.saveItemClob(itemClob, item.getItemId());
        paraValueDao.saveParaValue(paraValueList, item.getItemId());
        skuDao.saveSku(skuList, item.getItemId());
    }

    //审核
    @Override
    public void auditConsoleLog(Long itemId, Short auditStatus, String notes) {
        EbItem item = new EbItem();
        item.setItemId(itemId);
        item.setAuditStatus(auditStatus);
        itemDao.updateItem(item);
        EbConsoleLog consoleLog = new EbConsoleLog();
        consoleLog.setEntityId(itemId);
        consoleLog.setEntityName("商品表");
        consoleLog.setOpTime(new Date());
        consoleLog.setOpType("审核");
        consoleLog.setTableName("EB_ITEM");
        consoleLog.setUserId(1l);
        consoleLog.setNotes(notes);
        consoleLogDao.saveConsoleLog(consoleLog);
    }

    //上下架
    @Override
    public void showConsoleLog(Long itemId, Short showStatus, String notes) {
        EbItem item = new EbItem();
        item.setItemId(itemId);
        item.setShowStatus(showStatus);
        itemDao.updateItem(item);
        EbConsoleLog consoleLog = new EbConsoleLog();
        consoleLog.setEntityId(itemId);
        consoleLog.setEntityName("商品表");
        consoleLog.setOpTime(new Date());
        consoleLog.setOpType("上下架");
        consoleLog.setTableName("EB_ITEM");
        consoleLog.setUserId(2l);
        consoleLog.setNotes(notes);
        consoleLogDao.saveConsoleLog(consoleLog);
    }

    @Override
    public List<EbItem> selectItemByIndex(String price, String keyWords, String paraVals, Long brandId) {
        List<EbItem> list = new ArrayList<>();
        try {
            SolrServer solrServer = ECPSutils.getSolrServer();
            SolrQuery sq = new SolrQuery();
            //价钱筛选
            if(StringUtils.isNotBlank(price)){
                String[] priceArr = price.split("-");
                sq.set("fq", "sku_price:["+priceArr[0]+" TO "+priceArr[1]+"]");
            }
            String queryStr = "*:*";
            //品牌筛选
            if(brandId != null){
               queryStr = "brand_id:"+brandId;
            }
            //关键字筛选
            if(StringUtils.isNotBlank(keyWords)){
                if(StringUtils.equals(queryStr, "*:*")){
                    queryStr = "item_keywords:"+keyWords;
                }else {
                    queryStr = queryStr + " AND item_keywords:"+keyWords;
                }
            }
            //属性筛选
            if(StringUtils.isNotBlank(paraVals)){
                String[] paraArr = paraVals.split(",");

                String paraQuery = "";
                for (String s : paraArr) {
                    paraQuery = paraQuery + "para_vals:"+ s +" AND ";
                }
                paraQuery = paraQuery.substring(0,paraQuery.lastIndexOf(" AND "));
                if(StringUtils.equals(queryStr, "*:*")){
                    queryStr = paraQuery;
                }else {
                    queryStr = queryStr + " AND "+paraQuery;
                }
            }

            sq.setHighlight(true);
            sq.addHighlightField("item_name");
            sq.addHighlightField("promotion");
            sq.setHighlightSimplePre("<font color='red'>");
            sq.setHighlightSimplePost("</font>");
            sq.setQuery(queryStr);
            sq.setSort("id", SolrQuery.ORDER.desc);

            QueryResponse qr = solrServer.query(sq);
            SolrDocumentList results = qr.getResults();

            for (SolrDocument result : results) {
                String itemId = (String) result.getFieldValue("id");
                String skuPrice = result.getFieldValue("sku_price").toString();
                String imgs = (String) result.getFieldValue("imgs");
                String itemName = (String) result.getFieldValue("item_name");
                String promotion = (String) result.getFieldValue("promotion");

                Map<String, Map<String, List<String>>> highlighting = qr.getHighlighting();
                if(highlighting != null){
                    Map<String, List<String>> listMap = highlighting.get(itemId);

                    System.out.println("高亮查询:"+listMap);

                    if(listMap != null){
                        List<String> ilist = listMap.get("item_name");
                        if(ilist != null && ilist.size() > 0){
                            String str = "";
                            for (String s : ilist) {
                                str = str +s;
                            }
                            itemName = str;
                        }
                        List<String> plist = listMap.get("promotion");
                        if(plist != null && plist.size() > 0){
                            String str = "";
                            for (String s : plist) {
                                str = str +s;
                            }
                            promotion = str;
                        }
                        
                    }
                }

                EbItem item = new EbItem();

                item.setItemId(Long.parseLong(itemId));
                item.setSkuPrice(new BigDecimal(skuPrice));
                item.setImgs(imgs);
                item.setItemName(itemName);
                item.setPromotion(promotion);
                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public EbItem selectItemDetailById(Long itemId) {
        return itemDao.selectItemDetailById(itemId);
    }

    @Override
    public String publishItem(Long itemId, String password) {
        //创建服务访问集合点的对象
        EbWsItemSersviceService es = new EbWsItemSersviceService();
        //调用get + 服务访问点的 name
        EbWsItemSersvice esPort = es.getEbWsItemSersvicePort();

        return esPort.publishItem(itemId, password);
    }
}
