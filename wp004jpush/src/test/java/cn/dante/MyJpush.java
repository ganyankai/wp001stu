package cn.dante;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MyJpush {
    public  void jpushAndroid(Map<String, String> param) {
        // 设置好账号的app_key和masterSecret
        //只要appkey一致就可以推送
        String appKey = "e036a343b918462d2aec1873";
        String masterSecret = "57c37bb533af0fc1808d3c8c";
        //创建JPushClient
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        //推送的关键,构造一个payload
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())//指定android平台的用户
                .setAudience(Audience.all())//你项目中的所有用户
                .setNotification(Notification.android(param.get("msg"), "这是title", param))
                //发送内容,这里不要盲目复制粘贴,这里是我从controller层中拿过来的参数)
                .setOptions(Options.newBuilder().setApnsProduction(false).build())
                //这里是指定开发环境,不用设置也没关系
                .setMessage(Message.content(param.get("msg")))//自定义信息
                .build();

        try {
            PushResult pu = jpushClient.sendPush(payload);

        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1(){
        Map<String, String> parm =new HashMap<String, String>();
        //这是我的文章id
        parm.put("id","13");
        //文章标题
        parm.put("Atitle","helloworld" );
        //设置提示信息,内容是文章标题
        parm.put("msg","hello java" );
        //调用ios的
        //然后调用安卓的
        jpushAndroid(parm);

    }
    
    
}
