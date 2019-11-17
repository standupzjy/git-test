package com.xincon.ecps.ws.service;

import javax.jws.WebService;

@WebService
public interface EbWsItemSersvice {

    public String publishItem(Long itemId,String password);

}
