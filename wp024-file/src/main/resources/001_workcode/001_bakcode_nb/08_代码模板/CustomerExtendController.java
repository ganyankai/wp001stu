package com.zrytech.framework.newshop.controller.backend;


import com.zrytech.framework.app.constants.DictConstant;
import com.zrytech.framework.base.entity.Page;
import com.zrytech.framework.base.entity.RequestParams;
import com.zrytech.framework.base.entity.ServerResponse;
import com.zrytech.framework.base.exception.BusinessException;
import com.zrytech.framework.base.util.RequestUtil;
import com.zrytech.framework.common.entity.User;
import com.zrytech.framework.newshop.dto.ActivitDetailDto;
import com.zrytech.framework.newshop.dto.ActivitDto;
import com.zrytech.framework.newshop.dto.appDTO.CustomerExtendDto;
import com.zrytech.framework.newshop.dto.appDTO.CustomerExtendSetDto;
import com.zrytech.framework.newshop.dto.appDTO.CustomerExtendUpdateDto;
import com.zrytech.framework.newshop.dto.appDTO.LabelRecordCommonDto;
import com.zrytech.framework.newshop.entity.app.CustomerExtend;
import com.zrytech.framework.newshop.service.ActiService;
import com.zrytech.framework.newshop.service.CustomerExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customerExtend")
public class CustomerExtendController {

   

    /**
     * 查询用户列表
     * @param requestParams
     * @param result
     * @return
     */
    @PostMapping("/getList")
    public ServerResponse page(@RequestBody RequestParams<CustomerExtendDto> requestParams,BindingResult result) {
        Page page = requestParams.getPage();
        if (page == null) {
            page = new Page(1, 10);
        }
        Integer pageNum = page.getPageNum();
        Integer pageSize = page.getPageSize();
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        User user = RequestUtil.getCurrentUser(User.class);
        if (user.getUserType().equalsIgnoreCase(DictConstant.USER_ADMIN)){
            return customerExtendService.getCustomerExtendList(pageNum,pageSize);
        }else if(user.getUserType().equalsIgnoreCase(DictConstant.USER_AGENT)){
            return customerExtendService.getCustomerSerList(user.getId(),pageNum,pageSize);
        }
        throw new BusinessException(112, "无访问权限");
    }

    /**
     * 查询客服列表
     * @param requestParams
     * @param result
     * @return
     */
    @PostMapping("/getUserAgentList")
    public ServerResponse userAgentPage(@RequestBody RequestParams<CustomerExtendDto> requestParams,BindingResult result) {
        Page page = requestParams.getPage();
        if (page == null) {
            page = new Page(1, 10);
        }
        Integer pageNum = page.getPageNum();
        Integer pageSize = page.getPageSize();
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        User user = RequestUtil.getCurrentUser(User.class);
        if (user.getUserType().equalsIgnoreCase(DictConstant.USER_ADMIN)){
            ServerResponse res = customerExtendService.getUserAgentList(pageNum, pageSize);
            return res;
        }
        throw new BusinessException(112, "无访问权限");
    }

    /**
     * 设置客服
     * @param requestParams
     * @return
     */
    @Valid
    @PostMapping("/setUserAgent")
    public ServerResponse setUserAgent(@RequestBody @Valid RequestParams<CustomerExtendUpdateDto> requestParams,BindingResult result){
        User user = RequestUtil.getCurrentUser(User.class);
        if (user.getUserType().equalsIgnoreCase(DictConstant.USER_ADMIN)){
            Integer userServiceId = requestParams.getParams().getUserServiceId();
            List<Integer> idsList = requestParams.getParams().getIdsList();
            customerExtendService.setUserAgent(userServiceId,idsList);
            return ServerResponse.success();
        }
        throw new BusinessException(112, "无访问权限");
    }

    /**
     * 设置标签
     * @param requestParams
     * @return
     */
    @Valid
    @PostMapping("/setLabel")
    public ServerResponse setLabel(@RequestBody @Valid RequestParams<CustomerExtendSetDto> requestParams,BindingResult result){
        User user = RequestUtil.getCurrentUser(User.class);
        if (user.getUserType().equalsIgnoreCase(DictConstant.USER_ADMIN) || user.getUserType().equalsIgnoreCase(DictConstant.USER_AGENT)){
            String label = requestParams.getParams().getLabel();
            List<Integer> idsList = requestParams.getParams().getIdsList();

            customerExtendService.setLabel(label,idsList,user.getId());
            return ServerResponse.success();
        }
        throw new BusinessException(112, "无访问权限");
    }


    /**
     * 查询标签历史表
     * @param requestParams
     * @param result
     * @return
     */
    @PostMapping("/getLabelRecord")
    public ServerResponse getLabelRecord(@RequestBody RequestParams<CustomerExtendDto> requestParams,BindingResult result) {
        Page page = requestParams.getPage();
        if (page == null) {
            page = new Page(1, 10);
        }
        Integer pageNum = page.getPageNum();
        Integer pageSize = page.getPageSize();
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        User user = RequestUtil.getCurrentUser(User.class);
        if (user.getUserType().equalsIgnoreCase(DictConstant.USER_ADMIN)){
            return customerExtendService.getLabelRecord(pageNum,pageSize);
        }
        throw new BusinessException(112, "无访问权限");
    }

    /**
     * 删除标签历史表
     * @param requestParams
     * @param result
     * @return
     */
    @Valid
    @PostMapping("/deleteLabelRecord")
    public ServerResponse deleteLabelRecord(@RequestBody @Valid RequestParams<LabelRecordCommonDto> requestParams, BindingResult result) {
        User user = RequestUtil.getCurrentUser(User.class);
        if (user.getUserType().equalsIgnoreCase(DictConstant.USER_ADMIN)){
            return customerExtendService.deleteLabelRecord(requestParams.getParams().getId());
        }
        throw new BusinessException(112, "无访问权限");
    }
}
