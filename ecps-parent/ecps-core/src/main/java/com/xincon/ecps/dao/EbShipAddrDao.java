package com.xincon.ecps.dao;

import com.xincon.ecps.model.EbShipAddr;

import java.util.List;

public interface EbShipAddrDao {

    public List<EbShipAddr> selectShipAddrByUserId (Long ptlUserId);

    public EbShipAddr selectShipAddrById(Long shipAddrId);

    public void saveAddr(EbShipAddr addr);

    public void updateAddr(EbShipAddr addr);

    public void updateDefaltAddr(Long userId);
}
