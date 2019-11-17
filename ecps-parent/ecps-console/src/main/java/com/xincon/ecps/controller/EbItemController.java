package com.xincon.ecps.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.xincon.ecps.model.*;
import com.xincon.ecps.query.ItemQueryByCondition;
import com.xincon.ecps.service.EbBrandService;
import com.xincon.ecps.service.EbFeatureService;
import com.xincon.ecps.service.EbItemService;
import com.xincon.ecps.utils.ECPSutils;
import com.xincon.ecps.utils.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/item")
public class EbItemController {

    @Autowired
    private EbBrandService brandService;

    @Autowired
    private EbItemService itemService;

    @Autowired
    private EbFeatureService featureService;

    @RequestMapping("/toIndex.do")
    public String toIndex(){
        return "item/index";
    }

    @RequestMapping("/listBrand.do")
    public String listBrand(Model model){
        List<EbBrand> ebBrands = brandService.selectBrandAll();
        model.addAttribute("Brands",ebBrands);
        return "item/listbrand";
    }

    @RequestMapping("/toAddBrand.do")
    public String toAddBrand(){
        return "item/addbrand";
    }

    //验证品牌的名称的重复性
    @RequestMapping("/validBrandName.do")
    public void validBrandName(String brandName, PrintWriter pw){
        List<EbBrand> ebBrands = brandService.selectBrandByName(brandName);
        String flag = "yes";
        if(ebBrands.size() > 0){
            flag = "no";
        }
        pw.write(flag);
    }

    @RequestMapping("/editBrand.do")
    public String editBrand(Integer brandId,Model model){
        EbBrand brand = brandService.selectBrandById(brandId);
        model.addAttribute("brand",brand);
        return "item/editbrand";
    }

    @RequestMapping("/updateBrand.do")
    public String updateBrand(EbBrand ebBrand){
        brandService.updateBrand(ebBrand);
        return "redirect:listBrand.do";
    }

    @RequestMapping("/deletePic.do")
    public void deletePic(String selectPath){
       Client client = new Client();
        WebResource wr = client.resource(selectPath);
        wr.delete();
    }

    @RequestMapping("/deleteBrand.do")
    public String deleteBrand(Integer brandId,String img){
        System.out.println(img);
        if(org.apache.commons.lang.StringUtils.isNotBlank(img)){
            Client client = new Client();
            WebResource wr = null;
            try {
                wr = client.resource(ECPSutils.readProp("upload_file_path")+img);
            } catch (IOException e) {
                e.printStackTrace();
            }
            wr.delete();
        }
        brandService.deleteBrand(brandId);
        return "redirect:listBrand.do";
    }

    @RequestMapping("/saveBrand.do")
    public String saveBrand(EbBrand brand){
        brandService.saveBrand(brand);
        return "redirect:listBrand.do";
    }

    @RequestMapping("/listItem.do")
    public String listItem(ItemQueryByCondition qc,Model model){
        if(qc.getPageNo() == null){
            qc.setPageNo(1);
        }
        Page page = itemService.selectItemByCondition(qc);
        model.addAttribute("page",page);
        //把qc写回去做回显
        model.addAttribute("qc",qc);

        List<EbBrand> ebBrands = brandService.selectBrandAll();
        model.addAttribute("Brands",ebBrands);
        return "item/list";
    }

    @RequestMapping("/toAddItem.do")
    public String addItem(Model model,EbBrand brand){
        List<EbBrand> ebBrands = brandService.selectBrandAll();
        model.addAttribute("Brands",ebBrands);
        //查询出普通属性
        List<EbFeature> commList = featureService.selectCommFeature();
        model.addAttribute("commList",commList);
        //查询出特殊属性
        List<EbFeature> specList = featureService.selectSpecFeature();
        model.addAttribute("specList",specList);
        return "item/addItem";
    }

    @RequestMapping("/addItem.do")
    public String addItem(EbItem item, EbItemClob itemClob, HttpServletRequest request,int divNum){
        //生成商品编号
        item.setItemNo(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));

        /**
         *  tab3的接收
         */
        List<EbFeature> commList = featureService.selectCommFeature();
        List<EbParaValue> paraValues = new ArrayList<>();
        for (EbFeature ebFeature : commList) {
            Long featureId = ebFeature.getFeatureId();
            if(ebFeature.getInputType() == 3){
                String[] parameterValues = request.getParameterValues(featureId + "");
                if(parameterValues != null && parameterValues.length > 0){
                    String paraVals = "";
                    for (String parameterValue : parameterValues) {
                        paraVals = paraVals + parameterValue + ",";
                    }
                    paraVals = paraVals.substring(0,paraVals.length() - 1);
                    EbParaValue pv = new EbParaValue();
                    pv.setParaValue(paraVals);
                    pv.setFeatureId(featureId);
                    paraValues.add(pv);
                }
            }else {
                String parameter = request.getParameter(featureId + "");
                if(StringUtils.isNotBlank(parameter)){
                    EbParaValue pv = new EbParaValue();
                    pv.setParaValue(parameter);
                    pv.setFeatureId(featureId);
                    paraValues.add(pv);
                }
            }

        }

        /**
         * tab4的接收
         * sort1 skuPrice1 marketPrice1 stockInventory1 skuUpperLimit1 sku1 location1 showStatus1 skuType1
         */
        List<EbFeature> specList = featureService.selectSpecFeature();
        List<EbSku> skuList = new ArrayList<>();
        String sort = "sort";
        String skuPrice = "skuPrice";
        String marketPrice = "marketPrice";
        String stockInventory = "stockInventory";
        String skuUpperLimit = "skuUpperLimit";
        String sku = "sku";
        String location = "location";
        String skuType = "skuType";
        String showStatus = "showStatus";
        for (int i = 1; i <= divNum ; i++) {

            String paraPrice = request.getParameter(skuPrice + i);
            String paraInventory = request.getParameter(stockInventory + i);

            if(StringUtils.isNotBlank(paraPrice) && StringUtils.isNotBlank(paraInventory)){
                String paraSort = request.getParameter(sort + i);
                String paraMarPrice = request.getParameter(marketPrice + i);
                String paraUpperLimit = request.getParameter(skuUpperLimit + i);
                String paraSku = request.getParameter(sku + i);
                String paraLocation = request.getParameter(location + i);
                String paraType = request.getParameter(skuType + i);
                String paraShowStatus = request.getParameter(showStatus + i);

                EbSku ebSku = new EbSku();

                ebSku.setSkuPrice(new BigDecimal(paraPrice));
                ebSku.setStockInventory(Integer.parseInt(paraInventory));
                if(StringUtils.isNotBlank(paraSort)){
                    ebSku.setSkuSort(Integer.parseInt(paraSort));
                }
                if(StringUtils.isNotBlank(paraMarPrice)){
                    ebSku.setMarketPrice(new BigDecimal(paraMarPrice));
                }
                if(StringUtils.isNotBlank(paraUpperLimit)){
                    ebSku.setSkuUpperLimit(Integer.parseInt(paraUpperLimit));
                }
                if(StringUtils.isNotBlank(paraType)){
                    ebSku.setSkuType(Short.parseShort(paraType));
                }
                if(StringUtils.isNotBlank(paraShowStatus)){
                    ebSku.setShowStatus(Short.parseShort(paraShowStatus));
                }
                ebSku.setSku(paraSku);
                ebSku.setLocation(paraLocation);
                List<EbSpecValue> evList = new ArrayList<>();
                for (EbFeature feature : specList) {
                    Long featureId = feature.getFeatureId();
                    String specVal = request.getParameter(featureId + "specradio" + i);
                    EbSpecValue ev = new EbSpecValue();
                    ev.setFeatureId(featureId);
                    ev.setSpecValue(specVal);
                    evList.add(ev);
                }
                ebSku.setSpecList(evList);
                skuList.add(ebSku);
            }
        }
        itemService.saveItem(item, itemClob, paraValues, skuList);
        return "redirect:listItem.do?auditStatus=1&showStatus=1";
    }

    @RequestMapping("/listAudit.do")
    public String listAudit(ItemQueryByCondition qc,Model model){
        if(qc.getPageNo() == null){
            qc.setPageNo(1);
        }
        Page page = itemService.selectItemByCondition(qc);
        model.addAttribute("page",page);
        //把qc写回去做回显
        model.addAttribute("qc",qc);

        List<EbBrand> ebBrands = brandService.selectBrandAll();
        model.addAttribute("Brands",ebBrands);
        return "item/listAudit";
    }

    /*
    *   审核
    * */
    @RequestMapping("/auditItem.do")
    public String auditItem(Long itemId,Short auditStatus,String notes){
        itemService.auditConsoleLog(itemId, auditStatus, notes);
        return "redirect:listAudit.do?auditStatus=0&showStatus=1";
    }

    /*
     *   上下架
     * */
    @RequestMapping("/showItem.do")
    public String showItem(Long itemId,Short showStatus,String notes){
        itemService.showConsoleLog(itemId, showStatus, notes);
        String flag = "1";
        if(showStatus == 1){
            flag ="0";
        }
        return "redirect:listItem.do?auditStatus=1&showStatus="+flag;
    }

    @RequestMapping("/publishItem.do")
    public void publishItem(Long itemId,PrintWriter out){
        try {
            String result = itemService.publishItem(itemId, ECPSutils.readProp("ws_pass"));
            out.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}