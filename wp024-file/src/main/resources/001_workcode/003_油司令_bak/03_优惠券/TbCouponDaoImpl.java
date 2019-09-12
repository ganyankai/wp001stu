package com.catt.oil.repository.dao.coupon.impl;

import com.catt.common.base.pojo.search.Page;
import com.catt.common.base.pojo.search.Pageable;
import com.catt.common.base.repository.dao.impl.BaseDaoImpl;
import com.catt.common.util.lang.StringUtil;
import com.catt.common.util.spring.SpringUtils;
import com.catt.oil.repository.dao.coupon.TbCouponDao;
import com.catt.oil.repository.entity.coupon.TbCoupon;
import com.catt.oil.repository.form.coupon.TbCouponForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 优惠券Dao接口实现
 *
 */
@Repository("tbCouponDaoImpl")
public class TbCouponDaoImpl extends BaseDaoImpl<TbCoupon, Long> implements TbCouponDao {
    public static void main(String[] args) {
        String[] paths = new String[2];
        paths[0] = "applicationContext.xml";
        paths[1] = "applicationContext-project.xml";

        //ok
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(paths);
//        CusInfoDao cusInfoDao =(CusInfoDao) applicationContext.getBean("cusInfoDaoImpl");
        TbCouponDaoImpl tbCouponDaoImpl = SpringUtils.getBean("tbCouponDaoImpl");
//        Page<Map> mapPage = tbCouponDaoImpl.getTbCouponList(null, new Pageable());
//        System.out.println(mapPage.getContent().size());
        //ok
        TbCoupon tbCoupon = tbCouponDaoImpl.find((long) 1);
        System.out.println(tbCoupon);
    }

    public Page<Map> getTbCouponList(TbCouponForm tbCouponForm,Pageable pageable){
        StringBuilder sql = new StringBuilder();
        sql.append(getQuerySql());
        sql.append(" where 1=1 ");

        if(StringUtils.isNotBlank(tbCouponForm.getCouponType())){
            sql.append(" and a.coupon_type = '"+tbCouponForm.getCouponType()+"' ");
        }
        if(StringUtils.isNotBlank(tbCouponForm.getState())){
            sql.append(" and a.state = '"+tbCouponForm.getState()+"' ");
        }
        System.out.println("sql:"+sql.toString());
        Map params = new HashMap<>();
        return this.findPageBySql(sql.toString(), params, pageable, Map.class);
    }

    /**
     * 优惠券领取记录
     * @param tbCouponForm
     * @param pageable
     * @return
     */
    @Override
    public Page<Map> receiveRecord(TbCouponForm tbCouponForm, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        sql.append(getReceiveSql());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        sql.append(" where 1=1 ");
        if(StringUtils.isNotBlank(tbCouponForm.getCouponType())){
            sql.append(" and a.coupon_type = '"+tbCouponForm.getCouponType()+"' ");
        }
        if(tbCouponForm.getStartTime()!=null){
            String startTime = dateFormat.format(tbCouponForm.getStartTime());
            sql.append(" and uc.get_time >= '"+startTime+"' ");
        }
        if(tbCouponForm.getEndTime()!=null){
            String endTime = dateFormat.format(tbCouponForm.getEndTime());
            sql.append(" and uc.get_time <= '"+endTime+"' ");
        }
        System.out.println("sql:"+sql.toString());
        Map params = new HashMap<>();
        return this.findPageBySql(sql.toString(), params, pageable, Map.class);
    }

    /**
     *  查询优惠券列表
     */
    private String getQuerySql() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");

        sql.append(" a.id as \"id\",");
        sql.append(" a.publish_qty as \"publishQty\", ");
        sql.append(" a.use_qty as \"useQty\", ");
        sql.append(" a.remain_qty as \"remainQty\", ");
        sql.append(" a.coupon_value as \"couponValue\", ");

        sql.append(" a.coupon_condition as \"couponCondition\", ");
        sql.append(" a.remark as \"remark\", ");
        sql.append(" a.activation_code as \"activationCode\", ");
        sql.append(" a.coupon_type as \"couponType\", ");
        sql.append(" a.coupon_source as \"couponSource\", ");

        sql.append(" a.state as \"state\", ");
        sql.append(" a.create_by as \"createBy\", ");
        sql.append(" a.start_date as \"startDate\", ");
        sql.append(" a.expiration_date as \"expirationDate\", ");
        sql.append(" a.create_date as \"createDate\" ");

        sql.append(" from tb_coupon a");
        return sql.toString();
    }

    /**
     *  查询领取记录列表
     */
    private String getReceiveSql() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");

        sql.append(" a.id as \"id\",");
        sql.append(" a.publish_qty as \"publishQty\", ");
        sql.append(" a.use_qty as \"useQty\", ");
        sql.append(" a.remain_qty as \"remainQty\", ");
        sql.append(" a.coupon_value as \"couponValue\", ");

        sql.append(" a.coupon_condition as \"couponCondition\", ");
        sql.append(" a.remark as \"remark\", ");
        sql.append(" a.activation_code as \"activationCode\", ");
        sql.append(" a.coupon_type as \"couponType\", ");
        sql.append(" a.coupon_source as \"couponSource\", ");

        sql.append(" a.state as \"state\", ");
        sql.append(" a.create_by as \"createBy\", ");
        sql.append(" a.start_date as \"startDate\", ");
        sql.append(" a.expiration_date as \"expirationDate\", ");
        sql.append(" a.create_date as \"createDate\", ");

        sql.append(" tci.S_NICK_NAME as \"nickName\", ");
        sql.append(" uc.get_time as \"getTime\", ");
        sql.append(" uc.used_time as \"usedTime\" ");

        sql.append(" from tb_coupon a,user_coupon uc, t_cus_info tci ");
        sql.append(" where a.id = uc.coupon_id and uc.customer_id = tci.i_id ");
        return sql.toString();
    }
}
