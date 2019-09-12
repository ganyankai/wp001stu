package com.zrytech.framework.newshop.utils;


import com.zrytech.framework.app.constants.DictConstant;
import com.zrytech.framework.app.constants.MessageConstant;
import com.zrytech.framework.common.util.DictUtil;
import com.zrytech.framework.common.util.DictionUtil;
import com.zrytech.framework.newshop.entity.SysMessage;
import com.zrytech.framework.newshop.frontservice.SysMessageService;
import com.zrytech.framework.newshop.repository.SysMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@Slf4j
public class SysMessageUtils {

    public static SysMessageService sysMessageService;

    @Autowired
    public void setDictUtil(SysMessageService sysMessageService) {

        this.sysMessageService = sysMessageService;
    }

    // 保存
    public static void save(SysMessage dto) {

        sysMessageService.save(dto);
        // TODO 推送未写
    }

    //消息保存与发送

    /**
     *
     * @param tel   接收人手机号
     * @param content   消息内容 传递也是key值
     * @param reveicerId    接收人id
     * @param messageType   消息类别    message_type_system  /  message_type_indent
     * @param needSMS   是否需要发送短信 true为需要
     */
    public static void saveAndSend(String tel,Integer reveicerId,String messageType,String content,Boolean needSMS) {
        try {
            SysMessage sysMessage = new SysMessage();
            sysMessage.setContent(content);
            sysMessage.setReveicerId(reveicerId);
            sysMessage.setMessageType(messageType);
            sysMessage.setSenderDate(new Date());
            sysMessage.setMarkRead(false);
            sysMessageService.save(sysMessage);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        String messageTypeCN = DictionUtil.getValue(DictConstant.MESSAGE_TYPE, messageType);
        String contentCN = DictionUtil.getValue(MessageConstant.MESSAGE_CONTENT, content);
        //发送
        try {
            log.info("a");
            JpushUtil.jpush(tel, messageTypeCN, contentCN);
            log.info("b");
        }catch (Exception e){
            log.error("极光推送异常");
        }

        if(needSMS){
            //发送短信 todo

        }
    }


}
