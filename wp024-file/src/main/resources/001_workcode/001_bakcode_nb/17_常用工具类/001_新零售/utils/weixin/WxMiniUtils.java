package com.zrytech.framework.newshop.utils.weixin; //生成二维码

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zrytech.framework.newshop.utils.FileUtil;
import com.zrytech.framework.payment.config.WxConfiguration;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class WxMiniUtils {

    private static WxConfiguration wxConfiguration;

    private static String access_token;
    private static Long access_token_updateTime;
    private static  String APPID;
    private static  String SECRET;
    private static FileUtil fileUtil;

    @Autowired
    public void setWxConfiguration(WxConfiguration wxConfiguration){
        this.wxConfiguration=wxConfiguration;
        this.APPID=wxConfiguration.getAppid_wxmn();
        this.SECRET=wxConfiguration.getAppsecret_wxmn();
    }

    @Autowired
    public void setWxConfiguration(FileUtil fileUtil){
        this.fileUtil=fileUtil;
    }

    @Data
    static public class AccessTokenResult{
        public  String errcode;
        public  String access_token;
    }
    public static String getAccessToken() {
        if (access_token != null && (access_token_updateTime + 5400000) > new Date().getTime())
            return access_token;
        AccessTokenResult accessTokenResult = new RestTemplate().getForObject(String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",APPID, SECRET), AccessTokenResult.class);
        if (accessTokenResult.getErrcode() == null || accessTokenResult.getErrcode().equals("0")) {
            access_token_updateTime = new Date().getTime();
            access_token = accessTokenResult.getAccess_token();
        } else System.out.println("error:" + accessTokenResult);
        return accessTokenResult.getAccess_token();
    }

    public static InputStream getQr(String jsonstr) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=ACCESS_TOKEN";
        String url="https://api.weixin.qq.com/wxa/getwxacode?access_token=ACCESS_TOKEN";
        url = url.replace("ACCESS_TOKEN", getAccessToken());
        HttpPost httpPost = new HttpPost(url);  // 接口
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        Map<String, Object> params = new HashMap<>();
       // params.put("scene", jsonstr);  //参数
        params.put("path", "pages/index/main?"+jsonstr);   //位置
      //  params.put("width", 430);
        String body = JSON.toJSONString(params);           //必须是json模式的 post
        StringEntity entity;
        entity = new StringEntity(body);
        entity.setContentType("image/png");
        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
        InputStream inputStream = response.getEntity().getContent();
        return inputStream;
    }

    public static String wxQrPath(String jsonstr){
        try {
            InputStream inputStream = WxMiniUtils.getQr(jsonstr);
            String path = fileUtil.uploadVideo(inputStream);
            return path;
        }catch (Exception e){
            log.info("生成微信二维码失败");
            return null;
        }
    }
}