package com.zrytech.framework.newshop.service.Impl;

import com.github.pagehelper.PageHelper;
import com.zrytech.framework.base.entity.PageData;
import com.zrytech.framework.base.entity.ServerResponse;
import com.zrytech.framework.base.exception.BusinessException;
import com.zrytech.framework.common.entity.User;
import com.zrytech.framework.newshop.entity.app.CustomerExtend;
import com.zrytech.framework.newshop.entity.app.LabelRecord;
import com.zrytech.framework.newshop.mapper.CustomerExtendMapper;
import com.zrytech.framework.newshop.repository.LabelRecordRepository;
import com.zrytech.framework.newshop.service.CustomerExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerExtendServiceImpl implements CustomerExtendService {
    @Autowired
    private CustomerExtendMapper customerExtendMapper;

    @Autowired
    private LabelRecordRepository labelRecordRepository;
    /**
     * 获取全部用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse getCustomerExtendList(Integer pageNum,Integer pageSize) {

        com.github.pagehelper.Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<CustomerExtend> list = customerExtendMapper.getCustomerExtendList();

        setUserSerName(list);
		
        PageData<CustomerExtend> pageData = new PageData<>(page.getPageSize(), page.getPageNum(), page.getTotal(), list);
        return ServerResponse.successWithData(pageData);
    }


    /**
     * 获取指定客服对应的用户列表
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse getCustomerSerList(Integer id,Integer pageNum,Integer pageSize) {
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<CustomerExtend> list = customerExtendMapper.getCustomerSerList(id);

        setUserSerName(list);

        PageData<CustomerExtend> pageData = new PageData<>(page.getPageSize(), page.getPageNum(), page.getTotal(), list);
        return ServerResponse.successWithData(pageData);
    }

    @Override
    public ServerResponse getUserAgentList(Integer pageNum,Integer pageSize) {
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<User> userAgentList = customerExtendMapper.getUserAgentList();
        PageData<User> pageData = new PageData<>(page.getPageSize(), page.getPageNum(), page.getTotal(), userAgentList);
        return ServerResponse.successWithData(pageData);
    }

    @Transactional
    @Override
    public ServerResponse setUserAgent(Integer userServiceId, List<Integer> idsList) {
        User user = customerExtendMapper.getUserAgent(userServiceId);
        if(user==null){
            throw new BusinessException(112, "当前客服不存在或状态异常");
        }

        String idsStr = listToStr(idsList);
        customerExtendMapper.setUserAgent(userServiceId,idsStr);
        return ServerResponse.success();
    }

    @Override
    @Transactional
    public ServerResponse setLabel(String label, List<Integer> idsList,Integer userId) {
        customerExtendMapper.setLabel(label,listToStr(idsList));
        //存入标签历史记录表todo
//        LabelRecord labelRecord
        List<LabelRecord> list = new ArrayList<>();
        for(Integer id:idsList){
            LabelRecord labelRecord = new LabelRecord(label,userId,id,new Date());
            list.add(labelRecord);
        }
        labelRecordRepository.save(list);
        return ServerResponse.success();
    }

    public ServerResponse getLabelRecord(Integer pageNum,Integer pageSize){
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<LabelRecord> list = customerExtendMapper.getLabelRecord();

        PageData<LabelRecord> pageData = new PageData<>(page.getPageSize(), page.getPageNum(), page.getTotal(), list);
        return ServerResponse.successWithData(pageData);
    }

    @Transactional
    public ServerResponse deleteLabelRecord(Integer id){
        customerExtendMapper.deleteLabelRecord(id);
        return ServerResponse.success();
    }

    private void setUserSerName(List<CustomerExtend> list) {
        if (list!=null && list.size()>0){
            for (CustomerExtend customerExtend:list) {
                if(customerExtend.getUserServiceId()!=null){
                    customerExtend.setUserServiceName(customerExtendMapper.getSerName(customerExtend.getUserServiceId()));
                }
            }
        }
    }

    private String listToStr(List<Integer> list) {
        String idsStr = "(";
        for (int i = 0; i < list.size() ; i++) {
            if (i==list.size()-1){
                idsStr = idsStr + list.get(i);
            }else{
                idsStr = idsStr + list.get(i) + ",";
            }
        }
        idsStr = idsStr +")";
        return idsStr;
    }

}
