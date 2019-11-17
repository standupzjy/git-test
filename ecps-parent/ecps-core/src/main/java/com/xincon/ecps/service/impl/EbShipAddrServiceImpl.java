package com.xincon.ecps.service.impl;

import com.xincon.ecps.dao.EbShipAddrDao;
import com.xincon.ecps.model.EbShipAddr;
import com.xincon.ecps.service.EbShipAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EbShipAddrServiceImpl implements EbShipAddrService{

    @Autowired
    private EbShipAddrDao shipAddrDao;

    @Override
    public List<EbShipAddr> selectShipAddrByUserId(Long ptlUserId) {
        return shipAddrDao.selectShipAddrByUserId(ptlUserId);
    }

    @Override
    public EbShipAddr selectShipAddrById(Long shipAddrId) {
        return shipAddrDao.selectShipAddrById(shipAddrId);
    }

    @Override
    public void saveOrUpdateAddr(EbShipAddr shipAddr) {
        if(shipAddr.getDefaultAddr() == 1){
            shipAddrDao.updateDefaltAddr(shipAddr.getPtlUserId());
        }

        Long shipAddrId = shipAddr.getShipAddrId();
        if(shipAddrId == null){
            shipAddrDao.saveAddr(shipAddr);
        }else {
            shipAddrDao.updateAddr(shipAddr);
        }
    }
}
