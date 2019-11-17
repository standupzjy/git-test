package com.xincon.ecps.controller;

import com.xincon.ecps.model.*;
import com.xincon.ecps.service.EbBrandService;
import com.xincon.ecps.service.EbFeatureService;
import com.xincon.ecps.service.EbItemService;
import com.xincon.ecps.service.EbSkuService;
import com.xincon.ecps.utils.ECPSutils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/item")
public class EbItemController {

    @Autowired
    private EbBrandService brandService;

    @Autowired
    private EbFeatureService featureService;

    @Autowired
    private EbItemService itemService;

    @Autowired
    private EbSkuService skuService;

    @RequestMapping("/toIndex.do")
    public String toIndex(Model model){
        //查询品牌
        List<EbBrand> brandList = brandService.selectBrandAll();
        model.addAttribute("brandList",brandList);
        //查询筛选属性
        List<EbFeature> featureList = featureService.selectIsSelectFeature();
        model.addAttribute("featureList",featureList);

        return "index";
    }

    @RequestMapping("/listItem.do")
    public String listItem(String price,String keyWords,String paraVals,Long brandId,Model model){
        List<EbItem> list = itemService.selectItemByIndex(price, keyWords, paraVals, brandId);
        model.addAttribute("list",list);
        return "phoneClassification";
    }

    /*
    *   查询商品的详细信息
    *
    *
    **/
    @RequestMapping("/viewItemDetail.do")
    public String viewItemDetail(Long itemId,Model model){
        EbItem item = itemService.selectItemDetailById(itemId);
        model.addAttribute("item", item);
        return "productDetail";
    }

    @RequestMapping("/getSkuById.do")
    public void getSkuById(HttpServletResponse response,Long skuId){

        EbSku sku = skuService.getSkuByIdWithRedis(skuId);
        JSONObject jo = new JSONObject();
        jo.accumulate("sku", sku);
        String result = jo.toString();
        ECPSutils.printAjax(response, result);
    }

}
