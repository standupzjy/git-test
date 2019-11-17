package com.xincon.ecps.dao;

import com.xincon.ecps.model.EbItem;
import com.xincon.ecps.model.EbParaValue;
import com.xincon.ecps.query.ItemQueryByCondition;

import java.util.List;

public interface EbParaValueDao {

    public void saveParaValue(List<EbParaValue> paraValueList,Long itemId);
}
