package com.zrytech.framework.newshop.utils;


import com.zrytech.framework.app.constants.CustomConstants;
import com.zrytech.framework.app.constants.DictConstant;
import com.zrytech.framework.app.constants.MessageConstant;
import com.zrytech.framework.app.dto.search.TbAgentProductDtoSearch;
import com.zrytech.framework.app.entity.AgentProduct;
import com.zrytech.framework.app.entity.TbIndent;
import com.zrytech.framework.app.entity.TbIndentInfo;
import com.zrytech.framework.app.entity.TbShop;
import com.zrytech.framework.app.enums.MiniMessageTemp;
import com.zrytech.framework.app.mapper.TbIndentInfoMapper;
import com.zrytech.framework.app.service.MessageBaseUtil;
import com.zrytech.framework.base.cache.ICache;
import com.zrytech.framework.base.enums.DeviceType;
import com.zrytech.framework.base.util.JsonUtil;
import com.zrytech.framework.common.entity.SysCustomer;
import com.zrytech.framework.common.repository.CustomerRepository;
import com.zrytech.framework.common.util.DictUtil;
import com.zrytech.framework.common.util.DictionUtil;
import com.zrytech.framework.newshop.dto.MessageDto;
import com.zrytech.framework.newshop.entity.*;
import com.zrytech.framework.newshop.entity.app.CustomerExtend;
import com.zrytech.framework.newshop.frontservice.SysMessageService;
import com.zrytech.framework.newshop.mapper.DisCommissionLogMapper;
import com.zrytech.framework.newshop.mapper.ShopMapper;
import com.zrytech.framework.newshop.repository.*;
import com.zrytech.framework.newshop.utils.weixin.WxMiniUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.internet.NewsAddress;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
@Slf4j
public class SysMessageUtils implements MessageBaseUtil {

    @Autowired private  SysMessageService sysMessageService;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private ShopMapper shopMapper;
    @Autowired private TbAgentRepository tbAgentRepository;
    @Autowired private TbIndentInfoMapper tbIndentInfoMapper;
    @Autowired private TbCustomerBargainRepository tbCustomerBargainRepository;
    @Autowired private AgentProductRepository agentProductRepository;
    @Autowired private TbCustomerRecordRepository tbCustomerRecordRepository;
    @Autowired private ExtendRepository extendRepository;
    @Autowired private DisCommissionLogMapper disCommissionLogMapper;
    /**
     *
//     * @param tel   接收人手机号
//     * @param reveicerId    接收人id
//     * @param reveicerType  接收人类型   user_admin,user_agent,user_shop,sys_customer
//     * @param logicId       订单id
//     * @param messageType   消息类别    传递key
//     * @param title     消息标题 key
//     * @param content   消息内容 key
//     * @param strArr    需要替换占位符%s的参数数组，不需要传递null
//     * @param needSMS   是否需要发送短信 true为需要
     */
    public  void saveAndSend(MessageDto dto) {
        String messageType = DictionUtil.getValue(DictConstant.MESSAGE_TYPE, dto.getMessageKey());  //消息类型
        String title = DictionUtil.getValue(MessageConstant.MESSAGE_TITLE, dto.getTitleKey());      //标题
        String content = DictionUtil.getValue(MessageConstant.MESSAGE_CONTENT, dto.getContentKey());//内容
        if (NewShopAssert.listIsNotBlank(dto.getParams())) {
            List<String> list=dto.getParams();
            if(list!=null&&list.size()==3){
                content=String.format(content,list.get(0),list.get(1),list.get(2));
            }else {
                content = String.format(content, dto.getParams());
            }
            log.info("消息内容是:{}", content);
        }
        SysMessage sysMessage = new SysMessage();
        sysMessage.setTitle(title);
        sysMessage.setContent(content);
        sysMessage.setReveicerId(dto.getReveicerId());
        sysMessage.setReveicerType(dto.getReveicerType());
        sysMessage.setLogicId(dto.getLogicId());
        sysMessage.setMessageType(messageType);
        sysMessage.setSenderDate(new Date());
        sysMessage.setMarkRead(false);
        sysMessageService.save(sysMessage);
        //发送
        try {
            log.info("开始发送消息，推送的手机号："+dto.getTel()+"------yo");
            JpushUtil.jpush(dto.getTel(), title, content);
            log.info("结束推送消息");
        }catch (Exception e){
            log.error("极光推送异常");
        }
        if(dto.getNeedSMS()){
            //发送短信 todo

        }
    }

    @Override
    public void sendMessage(TbIndent tbIndent, String title, String content) {
        try {
            NewShopAssert.isNotBlank(tbIndent, "订单不能为空");
            NewShopAssert.isNotBlank(title, "标题不能为空");
            NewShopAssert.isNotBlank(content, "内容不能为空");
            SysCustomer sysCustomer = this.customerRepository.findOne(tbIndent.getCustomerId());
            NewShopAssert.isNotBlank(sysCustomer, "订单用户不存在");
            if (!DeviceType.MiNi.getMsg().equals(sysCustomer.getExtend())) {
                sendMessageJG(tbIndent, title, content,sysCustomer);
            }else {
                String formId=getFormId(sysCustomer.getId());
                sendMessageWxMn(formId,tbIndent, title,sysCustomer);
            }
        }catch (Exception e){
            log.error("发送消息失败");
            log.error("后台异常",e);
        }
    }


    /**
     * 发送消息
     *
     * @param tbIndent
     */
    public void sendMessageJG(TbIndent tbIndent, String title, String content,SysCustomer sysCustomer) {
            if (MessageConstant.ORDER_SEND_NOTICE.equals(title)) {
                //下单通知，需要发送消息给门店，没有门店发送渠道，，在发一个支付成功的消息给客户
                //sendOrderSuccess(tbIndent, title, content);
                //发送客户，购买成功
                String params = getProductName(tbIndent);
                sendGeneralMessage(tbIndent.getId(), sysCustomer, title, MessageConstant.MESSAGE_ORDER_SEND_CUSTOMER, params);
            }
            if (MessageConstant.GROUP_ORDER_NOTICE.equals(title)) {     //团购成功，但是人手还没有够===》您参与团购的商品%s下单成功
                String params = getProductName(tbIndent);
                sendGeneralMessage(tbIndent.getId(), sysCustomer, title, content, params);
            }
            if (MessageConstant.GROUP_SUCCESS_NOTICE.equals(title)) {   //团购成功，人手够了==》您参与的团购商品%s已生效，卖家正在安排安装，请耐心等待并保持电话畅通
                String params = getProductName(tbIndent);
                sendGeneralMessage(tbIndent.getId(), sysCustomer, title, content, params);
            }
            if (MessageConstant.SECKILL_ORDER_NOTICE.equals(title)) {   //秒杀通知，==》您已成功抢购到商品%s
                String params = getProductName(tbIndent);
                sendGeneralMessage(tbIndent.getId(), sysCustomer, title, content, params);
            }
            if (MessageConstant.CUTPRICE_SUCCESS_NOTICE.equals(title)) {   //砍价通知，==》恭喜您已成功砍到了商品%s
                String params = getProductName(tbIndent);
                sendGeneralMessage(tbIndent.getId(), sysCustomer, title, content, params);
            }
            if (MessageConstant.WITHDRAW_TO_ACCOUNT_NOTICE.equals(title)) {   //收益到账通知
                log.info("app收益到账通知开始");
                List<DisCommissionLog> dclList=this.disCommissionLogMapper.getMyCommission(tbIndent);
                for (DisCommissionLog disCommissionLog : dclList) {
                    SysCustomer sysCustomerUp = this.customerRepository.findOne(disCommissionLog.getCreateId());
                    NewShopAssert.isNotBlank(sysCustomerUp, "分销账户用户不存在");
                    log.info("app收益到账通知===》》用户："+sysCustomerUp.getId()+"---"+sysCustomerUp.getTel());
                    sendWithDrawToAccountMessage(disCommissionLog.getId(),sysCustomerUp,title,content,disCommissionLog);
                }
                log.info("app收益到账通知结束");
            }
    }

    public void sendMessage(Integer logicId, String title, String content) {
        NewShopAssert.isNotBlank(logicId,"订单不能为空");
        NewShopAssert.isNotBlank(title,"标题不能为空");
        NewShopAssert.isNotBlank(content,"内容不能为空");
        if(MessageConstant.START_CUTPRICE_NOTICE.equals(title)){     //发起砍价，您发起了%s商品的砍价，努力加油哦
            TbCustomerBargain tbCustomerBargain=this.tbCustomerBargainRepository.findOne(logicId);
            NewShopAssert.isNotBlank(tbCustomerBargain,"砍价实体不存在");
            AgentProduct agentProduct=this.agentProductRepository.findOne(tbCustomerBargain.getAgentProductId());
            NewShopAssert.isNotBlank(agentProduct,"砍价的渠道商品不存在");
            SysCustomer sysCustomer=this.customerRepository.findOne(tbCustomerBargain.getCreateBy());
            NewShopAssert.isNotBlank(sysCustomer,"砍价用户不能存在");
            sendGeneralMessage(logicId,sysCustomer,title,content,agentProduct.getSkuName());
        }
        if(MessageConstant.CUTPRICE_HELP_NOTICE.equals(title)) {     //帮助砍价，==》您的好友帮你砍了%s元!
            TbCustomerRecord tbCustomerRecord = tbCustomerRecordRepository.findOne(logicId);
            NewShopAssert.isNotBlank(tbCustomerRecord,"砍价记录实体不存在");
            TbCustomerBargain tbCustomerBargain=this.tbCustomerBargainRepository.findOne(tbCustomerRecord.getCustomerBargainId());
            NewShopAssert.isNotBlank(tbCustomerBargain,"砍价实体不存在");
            SysCustomer sysCustomer=this.customerRepository.findOne(tbCustomerBargain.getCreateBy());
            NewShopAssert.isNotBlank(sysCustomer,"发起砍价用户不能存在");
            sendGeneralMessage(logicId,sysCustomer,title,content,tbCustomerRecord.getCutPrice()+"");
        }
    }



    private void sendGeneralMessage(Integer logicId,SysCustomer sysCustomer,String title, String content,String productNmae) {
        MessageDto dto=new MessageDto();
        List<String> list=new ArrayList<String>();
        list.add(productNmae);
        dto.setParams(list);
        dto.setNeedSMS(false);
        dto.setTel(sysCustomer.getTel());
        dto.setReveicerType(MessageConstant.REVEICER_USER_APP);
        dto.setTitleKey(title);
        dto.setContentKey(content);
        dto.setLogicId(logicId);
        dto.setMessageKey(DictConstant.MESSAGE_TYPE_INDENT);
        dto.setReveicerId(sysCustomer.getId());
        this.saveAndSend(dto);
    }

    private void sendWithDrawToAccountMessage(Integer logicId,SysCustomer sysCustomer,String title, String content,DisCommissionLog disCommissionLog) {
        MessageDto dto=new MessageDto();
        List<String> list=new ArrayList<String>();
        list.add(disCommissionLog.getOrderNo());
        list.add(disCommissionLog.getMoney().toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        list.add(df.format(disCommissionLog.getCreateTime()));
        dto.setParams(list);
        dto.setNeedSMS(false);
        dto.setTel(sysCustomer.getTel());
        dto.setReveicerType(MessageConstant.REVEICER_USER_APP);
        dto.setTitleKey(title);
        dto.setContentKey(content);
        dto.setLogicId(logicId);
        dto.setMessageKey(DictConstant.MESSAGE_TYPE_SYSTEM);
        dto.setReveicerId(sysCustomer.getId());
        this.saveAndSend(dto);
    }

//    private void sendOrderSuccess(TbIndent tbIndent, String title, String content) {
//        MessageDto dto=new MessageDto();
//        //发送门店或者渠道
//        Integer reveicerId=0;
//        String reveicerType=null;
//        String tel=null;
//        List<String> list=new ArrayList<String>();
//        SysCustomer sysCustomer = customerRepository.findOne(tbIndent.getCustomerId());
//        NewShopAssert.isNotBlank(sysCustomer,"用户不存在");
//        list.add(sysCustomer.getUserName());
//        if(tbIndent.getShopId()!=0){
//            reveicerType=MessageConstant.REVEICER_USER_SHOP;
//            Shop tbShop=this.shopMapper.details(tbIndent.getShopId());
//            NewShopAssert.isNotBlank(tbShop,"门店不存在");
//            tel=tbShop.getContactTel();
//            reveicerId=tbShop.getUserId();
//        }else{
//            reveicerType=MessageConstant.REVEICER_USER_AGENT;
//            TbAgent tbAgent= tbAgentRepository.getOne(tbIndent.getAgentId());
//            NewShopAssert.isNotBlank(tbAgent,"渠道不存在");
//            tel=tbAgent.getContactTel();
//            reveicerId=tbAgent.getUserId();
//        }
//        list.add(getProductName(tbIndent));
//        dto.setParams(list);
//        dto.setNeedSMS(false);
//        dto.setTel(tel);
//        dto.setReveicerType(reveicerType);
//        dto.setTitleKey(title);
//        dto.setContentKey(content);
//        dto.setLogicId(tbIndent.getId());
//        dto.setMessageKey(DictConstant.MESSAGE_TYPE_INDENT);
//        dto.setReveicerId(reveicerId);
//        this.saveAndSend(dto);
//    }

    private String getProductName(TbIndent tbIndent) {
        StringBuffer sb=new StringBuffer();
        List<TbIndentInfo> tbIndentInfos = tbIndentInfoMapper.getDetailByOrderId(tbIndent.getId());
        NewShopAssert.listIsNotBlank(tbIndentInfos,"订单详情不存在");
        for (TbIndentInfo tbIndentInfo : tbIndentInfos) {
            sb.append(tbIndentInfo.getProductName());
            sb.append(",");
        }
        return sb.toString().substring(0,sb.length()-1);
    }


    //=================================================================================================
    //小程序推送

    @Autowired
    private ICache cache;
    public String getFormId(Integer customerId) {
        String jsonstr = cache.get(CustomConstants.DELAYED_FORMID_CUSTOMER_PREIX+customerId);
        if(StringUtils.isEmpty(jsonstr)){
            return null;
        }
        LinkedList<Map<String,Object>> linkedList= JsonUtil.toObject(jsonstr,new LinkedList<Map<String,Object>>().getClass());
        if(linkedList==null||linkedList.size()==0){
            return null;
        }
        //删除过期的formID；
        for (Iterator<Map<String,Object>> dd = linkedList.iterator(); dd.hasNext();) {
            Map<String,Object> dto = dd.next();
            if(new Date().getTime()/1000>Long.parseLong(dto.get("deadTime")+"")){
                dd.remove();
            }
            if(StringUtils.isEmpty(dto.get("formId")+"")){
                dd.remove();
            }
        }
        if(linkedList==null||linkedList.size()==0){
            return null;
        }
        String formId=linkedList.getFirst().get("formId").toString();
        linkedList.removeFirst();
        cache.set(CustomConstants.DELAYED_FORMID_CUSTOMER_PREIX+customerId,linkedList);
        return formId;
    }



    @Data
    public static class TemplateData {
        private String value;
        public TemplateData(String value) {
            this.value = value;
        }
        public TemplateData() {
        }
    }
    public void doSendMessageWxMn(String openId,String formId,String templateId,Map<String,TemplateData> mapdata) {
        NewShopAssert.isNotBlank(openId,null);
        NewShopAssert.isNotBlank(formId,null);
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=");
        url.append(WxMiniUtils.getAccessToken());//设置accesstoken
        JSONObject json = new JSONObject();
        json.put("touser", openId);//设置openid
        json.put("page", "pages/index/main");//设置openid
        json.put("template_id", templateId);//设置模板id
        json.put("form_id",formId);//设置formid
        json.put("data", JsonUtil.toJson(mapdata));//设置模板消息内容
        //   json.put("page", page);//跳转微信小程序页面路径（非必须）
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            HttpClient client = HttpClientBuilder.create().build();//构建一个Client
            HttpPost post = new HttpPost(url.toString());//构建一个POST请求
            StringEntity s = new StringEntity(json.toString(), "UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json; charset=UTF-8");
            post.setEntity(s);//设置编码，不然模板内容会乱码
            HttpResponse response = client.execute(post);//提交POST请求
            map = JsonUtil.toObject(EntityUtils.toString(response.getEntity()),map.getClass());
            if(map != null && "ok".equals(map.get("errmsg"))) {
                System.out.println("模版消息发送成功");
            }else {
                StringBuilder sb = new StringBuilder("模版消息发送失败\n");
                sb.append(map.toString());
                throw new Exception(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendMessageWxMn(String formId, TbIndent tbIndent, String title,SysCustomer sysCustomer) {
        NewShopAssert.isNotBlank(sysCustomer.getOpenId(),null);
      //  NewShopAssert.isNotBlank(formId,null);
        if (MessageConstant.ORDER_SEND_NOTICE.equals(title)) {
            Map<String,TemplateData> mapdata = new HashMap<>();
            mapdata.put("keyword1",new TemplateData(tbIndent.getOrderNo()));
            mapdata.put("keyword2",new TemplateData(tbIndent.getRealPay().toString()));
            mapdata.put("keyword3",new TemplateData(tbIndent.getIndentStatusCN()));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mapdata.put("keyword4",new TemplateData(df.format(tbIndent.getCreateDate())));
            doSendMessageWxMn(sysCustomer.getOpenId(),formId,MiniMessageTemp.ORDER_SUCCESS.getName(),mapdata);
        }
        if (MessageConstant.CUTPRICE_SUCCESS_NOTICE.equals(title)) {   //砍价通知
            String productName=getProductName(tbIndent);
            Map<String,TemplateData> mapdata = new HashMap<>();
            mapdata.put("keyword1",new TemplateData(productName));
            mapdata.put("keyword2",new TemplateData(sysCustomer.getUserName()));
            mapdata.put("keyword3",new TemplateData(tbIndent.getRealPay().toString()));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mapdata.put("keyword4",new TemplateData(df.format(tbIndent.getCreateDate())));
            doSendMessageWxMn(sysCustomer.getOpenId(),formId,MiniMessageTemp.BARGAIN_SUCCESS.getName(),mapdata);
        }
        if (MessageConstant.INVIST_FRIEDN_REGISTER.equals(title)) {   //好友邀
            CustomerExtend customerExtend = this.extendRepository.findByCustomerId(sysCustomer.getId());
            NewShopAssert.isNotBlank(customerExtend,"用户扩展不存在");
            CustomerExtend upCutomerExtent = this.extendRepository.findByRandomCode(customerExtend.getInviteCode());
            NewShopAssert.isNotBlank(upCutomerExtent,null);
            Map<String,TemplateData> mapdata = new HashMap<>();
            mapdata.put("keyword1",new TemplateData(upCutomerExtent.getName()));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mapdata.put("keyword2",new TemplateData(df.format(sysCustomer.getCreateDate())));
            doSendMessageWxMn(sysCustomer.getOpenId(),formId,MiniMessageTemp.BARGAIN_SUCCESS.getName(),mapdata);
        }
        if (MessageConstant.WITHDRAW_TO_ACCOUNT_NOTICE.equals(title)) {   //收益到账通知
            log.info("wx收益到账通知开始");
            List<DisCommissionLog> dclList=this.disCommissionLogMapper.getMyCommission(tbIndent);
            for (DisCommissionLog disCommissionLog : dclList) {
                SysCustomer sysCustomerUp = this.customerRepository.findOne(disCommissionLog.getCreateId());
                NewShopAssert.isNotBlank(sysCustomerUp, "分销账户用户不存在");
                formId=getFormId(sysCustomerUp.getId());
                Map<String,TemplateData> mapdata = new HashMap<>();
                mapdata.put("keyword1",new TemplateData(tbIndent.getOrderNo()));
                mapdata.put("keyword2",new TemplateData(disCommissionLog.getMoney().toString()));
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                mapdata.put("keyword3",new TemplateData(df.format(disCommissionLog.getCreateTime())));
                log.info("wx收益到账通知===》》用户："+sysCustomerUp.getId()+"---"+sysCustomerUp.getTel());
                doSendMessageWxMn(sysCustomerUp.getOpenId(),formId,MiniMessageTemp.DIVIDENDS_ACCOUNT.getName(),mapdata);
            }
            log.info("wx收益到账通知结束");
        }
    }
}
