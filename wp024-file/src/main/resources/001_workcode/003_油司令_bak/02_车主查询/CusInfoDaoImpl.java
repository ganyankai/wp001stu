package com.catt.oil.repository.dao.customerMgr.impl;

import com.catt.common.base.pojo.search.Page;
import com.catt.common.base.pojo.search.Pageable;
import com.catt.common.base.repository.dao.impl.BaseDaoImpl;
import com.catt.common.util.collections.CollectionUtil;
import com.catt.common.util.lang.DateUtil;
import com.catt.common.util.lang.StringUtil;
import com.catt.oil.repository.dao.customerMgr.CusInfoDao;
import com.catt.oil.repository.data.comEnum.DateDimension;
import com.catt.oil.repository.entity.customerMgr.CusInfo;
import com.catt.oil.repository.form.customerMgr.CusInfoForm;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注册用户表Dao接口实现
 *
 * @author 周明祥
 * @version V1.0
 * @date 2015-11-19 11:15:57
 */
@Repository("cusInfoDaoImpl")
public class CusInfoDaoImpl extends BaseDaoImpl<CusInfo, Long> implements CusInfoDao {

    public Page<Map> getCusInfoByList(CusInfoForm cusInfoForm, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        sql.append(getQuerySql());
        sql.append(" where 1=1 ");
        sql.append("and a.I_DEL_FLAG <> 1 ");
        Map params = new HashMap<>();
        if (StringUtil.checkObj(cusInfoForm.getId())) {  //用户ID
            sql.append(" and  a.I_ID = :id");
            params.put("id", cusInfoForm.getId());
        }
        if (StringUtil.checkObj(cusInfoForm.getAccount())) {   //用户账号
            sql.append(" and   a.S_NICK_NAME like  :account");
            params.put("account", "%" + cusInfoForm.getAccount() + "%");
        }
        if (StringUtil.checkObj(cusInfoForm.getIsVerification())) {  //实名认证
            sql.append(" and  d.I_IS_VERIFICATION =  :isVerification ");
            params.put("isVerification", cusInfoForm.getIsVerification());
        }
        if (StringUtil.checkObj(cusInfoForm.getIsBindcard())) {    //银行卡绑定
            sql.append(" and  d.I_IS_BINDCARD =  :isBindcard");
            params.put("isBindcard", cusInfoForm.getIsBindcard());
        }
        if (StringUtil.checkObj(cusInfoForm.getAccountStatus())) {  //账户状态
            sql.append(" and  d.I_IS_ACTIVATE =  :accountStatus");
            params.put("accountStatus", cusInfoForm.getAccountStatus());
        }

        if (cusInfoForm.getStartTime() != null) {   //创建时间--开始时间
            sql.append(" and  a.D_CREATE_DATE >=  :startTime");
            params.put("startTime", cusInfoForm.getStartTime());
        }
        if (cusInfoForm.getEndTime() != null) {     //创建时间--结束时间
            sql.append(" and a.D_CREATE_DATE <=  :endTime");
            params.put("endTime", cusInfoForm.getEndTime());
        }

        if (StringUtil.isNotBlank(cusInfoForm.getCusInviationCode())){
            sql.append(" and a.S_INVIATION_CODE =  :inviationCode");
            params.put("inviationCode", cusInfoForm.getCusInviationCode());
        }

        sql.append(" order by a.D_CREATE_DATE DESC");
//        if (StringUtil.checkObj(cusInfoForm.getAccountMoney())) {
//            sql.append(" ,c.I_ACCOUNT_MONEY ").append(cusInfoForm.getAccountMoney());
//        }
//        if (StringUtil.checkObj(cusInfoForm.getConsumptionMoney())) {
//            sql.append(" ,c.I_CONSUMPTION_MONEY ").append(cusInfoForm.getConsumptionMoney());
//        }
        return this.findPageBySql(sql.toString(), params, pageable, Map.class);
    }

    public Page<Map> getCusInfoById(Long id) {
        StringBuilder sql = new StringBuilder();
        sql.append(getQuerySql());
        sql.append(" where 1=1 ");
        Map params = new HashMap<>();
        Pageable pageable = new Pageable();
        if (StringUtil.checkObj(id)) {
            sql.append(" and  a.I_ID = :id");
            params.put("id", id);
        }
        return this.findPageBySql(sql.toString(), params, pageable, Map.class);
    }


    public Map findById2Map(Long id) {
        StringBuilder sql = new StringBuilder();
        sql.append(getQuerySql());
        sql.append(" where 1=1 ");
        Pageable pageable = new Pageable();
        Map params = new HashMap<>();
        if (id != null) {
            sql.append(" and  a.I_ID =  :id");
            params.put("id", id);
        }
        Page<Map> page = this.findPageBySql(sql.toString(), params, pageable, Map.class);

        Map result = new HashMap();
        if (page != null && CollectionUtil.isNotEmpty(page.getContent())) {
            result = page.getContent().get(0);
        }

        return result;
    }

    public void updateByIdInAndStatus(List<Long> ids, Integer accountStatus) {
        String jpql = "UPDATE OilstationAccountSina c set c.isActivate = :accountStatus WHERE c.id IN (:id) ";
        this.entityManager.createQuery(jpql)
                .setFlushMode(FlushModeType.COMMIT)
                .setParameter("id", ids)
                .setParameter("accountStatus", accountStatus)
                .executeUpdate();
    }

    /**
     * 用户注册统计图
     *
     * @param startCreateDate 开始时间
     * @param endCreateDate   结束时间
     * @return
     */
    @Override
    public List<Map> cusInfoRegisterStatisti(Date startCreateDate, Date endCreateDate, DateDimension dateDimension) {
        Map params = new HashMap<>();
        params.put("startCreateDate", startCreateDate);
        StringBuilder sb = new StringBuilder();

        switch (dateDimension) {
            case DAY:
                // yyyy-MM-dd时间转换为yyyy-MM-dd 23:59:59
                Date endDate4Day = DateUtil.addDays(endCreateDate, 1);
                endDate4Day = DateUtil.addSeconds(endDate4Day, -1);
                params.put("endCreateDate", endDate4Day);

                sb.append("SELECT new map( ");
                sb.append("DATE_FORMAT(t.createDate, '%Y-%m-%d') AS time, "); // 统计日期
                sb.append("COUNT(1) AS newRegisterCount "); // 统计总数
                sb.append(") FROM CusInfo t ");
                sb.append("WHERE 1=1 ");
                sb.append("AND t.createDate >= :startCreateDate ");
                sb.append("AND t.createDate <= :endCreateDate ");
                sb.append("GROUP BY DATE_FORMAT(t.createDate, '%Y-%m-%d') ");
                break;
            case MONTH:
                // 查询小于当前时间前一天23:59:59
                Date endDate4Month = DateUtil.clearTime(new Date());
                endDate4Month = DateUtil.addSeconds(endDate4Month, -1);
                params.put("endCreateDate", endDate4Month);

                sb.append("SELECT new map( ");
                sb.append("DATE_FORMAT(t.createDate, '%Y%m') AS time, "); // 统计日期
                sb.append("COUNT(1) AS newRegisterCount "); // 统计总数
                sb.append(") FROM CusInfo t ");
                sb.append("WHERE 1=1 ");
                sb.append("AND t.createDate >= :startCreateDate ");
                sb.append("AND t.createDate <= :endCreateDate ");
                sb.append("GROUP BY DATE_FORMAT(t.createDate, '%Y%m') ");
                break;
            case YEAR:
                break;
            default:
                break;
        }

        return this.findListByJql(sb.toString(), params, Map.class);
    }

    /**
     * 获取查询脚本
     *
     * @关联人员信息 银行账户 个人账户信息
     */
    private String getQuerySql() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select ");
        sql.append(" a.I_ID as \"id\",");
        sql.append(" a.S_ACCOUNT as \"account\",");
        sql.append(" a.S_EMAIL as \"email\",");
        sql.append(" a.S_NICK_NAME as \"nickName\",");
        sql.append(" a.S_HEADPIC_PATH as \"headpicPath\",");
        sql.append(" a.S_INVIATION_CODE as \"inviationCode\",");
        sql.append(" a.S_CUS_INVIATION_CODE as \"cusInviationCode\",");
        sql.append(" a.D_CREATE_DATE as \"dCreateDate\",");
        sql.append(" a.S_SYS_PWD as \"sysPwd\",");
        sql.append(" a.birth as \"birth\",");
//        sql.append(" d.S_REL_NAME as \"realName\",");
//        sql.append(" d.S_REL_IDENTITY_CARD as \"identityCard\",");
//        sql.append(" d.I_IS_SET_SINA_PASSWORD as \"isSetSinaPassword\",");
//        sql.append(" ifnull(d.I_IS_BINDCARD, 2) as \"isBindcard\",");
//        sql.append(" ifnull(d.I_IS_VERIFICATION, 2) as \"isVerification\",");
//        sql.append(" ifnull(d.I_IS_ACTIVATE, 3) as \"accountStatus\",");
//        sql.append(" d.I_ID as \"accountSinaId\",");
        sql.append(" b.S_BANK_TEL as \"bankTel\",");
        sql.append(" b.S_BANK_CARD as \"bankCard\",");
        sql.append(" b.S_BANK_NAME as \"bankName\",");
        sql.append(" c.I_ACCOUNT_MONEY as \"accountMoney\",");
        sql.append(" c.I_CREDIT_LINE as \"creditLine\",");
        sql.append(" c.I_JINDOU_NUM as \"jindouNum\",");
        sql.append(" c.I_CONSUMPTION_MONEY as \"consumptionMoney\"");
        sql.append(" from T_CUS_INFO a");
        sql.append(" left join T_CUS_BANK b on a.I_SINA_ACCOUNT_ID = b.I_CUS_ID ");
        sql.append(" left join T_CUS_ACCOUNT c on a.I_ID = c.I_CUS_ID ");
//        sql.append(" left join T_OILSTATION_ACCOUNT_SINA d on a.I_SINA_ACCOUNT_ID = d.I_ID ");
        return sql.toString();
    }
}
