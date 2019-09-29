package com.zrytech.framework.app.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.zrytech.framework.base.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class SendSmsUtil {
//    @Value("${addDriverAl.sms.msgSign : 油司令}")
//    private  String msgSign;
    @Value("${addDriverAl.sms.accessKeyId}")
    private String accessKeyId;
    @Value("${addDriverAl.sms.accessKeySecret}")
    private String accessKeySecret;
    @Value("${addDriverAl.sms.templateId}")
    private String templateId;


    public void sendSms(String tel,Map map) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", tel);
        request.putQueryParameter("SignName", "油司令");
        request.putQueryParameter("TemplateCode", templateId);

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