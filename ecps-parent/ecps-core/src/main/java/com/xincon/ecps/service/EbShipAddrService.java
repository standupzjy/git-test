package com.xincon.ecps.service;

import com.xincon.ecps.model.EbShipAddr;

import java.util.List;

public interface EbShipAddrService {

    public List<EbShipAddr> selectShipAddrByUserId(Long ptlUserId);

    public EbShipAddr selectShipAddrById(Long shipAddrId);

    public void saveOrUpdateAddr(EbShipAddr shipAddr);
}
