package com.xincon.ecps.controller;

import com.xincon.ecps.model.Cart;
import com.xincon.ecps.service.EbCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class EbCartController {

    @Autowired
    private EbCartService cartService;

    @RequestMapping("/addItemCar.do")
    public String addItemCar(HttpServletRequest request, HttpServletResponse response
    ,Long skuId,Integer quantity){

        try {
            cartService.addCart(request, response, skuId, quantity);

        } catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:listCart.do";
    }

    @RequestMapping("/listCart.do")
    public String listCart(HttpServletRequest request, HttpServletResponse response, Model model){
        try {
            List<Cart> carts = cartService.selectCartList(request, response);
            Integer itemNum = 0;
            BigDecimal totalPrice = new BigDecimal(0);
            for (Cart cart : carts) {
                itemNum = itemNum + cart.getQuantity();
                //multiply是做乘法
                totalPrice = totalPrice.add(cart.getSku().getSkuPrice()
                        .multiply(new BigDecimal(cart.getQuantity())));
            }
            model.addAttribute("itemNum", itemNum);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("cartList", carts);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "shop/car";
    }

    @RequestMapping("/modifyCart.do")
    public String modifyCart(HttpServletRequest request, HttpServletResponse response, Long skuId, Integer modQuantity) throws IOException {
        cartService.modifyCart(request, response, skuId, modQuantity);
        return "redirect:listCart.do";
    }

    @RequestMapping("/deleteCart.do")
    public String deleteCart(HttpServletRequest request, HttpServletResponse response, Long skuId) throws IOException {
        cartService.deleteCart(request, response, skuId);
        return "redirect:listCart.do";
    }

    @RequestMapping("/clearCart.do")
    public String clearCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        cartService.clearCart(request, response);
        return "redirect:listCart.do";
    }

}
