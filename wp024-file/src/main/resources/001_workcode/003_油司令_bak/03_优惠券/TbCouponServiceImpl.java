package com.catt.oil.service.impl.coupon;

import com.catt.common.base.pojo.search.Page;
import com.catt.common.base.pojo.search.Pageable;
import com.catt.common.base.service.impl.BaseServiceImpl;
import com.catt.common.util.spring.SpringUtils;
import com.catt.oil.repository.dao.coupon.TbCouponDao;
import com.catt.oil.repository.entity.coupon.TbCoupon;
import com.catt.oil.repository.form.coupon.TbCouponForm;
import com.catt.oil.service.coupon.TbCouponService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("tbCouponServiceImpl")
public class TbCouponServiceImpl extends BaseServiceImpl<TbCoupon,Long>  implements TbCouponService{
    @Resource(name = "tbCouponDaoImpl")
    private TbCouponDao tbCouponDao;


    @Override
    public TbCoupon find(Long id){
        return tbCouponDao.find(id);
    }

    @Override
    public Page<Map> receiveRecord(TbCouponForm tbCouponForm,Pageable pageable) {
       return tbCouponDao.receiveRecord(tbCouponForm,pageable);
    }

    /**
     * 优惠券列表
     * @param tbCouponForm
     * @param pageable
     * @return
     */
    @Override
    public Page<Map> tbCouponList(TbCouponForm tbCouponForm, Pageable pageable) {

        return tbCouponDao.getTbCouponList(tbCouponForm,pageable);
    }


    /**
     * 修改优惠券
     * @param tbCouponForm
     */
    @Override
    public void updateTbCoupon(TbCouponForm tbCouponForm) {
        String sql = "UPDATE tb_coupon a set a.id = a.id ";
        Map<String, Object> params = new HashMap<>();
        params.put("id", tbCouponForm.getId());
        params.put("couponType",tbCouponForm.getCouponType());
        params.put("publishQty", tbCouponForm.getPublishQty());
        params.put("couponValue", tbCouponForm.getCouponValue());
        params.put("couponCondition", tbCouponForm.getCouponCondition());

        params.put("startDate", tbCouponForm.getStartDate());
        params.put("expirationDate", tbCouponForm.getExpirationDate());
        params.put("state", tbCouponForm.getState());

        if(params.get("couponType")!=null && StringUtils.isNotBlank((String)params.get("couponType").toString())){
            sql += " ,a.coupon_type  = :couponType ";
        }
        if(params.get("publishQty")!=null && StringUtils.isNotBlank((String)params.get("publishQty").toString())){
            sql += " ,a.publish_qty  = :publishQty ";
        }
        if(params.get("couponValue")!=null && StringUtils.isNotBlank((String)params.get("couponValue").toString())){
            sql += " ,a.coupon_value  = :couponValue ";
        }
        if(params.get("couponCondition")!=null && StringUtils.isNotBlank((String)params.get("couponCondition").toString())){
            sql += " ,a.coupon_condition  = :couponCondition ";
        }
        if(params.get("startDate")!=null && StringUtils.isNotBlank((String)params.get("startDate").toString())){
            sql += " ,a.start_date  = :startDate ";
        }

        if(params.get("expirationDate")!=null && StringUtils.isNotBlank((String)params.get("expirationDate").toString())){
            sql += " ,a.expiration_date  = :expirationDate ";
        }
        if(params.get("state")!=null && StringUtils.isNotBlank(params.get("state").toString())){
            sql += " ,a.state = :state ";
        }

        if(params.get("id")!=null && StringUtils.isNotBlank(params.get("id").toString())){
            sql += " where a.id = :id";
        }

        System.out.println("sql:"+sql);
//        cusInfoDao.executeUpdateBySql(sql,params);
        tbCouponDao.executeUpdateBySql(sql,params);
    }

    @Override
    public void addTbCoupon(TbCouponForm tbCouponForm) {
        TbCoupon tbCoupon = new TbCoupon();
        BeanUtils.copyProperties(tbCouponForm,tbCoupon,TbCoupon.class);
//        System.out.println(tbCoupon.getPublishQty());
        tbCoupon.setCreateDate(new Date());
        tbCouponDao.persist(tbCoupon);
    }

    public static void main(String[] args) {
        String[] paths = new String[2];
        paths[0] = "applicationContext.xml";
        paths[1] = "applicationContext-project.xml";

        //ok
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(paths);
//        CusInfoDao cusInfoDao =(CusInfoDao) applicationContext.getBean("cusInfoDaoImpl");
//        TbCouponServiceImpl  tbCouponServiceImpl= SpringUtils.getBean("tbCouponServiceImpl");
        TbCouponService tbCouponService= SpringUtils.getBean("tbCouponServiceImpl");

        TbCoupon tbCoupon = tbCouponService.find((long) 1);
        System.out.println(tbCoupon);
        System.out.println("over");
    }

    public static void main2(String[] args) {
        String[] paths = new String[2];
        paths[0] = "applicationContext.xml";
        paths[1] = "applicationContext-project.xml";

        //ok
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(paths);
//        CusInfoDao cusInfoDao =(CusInfoDao) applicationContext.getBean("cusInfoDaoImpl");
        TbCouponServiceImpl  tbCouponServiceImpl= SpringUtils.getBean("tbCouponServiceImpl");

        TbCouponForm tbCouponForm = new TbCouponForm();
        tbCouponForm.setState("coupon_status_no_use");
        tbCouponForm.setCreateDate(new Date());
//        tbCouponForm.setId((long)1);
        tbCouponForm.setPublishQty(4999);
//        tbCouponServiceImpl.updateTbCoupon(tbCouponForm);
        tbCouponServiceImpl.addTbCoupon(tbCouponForm);
        System.out.println("over");
    }

}
