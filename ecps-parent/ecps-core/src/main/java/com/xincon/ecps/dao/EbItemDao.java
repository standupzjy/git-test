package com.xincon.ecps.dao;

import com.xincon.ecps.model.EbItem;
import com.xincon.ecps.query.ItemQueryByCondition;

import java.util.List;

public interface EbItemDao {

    public List<EbItem> queryByCondition(ItemQueryByCondition qc);

    public Integer queryByConditionCount(ItemQueryByCondition qc);

    public void saveItem(EbItem item);

    public void updateItem(EbItem item);

    public List<EbItem> selectIsItemList();

    public EbItem selectItemDetailById(Long itemId);
}
