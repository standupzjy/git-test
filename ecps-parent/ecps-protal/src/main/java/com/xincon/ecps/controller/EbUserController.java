package com.xincon.ecps.controller;

import com.xincon.ecps.model.EbArea;
import com.xincon.ecps.model.EbShipAddr;
import com.xincon.ecps.model.TsPtlUser;
import com.xincon.ecps.service.EbAreaService;
import com.xincon.ecps.service.EbShipAddrService;
import com.xincon.ecps.service.TsPtlUserService;
import com.xincon.ecps.utils.ECPSutils;
import com.xincon.ecps.utils.MD5Utils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/user")
public class EbUserController{

    @Autowired
    private TsPtlUserService userService;

    @Autowired
    private EbShipAddrService addrService;

    @Autowired
    private EbAreaService areaService;

    @RequestMapping("/toLogin.do")
    public String toLogin(){

        return "passport/login";
    }

    /**
     * 生成验证码的图片
     * @throws Exception
     */
    @RequestMapping("/getImg.do")
    public void getImg(HttpServletResponse response, HttpServletRequest request) {
        System.out.println("#######################生成数字和字母的验证码#######################");
        BufferedImage img = new BufferedImage(68, 22,

                BufferedImage.TYPE_INT_RGB);

        // 得到该图片的绘图对象

        Graphics g = img.getGraphics();

        Random r = new Random();

        Color c = new Color(200, 150, 255);

        g.setColor(c);

        // 填充整个图片的颜色

        g.fillRect(0, 0, 68, 22);

        // 向图片中输出数字和字母

        StringBuffer sb = new StringBuffer();


        char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

        int index, len = ch.length;

        for (int i = 0; i < 4; i++) {

            index = r.nextInt(len);

            g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt

                    (255)));

            g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
            // 输出的  字体和大小

            g.drawString("" + ch[index], (i * 15) + 3, 18);
            //写什么数字，在图片 的什么位置画

            sb.append(ch[index]);

        }
        //把验证码的值放入session中
        request.getSession().setAttribute("piccode", sb.toString());
        System.out.println(sb.toString());
        //把验证码的图片写回浏览器
        try {
            ImageIO.write(img, "JPG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/login.do")
    public String login(String username, String password, String captcha, Model model, HttpSession session){
        String piccode = (String) session.getAttribute("piccode");
        if(!StringUtils.equalsIgnoreCase(captcha, piccode)){
            model.addAttribute("tip","cap_error");
            return "passport/login";
        }
        password = MD5Utils.md5(password);
        Map<String,String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        TsPtlUser tsPtlUser = userService.selectUser(map);
        if(tsPtlUser == null){
            model.addAttribute("tip","user_error");
            return "passport/login";
        }
        session.setAttribute("user", tsPtlUser);
        return "redirect:/item/toIndex.do";

    }

    @RequestMapping("/getUser.do")
    public void getUser(HttpSession session, HttpServletResponse response){
        TsPtlUser user = (TsPtlUser) session.getAttribute("user");

        //System.out.println(user.getUsername());

        JSONObject jo = new JSONObject();
        jo.accumulate("user", user);
        String result = jo.toString();

        ECPSutils.printAjax(response, result);
    }

    @RequestMapping("/login/toPersonIndex.do")
    public String toPersonIndex(){

        return "person/index";
    }

    /**
     * 查询收货地址
     */
    @RequestMapping("/login/toAddress.do")
    public String toAddress(HttpSession session,Model model){
        TsPtlUser user = (TsPtlUser) session.getAttribute("user");
        List<EbShipAddr> addrs = addrService.selectShipAddrByUserId(user.getPtlUserId());
        System.out.println(addrs);
        model.addAttribute("addrs", addrs);

        List<EbArea> areaProv = areaService.selectProv(0l);
        model.addAttribute("areaProv", areaProv);
        return "person/deliverAddress";
    }

    @RequestMapping("/login/getChildArea.do")
    public void getChildArea(Long areaId,HttpServletResponse response){
        List<EbArea> areaList = areaService.selectProv(areaId);
        JSONObject jo = new JSONObject();
        jo.accumulate("areaList", areaList);
        String result = jo.toString();
        ECPSutils.printAjax(response, result);
    }

    @RequestMapping("/login/getAddrById.do")
    public void getAddrById(Long shipAddrId,HttpServletResponse response){
        EbShipAddr shipAddr = addrService.selectShipAddrById(shipAddrId);
        JSONObject jo = new JSONObject();
        jo.accumulate("shipAddr", shipAddr);
        String result = jo.toString();
        ECPSutils.printAjax(response, result);
    }

    @RequestMapping("/login/saveOrUpdateAddr.do")
    public String saveOrUpdateAddr(HttpSession session,EbShipAddr shipAddr){

        TsPtlUser user = (TsPtlUser) session.getAttribute("user");
        shipAddr.setPtlUserId(user.getPtlUserId());
        if(shipAddr.getDefaultAddr() == null){
            shipAddr.setDefaultAddr((short) 0);
        }
        addrService.saveOrUpdateAddr(shipAddr);
        return "redirect:toAddress.do";
    }

}
