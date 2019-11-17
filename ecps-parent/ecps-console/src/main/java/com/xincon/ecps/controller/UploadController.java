package com.xincon.ecps.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.xincon.ecps.utils.ECPSutils;
import com.xincon.ecps.utils.UploadResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping("/uploadPic.do")
    public void uploadPic(HttpServletRequest request, PrintWriter out,String lastRealPath){
        //强转request
        MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
        //从表单获得文件
        //获得文件类型input的name
        Iterator<String> fileNames = mr.getFileNames();
        String inputName = fileNames.next();
        MultipartFile file = mr.getFile(inputName);

        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
            //定义上传后的文件名
            String fileName = UUID.randomUUID().toString();
            String originalFilename = file.getOriginalFilename();
            fileName = fileName + originalFilename.substring(originalFilename.lastIndexOf("."));

            //要上传文件的绝对路径
            String realPath = ECPSutils.readProp("upload_file_path")+"/upload/"+fileName;

            //相对路径保存到数据库
            String relativePath = "/upload/"+fileName;

            //由于需要在不同主机上传，不能直接通过流的方式写文件，使用jersey来上传//的web.xml中
        /*
        * 在tomcat 的目录中加入了
        *   <init-param>
                <param-name>readonly</param-name>
                <param-value>false</param-value>
            </init-param>
        这样才能上传文件
        **/
            Client client = Client.create();
            if(StringUtils.isNotBlank(lastRealPath)){
                WebResource wr = client.resource(lastRealPath);
                wr.delete();
            }
            WebResource wr = client.resource(realPath);
            //文件的上传,上传到文件主机上
            wr.put(bytes);

            JSONObject jo = new JSONObject();
            jo.accumulate("realPath", realPath);
            jo.accumulate("relativePath", relativePath);

            String result = jo.toString();
            out.write(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @RequestMapping("/uploadForFck.do")
    public void uploadForFck(HttpServletRequest request, PrintWriter out){
        //强转request
        MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
        //从表单获得文件
        //获得文件类型input的name
        Iterator<String> fileNames = mr.getFileNames();
        String inputName = fileNames.next();
        MultipartFile file = mr.getFile(inputName);

        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
            //定义上传后的文件名
            String fileName = UUID.randomUUID().toString();
            String originalFilename = file.getOriginalFilename();
            fileName = fileName + originalFilename.substring(originalFilename.lastIndexOf("."));

            //要上传文件的绝对路径
            String realPath = ECPSutils.readProp("upload_file_path")+"/upload/"+fileName;

            //相对路径保存到数据库
            String relativePath = "/upload/"+fileName;

            //由于需要在不同主机上传，不能直接通过流的方式写文件，使用jersey来上传//的web.xml中
        /*
        * 在tomcat 的目录中加入了
        *   <init-param>
                <param-name>readonly</param-name>
                <param-value>false</param-value>
            </init-param>
        这样才能上传文件
        **/
            Client client = Client.create();
            WebResource wr = client.resource(realPath);
            //文件的上传,上传到文件主机上
            wr.put(bytes);

            //回调对象的创建
            UploadResponse ur = new UploadResponse(UploadResponse.EN_OK,realPath);
            out.print(ur);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
