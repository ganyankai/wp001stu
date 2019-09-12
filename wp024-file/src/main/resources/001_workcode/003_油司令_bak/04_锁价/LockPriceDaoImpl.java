package com.catt.oil.repository.dao.lockPrice.impl;

import com.catt.common.base.pojo.search.Page;
import com.catt.common.base.pojo.search.Pageable;
import com.catt.common.base.repository.dao.impl.BaseDaoImpl;
import com.catt.oil.repository.dao.lockPrice.LockPriceDao;
import com.catt.oil.repository.entity.lockPriceMgr.LockPrice;
import com.catt.oil.repository.entity.oilStationMgr.OilstationInfo;
import com.catt.oil.repository.form.coupon.TbCouponForm;
import com.catt.oil.repository.form.lockPrice.LockPriceForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 锁价Dao接口实现
 *
 */
@Repository("lockPriceDaoImpl")
public class LockPriceDaoImpl extends BaseDaoImpl<LockPrice, String> implements LockPriceDao {
    @Override
    public Page<Map> lockPriceList(LockPriceForm lockPriceForm, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        sql.append(getQuerySql());
        sql.append(" where 1=1 ");

        if(lockPriceForm.getProductKind()!=null){
            sql.append(" and a.product_kind = '"+lockPriceForm.getProductKind()+"' ");
        }
        if(StringUtils.isNotBlank(lockPriceForm.getGroupId())){
            sql.append(" and a.group_id = '"+lockPriceForm.getGroupId()+"' ");
        }
        if(lockPriceForm.getStatus()!=null){
            sql.append(" and a.status = '"+lockPriceForm.getStatus()+"' ");
        }
        //根据锁价id和集团id查询所有的加油站
        Map params = new HashMap<>();

        Page page = this.findPageBySql(sql.toString(), params, pageable, Map.class);

        List list = page.getContent();
        for (Object obj:
             list) {
            Map map = (Map) obj;
            String  id =    (String) map.get("id");
            String  groupId =    (String) map.get("groupId");
            List stationList = findOilStationList(id);
            map.put("stationList",stationList);
            map.put("stationListStr",listToStr(stationList));
        }

        return page;
    }

    //根据锁价id查询所有的油站
    public List findOilStationList(String id){
        StringBuilder sql = new StringBuilder();
        sql.append("select s_id as id,s_name as name from t_oilstation_info where s_id in   \n" +
                "\t\t(\n" +
                "\t\t\t select STATION_ID from t_lock_oilstation where LOCK_PRICE_ID = '"+ id +"'  \n" +
                "\t\t )");
        List<OilstationInfo> list = this.findListBySql(sql.toString(), null, OilstationInfo.class);
        return list;
    }

    //根据集团id查询所有的油站
    public List findOilStationListByGroup(String groupId){
        StringBuilder sql = new StringBuilder();
        sql.append("select t.s_id as id,t.s_name as name    \n" +
                "\t from t_oilstation_info t,t_oilstation_rel_group torg    \n" +
                "\t where t.s_id = torg.S_OILSTATION_ID and torg.S_GROUP_ID = " + groupId);
        List<OilstationInfo> list = this.findListBySql(sql.toString(), null, OilstationInfo.class);
        return list;
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
        Map params = new HashMap<>();
        return this.findPageBySql(sql.toString(), params, pageable, Map.class);
    }



    /**
     *  查询锁价列表
     */
    private String getQuerySql() {
        System.out.println("flag:1");
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");

        sql.append(" a.s_id as \"id\",");
        sql.append(" a.group_id as \"groupId\",");
        sql.append(" a.product_kind as \"productKind\", ");
        sql.append(" a.status as \"status\", ");
        sql.append(" a.death_range as \"deathRange\", ");

        sql.append(" a.float_range as \"floatRange\", ");
        sql.append(" a.d_create_date as \"createDate\", ");
        sql.append(" a.d_modify_date as \"modifyDate\" ");

        sql.append(" from t_lock_price a");
        return sql.toString();
    }

    private String listToStr(List<OilstationInfo> list) {
        String idsStr = "";
        for (int i = 0; i < list.size() ; i++) {
            if (i==list.size()-1){
                idsStr = idsStr + list.get(i).getName();
            }else{
                idsStr = idsStr + list.get(i).getName() + ",";
            }
        }
        return idsStr;
    }


}
