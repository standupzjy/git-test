package com.xincon.ecps.service;

import com.xincon.ecps.model.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface EbCartService {

    public void addCart(HttpServletRequest request, HttpServletResponse response,Long skuId,Integer quantity) throws IOException;

    public List<Cart> selectCartList(HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void deleteCart(HttpServletRequest request, HttpServletResponse response,Long skuId) throws IOException;

    public void modifyCart(HttpServletRequest request, HttpServletResponse response,Long skuId,Integer modQuantity) throws IOException;

    public void clearCart(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
