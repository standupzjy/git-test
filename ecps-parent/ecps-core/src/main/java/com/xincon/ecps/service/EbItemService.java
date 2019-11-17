package com.xincon.ecps.service;

import com.xincon.ecps.model.*;
import com.xincon.ecps.query.ItemQueryByCondition;
import com.xincon.ecps.utils.Page;

import java.util.List;

public interface EbItemService {

    public Page selectItemByCondition(ItemQueryByCondition qc);

    public void saveItem(EbItem item, EbItemClob itemClob, List<EbParaValue> paraValueList, List<EbSku> skuList);

    public void auditConsoleLog(Long itemId,Short auditStatus,String notes);

    public void showConsoleLog(Long itemId,Short showStatus,String notes);

    public List<EbItem> selectItemByIndex(String price,String keyWords,String paraVals,Long brandId);

    public EbItem selectItemDetailById(Long itemId);

    public String publishItem(Long itemId,String password);
}
