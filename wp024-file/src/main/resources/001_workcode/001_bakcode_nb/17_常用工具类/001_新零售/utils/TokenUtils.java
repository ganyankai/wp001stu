package com.zrytech.framework.newshop.utils;

import com.zrytech.framework.newshop.frontservice.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class TokenUtils {

    private static TokenService tokenService;

    @Autowired
    private void SetTokenUtils(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * 获取用户的渠道id用户没注册直接获取直营渠道
     *
     * @return
     */
    public static Integer getAgentId(String token) {
        return tokenService.getAgentId(token);
    }

}
