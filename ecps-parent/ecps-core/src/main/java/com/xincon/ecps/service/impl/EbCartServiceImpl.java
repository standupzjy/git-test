package com.xincon.ecps.service.impl;

import com.xincon.ecps.model.Cart;
import com.xincon.ecps.model.EbSku;
import com.xincon.ecps.service.EbCartService;
import com.xincon.ecps.service.EbSkuService;
import com.xincon.ecps.utils.ECPSutils;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbCartServiceImpl implements EbCartService{

    @Autowired
    private EbSkuService skuService;

    @Override
    public void addCart(HttpServletRequest request, HttpServletResponse response, Long skuId, Integer quantity) throws IOException {

        List<Cart> carts = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        //把json数组转换成对象
        JsonConfig jf = new JsonConfig();
        //设置要转化的类
        jf.setRootClass(Cart.class);
        jf.setExcludes(new String[]{"sku"});
        if(cookies != null && cookies.length >0){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if(StringUtils.equals(name, ECPSutils.readProp("cart_key"))){
                    String cookieVal = cookie.getValue();
                    //解码
                    System.out.println("解码前："+cookieVal);
                    cookieVal = URLDecoder.decode(cookieVal,"utf-8");
                    System.out.println("解码后："+cookieVal);
                    //把字符串转换成json数组
                    JSONArray ja = JSONArray.fromObject(cookieVal);

                    carts = (List<Cart>) JSONSerializer.toJava(ja, jf);
                    boolean isExist = false;
                    for (Cart cart : carts) {
                        if(cart.getSkuId().longValue() == skuId.longValue()){
                            cart.setQuantity(cart.getQuantity()+quantity);
                            isExist = true;
                            break;
                        }
                    }
                    if(!isExist){
                        Cart cart = new Cart();
                        cart.setQuantity(quantity);
                        cart.setSkuId(skuId);
                        carts.add(cart);
                    }
                }else {
                    Cart cart = new Cart();
                    cart.setQuantity(quantity);
                    cart.setSkuId(skuId);
                    carts.add(cart);
                }
            }
        }

        JSONArray ja = JSONArray.fromObject(carts, jf);
        String result = ja.toString();
        System.out.println("编码前："+result);
        result = URLEncoder.encode(result,"utf-8");
        System.out.println("编码后："+result);
        Cookie cookie = new Cookie(ECPSutils.readProp("cart_key"),result);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
    }

    @Override
    public List<Cart> selectCartList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Cart> carts = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        //把json数组转换成对象
        JsonConfig jf = new JsonConfig();
        //设置要转化的类
        jf.setRootClass(Cart.class);
        jf.setExcludes(new String[]{"sku"});
        if(cookies != null && cookies.length >0){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if(StringUtils.equals(name, ECPSutils.readProp("cart_key"))){
                    String cookieVal = cookie.getValue();
                    //解码
                    cookieVal = URLDecoder.decode(cookieVal,"utf-8");
                    //把字符串转换成json数组
                    JSONArray ja = JSONArray.fromObject(cookieVal);

                    carts = (List<Cart>) JSONSerializer.toJava(ja, jf);
                    for (Cart cart : carts) {
                        EbSku sku = skuService.getSkuDetailWithRedis(cart.getSkuId());
                        cart.setSku(sku);
                    }
                }
            }
        }
        return carts;
    }

    @Override
    public void deleteCart(HttpServletRequest request, HttpServletResponse response, Long skuId) throws IOException {

        List<Cart> carts = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        //把json数组转换成对象
        JsonConfig jf = new JsonConfig();
        //设置要转化的类
        jf.setRootClass(Cart.class);
        jf.setExcludes(new String[]{"sku"});
        if(cookies != null && cookies.length >0){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if(StringUtils.equals(name, ECPSutils.readProp("cart_key"))){
                    String cookieVal = cookie.getValue();
                    //解码
                    cookieVal = URLDecoder.decode(cookieVal,"utf-8");
                    //把字符串转换成json数组
                    JSONArray ja = JSONArray.fromObject(cookieVal);

                    carts = (List<Cart>) JSONSerializer.toJava(ja, jf);
                    for(int i = 0; i < carts.size(); i++){
                        Cart cart = carts.get(i);
                        if(cart.getSkuId().longValue() == skuId.longValue()){
                            carts.remove(cart);
                        }
                    }
                }
            }
        }
        JSONArray ja = JSONArray.fromObject(carts, jf);
        String result = ja.toString();
        result = URLEncoder.encode(result,"utf-8");
        Cookie cookie = new Cookie(ECPSutils.readProp("cart_key"),result);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
    }

    @Override
    public void modifyCart(HttpServletRequest request, HttpServletResponse response, Long skuId, Integer modQuantity) throws IOException {

        List<Cart> carts = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        //把json数组转换成对象
        JsonConfig jf = new JsonConfig();
        //设置要转化的类
        jf.setRootClass(Cart.class);
        jf.setExcludes(new String[]{"sku"});
        if(cookies != null && cookies.length >0){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if(StringUtils.equals(name, ECPSutils.readProp("cart_key"))){
                    String cookieVal = cookie.getValue();
                    //解码
                    cookieVal = URLDecoder.decode(cookieVal,"utf-8");
                    //把字符串转换成json数组
                    JSONArray ja = JSONArray.fromObject(cookieVal);

                    carts = (List<Cart>) JSONSerializer.toJava(ja, jf);
                    for (Cart cart : carts) {
                       if(cart.getSkuId().longValue() == skuId.longValue()){
                           cart.setQuantity(modQuantity);
                       }
                    }
                }
            }
        }
        JSONArray ja = JSONArray.fromObject(carts, jf);
        String result = ja.toString();
        result = URLEncoder.encode(result,"utf-8");
        Cookie cookie = new Cookie(ECPSutils.readProp("cart_key"),result);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
    }

    @Override
    public void clearCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Cart> carts = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        //把json数组转换成对象
        JsonConfig jf = new JsonConfig();
        //设置要转化的类
        jf.setRootClass(Cart.class);
        jf.setExcludes(new String[]{"sku"});
        if(cookies != null && cookies.length >0){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if(StringUtils.equals(name, ECPSutils.readProp("cart_key"))){
                    String cookieVal = cookie.getValue();
                    //解码
                    cookieVal = URLDecoder.decode(cookieVal,"utf-8");
                    //把字符串转换成json数组
                    JSONArray ja = JSONArray.fromObject(cookieVal);

                    carts = (List<Cart>) JSONSerializer.toJava(ja, jf);
                    carts.clear();
                }
            }
        }
        JSONArray ja = JSONArray.fromObject(carts, jf);
        String result = ja.toString();
        result = URLEncoder.encode(result,"utf-8");
        Cookie cookie = new Cookie(ECPSutils.readProp("cart_key"),result);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
    }
}
