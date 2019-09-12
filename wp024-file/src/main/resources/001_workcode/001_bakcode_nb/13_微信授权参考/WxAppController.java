package com.zrytech.framework.app.controller.front;

import com.zrytech.framework.app.dto.SnsAuthDto;
import com.zrytech.framework.app.dto.wx.AuthByH5Dto;
import com.zrytech.framework.app.dto.wx.BindTelDto;
import com.zrytech.framework.app.service.front.WxAppService;
import com.zrytech.framework.base.entity.RequestParams;
import com.zrytech.framework.base.entity.ServerResponse;
//import com.zrytech.framework.newshop.dto.AuthByH5Dto;
//import com.zrytech.framework.newshop.dto.SnsAuthDto;
//import com.zrytech.framework.newshop.frontservice.WxAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wxApp")
public class WxAppController {

    @Autowired private WxAppService wxAppService;


    /**
     * @Desinition:微信授权，登录
     * @author:qufei
     * @return:ServerResponse
     */
    @RequestMapping("/login")
    public ServerResponse login(@RequestBody RequestParams<AuthByH5Dto> requestParams) {
        return  this.wxAppService.login(requestParams.getParams());
    }

    /**
     * @Desinition: 绑定手机号
     * @author:qufei
     * @return:ServerResponse
     */
    @RequestMapping("/bindTel")
    public ServerResponse bindTel(@RequestBody RequestParams<BindTelDto> requestParams) {
        return  this.wxAppService.bindTel(requestParams.getParams());
    }



    /**
     * @Desinition:H5微信授权注册
     * @author:qufei
     * @return:ServerResponse
     */
    @RequestMapping("/authByH5")
    public ServerResponse authByH5(@RequestBody RequestParams<AuthByH5Dto> requestParams) {
        return  this.wxAppService.authByH5(requestParams.getParams());
    }


    /**
     * @Desinition:微信小程序授权登录
     * @author:qufei
     * @return:ServerResponse
     */
    @RequestMapping("/authByWx")
    public ServerResponse authByWx(@RequestBody RequestParams<SnsAuthDto> requestParams) {
        return  this.wxAppService.authByWx(requestParams.getParams());
    }

}
