package com.zrytech.framework.newshop.mapper;

import com.zrytech.framework.common.entity.User;
import com.zrytech.framework.newshop.entity.app.CustomerExtend;
import com.zrytech.framework.newshop.entity.app.LabelRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CustomerExtendMapper {
//    @Select("select * from customer_extend ")
    public List<CustomerExtend> getCustomerExtendList();
	
//    @Select("select * from customer_extend where user_service_id = #{id}")
    public List<CustomerExtend> getCustomerSerList(@Param("id") Integer id);

    //根据id获得用户客服名称
    @Select("select user_name from sys_user where id = #{id}")
    public String getSerName(@Param("id")Integer id);

    //查询客服列表
    @Select("select id,user_name as userName,user_account as userAccount,tel from sys_user where user_type = 'user_agent' and user_status = 1")
    public List<User> getUserAgentList();
	
    //查询指定id客服是否存在
    @Select("select * from sys_user where id = #{id} and user_status = 1 and user_type = 'user_agent'")
    public User getUserAgent(@Param("id")Integer id);

    //设置客服-管理员
//    update customer_extend set user_service_id = 23 where id in (910,912)
    @Update("update customer_extend set user_service_id = #{userServiceId} where id in ${idsStr}")
    public void setUserAgent(@Param("userServiceId")Integer userServiceId,@Param("idsStr")String idsStr);
	
    //设置标签-客服-管理员
    @Update("update customer_extend set label = #{label} where id in ${idsStr}")
    public void setLabel(@Param("label")String label,@Param("idsStr")String idsStr);

    //标签历史记录查询
    @Select("select lr.id as id,lr.label_name labelName,lr.create_by createBy,lr.create_time createTime,lr.customer_id customerId,\n" +
            "su.user_name as userServiceName,ce.name as customerName \n" +
            "from label_record lr,sys_user su,customer_extend ce \n" +
            "where lr.create_by = su.id and lr.customer_id = ce.id ")
    public List<LabelRecord> getLabelRecord();
	
    //标签历史记录删除
    @Delete("delete from label_record where id =#{id} ")
    public void deleteLabelRecord(@Param("id")Integer id);
	
}
