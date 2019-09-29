package com.zrytech.mytest;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.zrytech.framework.base.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class SendSms {
    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIOkjQjrdNbJQl", "PN1YMbmqT4KGBn5jozetqaIXoV9w9b");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "13477907639");
        request.putQueryParameter("SignName", "油司令");
        request.putQueryParameter("TemplateCode", "SMS_174986506");

        Map map = new HashMap<>();
        map.put("account","13477907639");
        map.put("password","123456a");
        String jsonMap = JsonUtil.toJson(map);
        System.out.println(jsonMap);
        request.putQueryParameter("TemplateParam", jsonMap);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}