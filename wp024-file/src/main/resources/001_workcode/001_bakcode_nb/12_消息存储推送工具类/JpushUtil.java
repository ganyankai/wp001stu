package com.zrytech.framework.newshop.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 *  极光推送工具类
 */
@Slf4j
public class JpushUtil {
    protected static final String APP_KEY ="a0e86aa0aaf42dedcdfbabcb";
    protected static final String MASTER_SECRET = "4d23f2612182a47e5c395084";

    /**
     *  推送通知给指定手机号的用户
     * @param tag 用户tag 目前设定为用户手机号
     * @param tilte android消息标题
     * @param content   消息内容
     */
    public static void jpush(String tag,String tilte,String content) {
        try {
        // 设置好账号的app_key和masterSecret
        //创建JPushClient
        JPushClient jpushClient = new JPushClient(MASTER_SECRET,APP_KEY);
        //推送的关键,构造一个payload
        PushPayload payload = PushPayload.newBuilder()
                //指定所有平台用户
                .setPlatform(Platform.all())
                //推送给指定tag用户
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.newBuilder()
                        .setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(tilte)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .build())
                        .build())
                //这里是指定生产环境,不用设置也没关系
                .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();

            PushResult pushResult = jpushClient.sendPush(payload);
        } catch (Exception e) {
            log.error("捕获tag");
            log.error(e.getMessage());
        }
    }

    /**
     *  推送通知所有平台所有用户
     * @param tilte android消息标题
     * @param content   消息内容
     */
    public static void jpushAll(String tilte,String content) {
        // 设置好账号的app_key和masterSecret
        //创建JPushClient
        try {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET,APP_KEY);
        //推送的关键,构造一个payload
        PushPayload payload = PushPayload.newBuilder()
                //指定所有平台用户
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .setAlert(content)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(tilte)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .build())
                        .build())
                //这里是指定生产环境,不用设置也没关系
                .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();

            PushResult pushResult = jpushClient.sendPush(payload);
        } catch (Exception e) {
            log.error("捕获all");
            log.error(e.getMessage());
        }
    }

    @Test
    public  void test(){
//        jpush("134","hello","ccccc");
//        jpushAll("hello","22222");
    }
}
