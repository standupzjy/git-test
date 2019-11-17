package com.xincon.ecps.utils;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ECPSutils {

    private static SolrServer solrServer;

    public static String readProp(String key) throws IOException {

        InputStream in = ECPSutils.class.getClassLoader().getResourceAsStream("ecps_port.properties");
        Properties prop = new Properties();
        prop.load(in);
        String value = (String) prop.getProperty(key);
        return value;
    }

    public static SolrServer getSolrServer() throws IOException{
        if(solrServer != null){
            return solrServer;
        }else {
            String solr_path = ECPSutils.readProp("solr_path");
            solrServer = new HttpSolrServer(solr_path);
            return solrServer;
        }
    }

    public static void printAjax(HttpServletResponse response,String result){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
