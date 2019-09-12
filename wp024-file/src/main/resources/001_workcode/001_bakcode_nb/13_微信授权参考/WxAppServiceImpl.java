package com.zrytech.framework.app.service.front.impl;

import com.zrytech.framework.app.Constans.NewShopConstant;
import com.zrytech.framework.app.configuration.WxConfiguration;
//import com.zrytech.framework.app.constants.DictConstant;
import com.zrytech.framework.app.dto.SnsAuthDto;
import com.zrytech.framework.app.dto.SnsUserInfo;
import com.zrytech.framework.app.dto.WxInfoDto;
import com.zrytech.framework.app.dto.wx.AuthByH5Dto;
//import com.zrytech.framework.app.enums.CommonResult;
//import com.zrytech.framework.app.enums.ResultEnum;
import com.zrytech.framework.app.dto.wx.BindTelDto;
import com.zrytech.framework.app.mapper.SysCustomerMapper;
import com.zrytech.framework.app.service.front.WxAppService;
import com.zrytech.framework.app.utils.*;
import com.zrytech.framework.base.constant.Constant;
import com.zrytech.framework.base.entity.ServerResponse;
import com.zrytech.framework.base.enums.BaseResult;
import com.zrytech.framework.base.enums.BaseResultEnum;
import com.zrytech.framework.base.enums.FileTypeEnum;
import com.zrytech.framework.base.exception.BusinessException;
import com.zrytech.framework.base.util.Assert;
import com.zrytech.framework.base.util.JsonUtil;
import com.zrytech.framework.base.util.TokenUtil;
import com.zrytech.framework.common.controller.core.UserExtendInfo;
import com.zrytech.framework.common.entity.SysCustomer;
import com.zrytech.framework.common.repository.CustomerRepository;
import com.zrytech.framework.common.util.VideoTool;
//import com.zrytech.framework.distribution.dto.DistributionTaskDto;
//import com.zrytech.framework.distribution.entity.Invitation;
//import com.zrytech.framework.distribution.entity.VipInfo;
//import com.zrytech.framework.distribution.repository.InvitationRepository;
//import com.zrytech.framework.distribution.task.CustomerQueue;
//import com.zrytech.framework.newshop.contants.NewShopConstant;
//import com.zrytech.framework.newshop.dto.AuthByH5Dto;
//import com.zrytech.framework.newshop.dto.SnsAuthDto;
//import com.zrytech.framework.newshop.dto.SnsUserInfo;
//import com.zrytech.framework.newshop.dto.WxInfoDto;
//import com.zrytech.framework.newshop.entity.TbAgent;
//import com.zrytech.framework.newshop.entity.TbUserGoodFriend;
//import com.zrytech.framework.newshop.entity.app.SysCustomer;
//import com.zrytech.framework.newshop.frontservice.WxAppService;
//import com.zrytech.framework.newshop.repository.ExtendRepository;
//import com.zrytech.framework.newshop.repository.TbAgentRepository;
//import com.zrytech.framework.newshop.repository.UserGoodFriendRepository;
//import com.zrytech.framework.newshop.utils.CodeUtils;
//import com.zrytech.framework.newshop.utils.NewShopAssert;
//import com.zrytech.framework.newshop.utils.QRCodeUtils2;
//import com.zrytech.framework.newshop.utils.weixin.SnsCode2Session;
//import com.zrytech.framework.newshop.utils.weixin.SnsTool;
//import com.zrytech.framework.newshop.utils.weixin.SnsUserInfos;
//import com.zrytech.framework.payment.config.WxConfiguration;
import com.zrytech.framework.link.service.SmsService;
import com.zrytech.framework.link.service.impl.SmsSeriveImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WxAppServiceImpl implements WxAppService {

    @Autowired
    private WxConfiguration wxConfiguration;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserExtendInfo userExtendInfo;
    @Autowired
    private SysCustomerMapper sysCustomerMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SmsService smsService;

    //app登录
    public ServerResponse login(AuthByH5Dto dto) {
        Assert.isNotBlank(dto);
        SnsUserInfo snsUserInfo = null;
        SysCustomer customer = null;

        Map<String,Object> map = new HashMap<>();

        if (dto.getCode() != null) {
            WxInfoDto wxInfoDto = getOpenId(dto, NewShopConstant.WX_TYPE_APP);
            try {
                snsUserInfo = getSnsUserInfoByH5(wxInfoDto);
                customer = sysCustomerMapper.findByOpenId(snsUserInfo.getOpenid());
                if(customer!=null){
                    //openid存在
                    map.put("customer",customer);
                    map.put("isAuthorization","1");
                    map.put("token",getToken(customer));
                    return ServerResponse.successWithData(map);
                }else {
                    //不存在返回用户信息
                    map.put("customer",customer);
                    map.put("isAuthorization","0");
                    map.put("token",getToken(customer));
                    return ServerResponse.successWithData(map);
                }

            }catch (Exception e){
                log.error("获取用户信息失败" + e);
            }
        }
        throw new BusinessException(112,"获取用户信息失败");
    }


    @Override
    public ServerResponse authByH5(AuthByH5Dto dto) {
        Assert.isNotBlank(dto);
        Assert.isNotBlank(dto.getCode());
//        System.out.println(dto.getAgentId());
        System.out.println(dto.getCustomerId());
//        if (dto.getAgentId() == null && dto.getCustomerId() == null) {
//            throw new BusinessException(new CommonResult(ResultEnum.PARAMETER_ERROR));
//        }
//        WxInfoDto wxInfoDto = getOpenId(dto, NewShopConstant.WX_TYPE_H5);
//        WxInfoDto wxInfoDto = getOpenId(dto, NewShopConstant.WX_TYPE_H5);
        WxInfoDto wxInfoDto = getOpenId(dto, "h5");
        SnsUserInfo snsUserInfo = null;
        try {
            snsUserInfo = getSnsUserInfoByH5(wxInfoDto);
        } catch (Exception e) {
            log.error("获取用户信息失败" + e);
        }
        SysCustomer customer = new SysCustomer();
        customer.setUnionId(snsUserInfo.getUnionid());
        List<SysCustomer> list = this.customerRepository.findAll(Example.of(customer));
//        if (!NewShopAssert.listIsNotBlank(list)) {
            addSysCustomer(snsUserInfo, dto);
//        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse authByWx(SnsAuthDto dto) {
//        NewShopAssert.isNotBlank(dto.getCode(),"code不能为空");
//        NewShopAssert.isNotBlank(dto.getEncryptedData(),"密文不能为空");
//        NewShopAssert.isNotBlank(dto.getIv(),"vi不能为空");

        SnsCode2Session code2Session = SnsTool.jscode2session(dto.getCode());
        SnsUserInfos userInfos = SnsTool.decodeUserInfo(dto.getEncryptedData(), dto.getIv(), code2Session.getSessionKey());
        //用unionid去获取用户信息，没有就注册，有就登录
        SnsUserInfo userInfo=new SnsUserInfo();
        userInfo.setHeadimgurl(userInfos.getAvatarUrl());
        userInfo.setNickname(userInfos.getNickName());
        userInfo.setUnionid(userInfos.getUnionId());
        userInfo.setOpenid(userInfos.getOpenId());
        AuthByH5Dto authByH5Dto=new AuthByH5Dto();
//        authByH5Dto.setAgentId(dto.getAgentId());
        authByH5Dto.setCustomerId(dto.getCustomerId());
        SysCustomer customer = registerOrUpdate(userInfo, authByH5Dto);
        return ServerResponse.successWithData(getToken(customer));
    }


    @Override
    public ServerResponse bindTel(BindTelDto dto) {
        //校验验证码 手机号,验证码
        smsService.checkCode(dto.getTel(),dto.getSmsCode());

        SysCustomer customer = sysCustomerMapper.findByTel(dto.getTel());
        if (customer!=null){
            //存在则修改
            if (StringUtils.isEmpty(customer.getLogo())  && StringUtils.isBlank(dto.getHeadImg())){
                sysCustomerMapper.updateCustomerPhoto(customer.getId(),dto.getHeadImg());
            }
            if (StringUtils.isEmpty(customer.getUserName())&& StringUtils.isBlank(dto.getNickName())){
                sysCustomerMapper.updateCustomerNickName(customer.getId(),dto.getNickName());
            }
            sysCustomerMapper.updateOpenId(customer.getId(),dto.getOpenId());
        }else{
            //不存在则插入
            customer = new SysCustomer();
            customer.setOpenId(dto.getOpenId());
            customer.setTel(dto.getTel());
            if(StringUtils.isBlank(dto.getHeadImg())){
                customer.setLogo(dto.getHeadImg());
            }
            if(StringUtils.isBlank(dto.getNickName())){
                customer.setLogo(dto.getNickName());
            }
            customerRepository.saveAndFlush(customer);
        }

        return ServerResponse.successWithData(getToken(customer));
    }

    private SysCustomer registerOrUpdate(SnsUserInfo snsUserInfo, AuthByH5Dto dto) {
        SysCustomer customer = new SysCustomer();

        customer.setOpenId(snsUserInfo.getOpenid());

        List<SysCustomer> list = this.customerRepository.findAll(Example.of(customer));
        if (!NewShopAssert.listIsNotBlank(list)) {
            // 注册
            customer = addSysCustomer(snsUserInfo, dto);
            return customer;
        } else {
            return list.get(0);
        }
    }

    private SnsUserInfo getSnsUserInfoByH5(WxInfoDto wxInfoDto) throws Exception {
        StringBuffer sb = new StringBuffer();
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        url = url.replace("ACCESS_TOKEN", wxInfoDto.getAccessToken());
        url = url.replace("OPENID", wxInfoDto.getOpenid());
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url1 = new URL(url);
            urlConnection = (HttpURLConnection) url1.openConnection();
            inputStream = urlConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader in = new BufferedReader(inputStreamReader);
            String jsonUserStr = in.readLine().toString();
            SnsUserInfo userInfo = JsonUtil.toObject(jsonUserStr, SnsUserInfo.class);
            if (userInfo != null) {
                return userInfo;
            }
        } catch (Exception e) {
            log.error("获取用户信息失败" + e);
        } finally {
            inputStream.close();
            inputStreamReader.close();
            urlConnection.disconnect();
        }
        throw new BusinessException(new BaseResult(BaseResultEnum.OBTAIN_USER_ERROR));
    }


    private SnsUserInfo getSnsUserInfo(WxInfoDto wxInfoDto) {
        StringBuffer sb = new StringBuffer();
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        url = url.replace("ACCESS_TOKEN", wxInfoDto.getAccessToken());
        url = url.replace("OPENID", wxInfoDto.getOpenid());
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            SnsUserInfo userInfo = JsonUtil.toObject(EntityUtils.toString(response.getEntity()), SnsUserInfo.class);
            if (userInfo != null) {
                return userInfo;
            }
        } catch (Exception e) {
            log.error("获取用户信息失败" + e);
        }
        throw new BusinessException(new BaseResult(BaseResultEnum.OBTAIN_USER_ERROR));
    }

    private WxInfoDto getOpenId(AuthByH5Dto dto, String type) {
        StringBuffer sb = new StringBuffer();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        if (NewShopConstant.WX_TYPE_APP.equals(type)) {
            url = url.replace("APPID", wxConfiguration.getAppid());
            url = url.replace("CODE", dto.getCode());
            url = url.replace("SECRET", wxConfiguration.getAppsecret());
        }
        if (NewShopConstant.WX_TYPE_H5.equals(type)) {
            url = url.replace("APPID", wxConfiguration.getAppid_h5());
            url = url.replace("CODE", dto.getCode());
            url = url.replace("SECRET", wxConfiguration.getAppsecret_h5());
        }
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            Map<String, Object> map = JsonUtil.toObject(EntityUtils.toString(response.getEntity()), new HashMap<String, Object>().getClass());
            if (map != null && map.get("openid") != null && map.get("access_token") != null) {
                WxInfoDto wxInfoDto = new WxInfoDto();
                wxInfoDto.setAccessToken(map.get("access_token").toString());
                wxInfoDto.setOpenid(map.get("openid").toString());
                return wxInfoDto;
            }
        } catch (Exception e) {
            log.error("获取openid,access_token失败");
        }
        throw new BusinessException(new BaseResult(BaseResultEnum.GET_WX_OPENID_FAIL));
    }


    private SysCustomer addSysCustomer(SnsUserInfo snsUserInfo, AuthByH5Dto dto) {
        //添加用户表
        SysCustomer newcustomer = new SysCustomer();
        newcustomer.setOpenId(snsUserInfo.getOpenid());
        newcustomer.setUnionId(snsUserInfo.getUnionid());
        newcustomer.setLogo(snsUserInfo.getHeadimgurl());
        newcustomer.setUserName(snsUserInfo.getNickname());
        newcustomer.setCreateDate(new Date());
        newcustomer.setCreateBy(0);
        newcustomer.setUserStatus(1);
        newcustomer = this.customerRepository.save(newcustomer);
//        NewShopAssert.isNotBlank(newcustomer, "注册失败");

        //添加用户扩展表
        addSysCustomer(newcustomer, dto);
        return newcustomer;
    }

    private void addSysCustomer(SysCustomer newcustomer, AuthByH5Dto dto) {
        SysCustomer customer = new SysCustomer();
        //用户关联渠道ID
//        if (dto.getAgentId() != null) {
////            customer.setAgentId(dto.getAgentId());
//        } else {
////            TbAgent tbAgent = new TbAgent();
////            tbAgent.setType(DictConstant.DIRECT_BATTALION);
////            List<TbAgent> tbAgents = this.tbAgentRepository.findAll(Example.of(tbAgent));
////            if (NewShopAssert.listIsNotBlank(tbAgents)) {
////                customer.setAgentId(tbAgents.get(0).getId());
////            }
//        }
        if(dto.getCustomerId()!=null){
//            SysCustomer customerUp=this.customerRepository.findByCustomerId(dto.getCustomerId());
//            customer.setAgentId(customerUp.getAgentId());
        }
//        customer.setName(newcustomer.getUserName());
        customer.setLogo(newcustomer.getLogo());
//        customer.setCustomerId(newcustomer.getId());
//        customer.setLevel(DictConstant.LEVEL_VIP);
//        customer.setRandomCode(CodeUtils.getCodeInvicate(newcustomer.getId()));//TODO:随机码生成
//        customer.setVipCount(0);
//        customer.setScore(0);
        BigDecimal bigDecimal = new BigDecimal("0");
//        customer.setTodayMoney(bigDecimal);
//        customer.setYestodayMoney(bigDecimal);
//        customer.setMonthMoney(bigDecimal);
//        customer.setAllMoney(bigDecimal);
//        customer.setBalance(bigDecimal);
//        customer.setAudit(false);
        //生成二维码
        String qrUrl = getQrUrl(customer);
//        customer.setQrImgUrl(qrUrl);
        SysCustomer customerAdd = customerRepository.save(customer);
//        NewShopAssert.isNotBlank(customerAdd, "注册失败");
//        if (dto.getCustomerId() != null) {
//            //下面代码只做团队人数更新和团队升级
//            DistributionTaskDto taskDto = new DistributionTaskDto();
//            taskDto.setNeedCommission(false);
//            taskDto.setCurrentVipInfo(new VipInfo(customerAdd.getRandomCode(), customerAdd.getInviteCode()));
//            customerQueue.addQueue(taskDto);
//
//            //好友邀请表，团队统计表都添加一条信息
//            TbUserGoodFriend tbUserGoodFriend=new TbUserGoodFriend();
//            tbUserGoodFriend.setCreateTime(new Date());
//            tbUserGoodFriend.setUserid(dto.getCustomerId());
//            tbUserGoodFriend.setGoodFriendId(customerAdd.getCustomerId());
//            userGoodFriendRepository.save(tbUserGoodFriend);
//
//            Invitation invitation=new Invitation();
//            invitation.setCreateTime(new Date());
//            invitation.setIs_vip(true);
//            invitation.setSelfId(customerAdd.getCustomerId());
//            invitation.setParentId(dto.getCustomerId());
//            invitationRepository.save(invitation);
//        }

    }

    //unuse
    private String getQrUrl(SysCustomer customer) {
        try {
            //生成二维码，修改customerId用户表  id=课程id&userid=用户id&inviteCode=邀请码
//            String path = NewShopConstant.QR_CODE_PATH + customer.getCustomerId();
            String path = NewShopConstant.QR_CODE_PATH + customer.getId();
//            System.out.println(path);
//            String qrPath = QRCodeUtils2.generateQRcode(path, customer.getRandomCode(), null);
//            System.out.println(qrPath);
//            String pathUrl = videoTool.uploadVideo(qrPath, FileTypeEnum.picture.toString());
//            return pathUrl;
        } catch (Exception e) {
            log.error("用户生成二维码失败");
            return null;
        }
        return null;
    }

    public String getToken(SysCustomer customer) {
        String jwtToken = Jwts.builder().setId(String.valueOf(customer.getId())).setIssuer(Constant.ISS_URL)
                .setSubject(customer.getTel()).claim("roles", userExtendInfo.getRole(request.getRequestURI())).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, Constant.PUBLIC_KEY).compact();
        String tokenStr = TokenUtil.obtainToken(customer, jwtToken);//放入redis缓存

        return tokenStr;
    }

    public Map getTokenMap(SysCustomer customer) {
        String jwtToken = Jwts.builder().setId(String.valueOf(customer.getId())).setIssuer(Constant.ISS_URL)
                .setSubject(customer.getTel()).claim("roles", userExtendInfo.getRole(request.getRequestURI())).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, Constant.PUBLIC_KEY).compact();
        String tokenStr = TokenUtil.obtainToken(customer, jwtToken);//放入redis缓存

        SysCustomer one = customerRepository.findOne(customer.getId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", tokenStr);
        map.put("customer", customer);
        if(customer!=null){
//            map.put("qrImgUrl",customer.getQrImgUrl());
        }
        return map;
    }
}
