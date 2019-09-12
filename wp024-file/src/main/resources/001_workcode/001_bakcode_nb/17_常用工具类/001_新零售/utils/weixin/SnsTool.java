package com.zrytech.framework.newshop.utils.weixin;

import com.alibaba.fastjson.JSON;
import com.zrytech.framework.base.enums.BaseResult;
import com.zrytech.framework.base.enums.BaseResultEnum;
import com.zrytech.framework.base.exception.BusinessException;
import com.zrytech.framework.newshop.dto.SnsUserInfo;
import com.zrytech.framework.newshop.utils.HttpUtils;
import com.zrytech.framework.newshop.utils.NewShopAssert;
import com.zrytech.framework.payment.config.WxConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SnsTool {

    private static WxConfiguration wxConfiguration;

    @Autowired
    public void setWxConfiguration(WxConfiguration wxConfiguration){
        this.APPID=wxConfiguration.getAppid_wxmn();
        this.SECRET=wxConfiguration.getAppsecret_wxmn();
    }

    private static final String SNS_JSCODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    public static  String APPID = "";

    public static  String SECRET = "";

    public static  SnsCode2Session jscode2session(String code) {
        String url = String.format(SNS_JSCODE2SESSION_URL, APPID, SECRET, code);
        String result = HttpUtils.sendGet(url);
        NewShopAssert.isNotNull(result, BaseResultEnum.JSON_TO_OBJECT_ERROR);

        // 正常回去到数据不返回 errcode， errmsg.
        // {"session_key":"6TSm0x5hR5V9ktUlrxd9aQ==","expires_in":7200,"openid":"o1YH50Fq10YGX77q6NlCVcEAa7LY"}
        SnsCode2Session code2Session;
        try {
            code2Session = JSON.parseObject(result, SnsCode2Session.class);
        } catch (Exception e) {
            log.error("JSON 转对象异常，{}", result);
            throw new BusinessException(new BaseResult(BaseResultEnum.JSON_TO_OBJECT_ERROR));
        }
        if (code2Session.getErrcode() != null) {
            if ("0".equals(code2Session.getErrcode())) { // 请求成功
                return code2Session;
            } else if ("-1".equals(code2Session.getErrcode())) { // 系统繁忙，此时请开发者稍候再试
                log.error("系统繁忙，此时请开发者稍候再试");
                throw new BusinessException(112, "系统繁忙，请稍候再试");
            } else if ("40029".equals(code2Session.getErrcode())) { // code 无效
                throw new BusinessException(112, "code 无效");
            } else if ("45011".equals(code2Session.getErrcode())) { // 频率限制，每个用户每分钟100次
                log.error("频率限制，每个用户每分钟100次");
                throw new BusinessException(112, "系统繁忙，请稍候再试");
            } else if ("40163".equals(code2Session.getErrcode())) { // code been used
                log.error("code been used");
                throw new BusinessException(112, "code 已被使用");
            } else {
                log.error(code2Session.getErrmsg());
                throw new BusinessException(112, "系统繁忙，请稍候再试");
            }
        } else {
            return code2Session;
        }

    }

    public static SnsUserInfos decodeUserInfo(String encryptedData, String iv, String sessionKey) {
        String decrypt = AESUtils.decrypt(encryptedData, sessionKey, iv);
        NewShopAssert.isNotNull(decrypt,BaseResultEnum.JSON_TO_OBJECT_ERROR);
        return JSON.parseObject(decrypt, SnsUserInfos.class);
    }

    public static SnsPhone decodeSnsPhone(String encryptedData, String iv, String sessionKey) {
        String decrypt = AESUtils.decrypt(encryptedData, sessionKey, iv);
        System.out.println("手机号是："+decrypt);
        NewShopAssert.isNotNull(decrypt,BaseResultEnum.JSON_TO_OBJECT_ERROR);
        return JSON.parseObject(decrypt, SnsPhone.class);
    }

}