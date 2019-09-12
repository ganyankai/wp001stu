package com.zrytech.framework.newshop.service;

import com.zrytech.framework.base.entity.ServerResponse;
import com.zrytech.framework.newshop.entity.app.CustomerExtend;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CustomerExtendService {
    /**
     * 获取全部用户数据
     * @return
     */
    public ServerResponse getCustomerExtendList(Integer pageNum,Integer pageSize);

    /**
     * 获取指定客服用户数据
     * @param id
     * @return
     */
    public ServerResponse getCustomerSerList(@Param("id") Integer id,Integer pageNum,Integer pageSize);

    /**
     * 获取客服列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse getUserAgentList(Integer pageNum,Integer pageSize);

    /**
     *
     * @param userServiceId 客服id
     * @param idsList   用户id集合
     * @return
     */
    public ServerResponse setUserAgent(Integer userServiceId,List<Integer> idsList);
	
    /**
     *
     * @param label 标签名称
     * @param idsList 客户id集合
     * @return
     */
    public ServerResponse setLabel(String label,List<Integer> idsList,Integer userId);

    /**
     * 标签历史记录查询
     * @return
     */
    public ServerResponse getLabelRecord(Integer pageNum,Integer pageSize);

    /**
     * 标签历史记录删除
     * @return
     */
    public ServerResponse deleteLabelRecord(Integer id);


}
