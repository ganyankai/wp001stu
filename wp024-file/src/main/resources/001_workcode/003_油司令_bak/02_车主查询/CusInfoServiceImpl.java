package com.catt.oil.service.impl.customerMgr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.catt.common.util.collections.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.CryptUtil;
import util.MaskUtil;
import util.ShareCodeUtil;

import com.catt.common.base.pojo.search.Filter;
import com.catt.common.base.pojo.search.Page;
import com.catt.common.base.pojo.search.Pageable;
import com.catt.common.module.enumMgr.pojo.EnumTransformData;
import com.catt.common.module.enumMgr.service.EnumMgrService;
import com.catt.common.module.security.service.StaffService;
import com.catt.common.util.bean.BeanUtil;
import com.catt.common.util.collections.MapUtil;
import com.catt.common.util.json.JsonUtils;
import com.catt.common.util.lang.DateUtil;
import com.catt.common.util.lang.StringUtil;
import com.catt.oil.common.service.system.PubSetupBaseService;
import com.catt.oil.repository.dao.customerMgr.CusInfoDao;
import com.catt.oil.repository.data.CusInfoVo;
import com.catt.oil.repository.data.Html5Url;
import com.catt.oil.repository.data.mobile.CusInfoByIdVo;
import com.catt.oil.repository.entity.customerMgr.CusAccount;
import com.catt.oil.repository.entity.customerMgr.CusInfo;
import com.catt.oil.repository.entity.customerMgr.PayRecord;
import com.catt.oil.repository.entity.customerMgr.PubSetup;
import com.catt.oil.repository.entity.oilStationMgr.OilstationAccountSina;
import com.catt.oil.repository.form.customerMgr.CusInfoForm;
import com.catt.oil.service.account.SinaAccountService;
import com.catt.oil.service.base.customerMgr.CusAccountBaseService;
import com.catt.oil.service.base.customerMgr.CusInfoBaseService;
import com.catt.oil.service.base.customerMgr.PayRecordBaseService;
import com.catt.oil.service.base.customerMgr.SharePriceRecordBaseService;
import com.catt.oil.service.customerMgr.CusInfoService;

//import com.catt.oil.jiesuan.service.ShareJiesuanService;

/**
 * 注册用户表服务接口实现
 *
 * @author 周明祥
 * @version V1.0
 * @date 2015-11-19 11:15:57
 */

@Service("cusInfoServiceImpl")
public class CusInfoServiceImpl implements CusInfoService {

    private static final Logger LOG = LoggerFactory
            .getLogger(CusInfoServiceImpl.class);

    @Resource(name = "cusInfoDaoImpl")
    private CusInfoDao cusInfoDao;
    /**
     * 枚举服务层对象
     */
    @Resource(name = "enumMgrServiceImpl")
    private EnumMgrService enumMgrService;
    /**
     * 用户服务层对象
     */
    @Resource
    private CusInfoBaseService cusInfoBaseService;
    /**
     * 用户账户服务层对象
     */
    @Resource
    private CusAccountBaseService cusAccountBaseService;
    /**
     * 油金豆配置表
     */
    @Resource
    private PubSetupBaseService pubSetupBaseService;
    /**
     * 充值记录服务层
     */
    @Resource
    private PayRecordBaseService payRecordBaseService;
    /**
     * 新浪账户设置服务接口
     */
    @Resource
    private SinaAccountService sinaAccountService;
    /**
     * 人员信息
     */
    @Resource(name = "staffServiceImpl")
    private StaffService staffService;
    /**
     * 分享所得资金记录
     */
    @Resource
    private SharePriceRecordBaseService sharePriceRecordBaseService;


    /**
     * 查询注册用户信息
     *
     * @param cusInfoForm 用户管理主表表单对象
     * @param pageable
     * @return
     */

    public Page<Map> getCusInfoByList(CusInfoForm cusInfoForm, Pageable pageable) {
        Page<Map> pageMap = cusInfoDao.getCusInfoByList(cusInfoForm, pageable);
        List<Map> pageList = this.getEnumMgrName(pageMap.getContent());
        if(CollectionUtil.isEmpty(pageList)){
            return pageMap;
        }
        List<String> codeList = new ArrayList<>();
        for(Map map : pageList){
            codeList.add(MapUtil.getString(map, "inviationCode"));
        }
       List<CusInfo> cusInfos = cusInfoBaseService.findList(null, Arrays.asList(Filter.in("cusInviationCode", codeList)), null);
        Map<String, String> map1 = new HashMap<>();
        for (CusInfo cusInfo: cusInfos){
            map1.put(cusInfo.getCusInviationCode(), cusInfo.getNickName());
        }

        for(Map map : pageList){
            String inviationCode = MapUtil.getString(map, "inviationCode");

            if(map1.containsKey(inviationCode)){
                map.put("inviationName", map1.get(inviationCode));
            }

        }

        pageMap.setContent(pageList);
        return pageMap;
    }

    /**
     * 根据id获取对应用户信息
     *
     * @param id 用户ID
     * @return vo对象
     */
    public CusInfoVo getCusInfoById(Long id) {
        Page<Map> pageMap = cusInfoDao.getCusInfoById(id);
        List<Map> pageList = this.getEnumMgrName(pageMap.getContent());
        CusInfoVo cusInfoVo = new CusInfoVo();
        BeanUtil.copyProperties(cusInfoVo, pageList.get(0));
        return cusInfoVo;
    }

    /**
     * 修改账户状态
     *
     * @param ids           要修改账户对应id集合
     * @param accountStatus 1或2 1为冻结 2为解除
     */
    @Transactional
    public void updateByIdInAndStatus(List<Long> ids, Integer accountStatus) {
        cusInfoDao.updateByIdInAndStatus(ids, accountStatus);
    }

    /**
     * 枚举翻译
     *
     * @param pageList 获取到的数据集合
     * @return 枚举翻译后的数据集合
     */
    private List<Map> getEnumMgrName(List<Map> pageList) {
        if (pageList.size() > 0) {
            enumMgrService.transformEnum(pageList,
                    new ArrayList<EnumTransformData>() {
                        {
                            add(new EnumTransformData(
                                    "T_OILSTATION_ACCOUNT_SINA",
                                    "I_IS_VERIFICATION", "isVerification",
                                    "isVerificationName")); // 实名认证
                            add(new EnumTransformData(
                                    "T_OILSTATION_ACCOUNT_SINA",
                                    "I_IS_BINDCARD", "isBindcard",
                                    "isBindcardName")); // 是否绑定卡
                            add(new EnumTransformData(
                                    "T_OILSTATION_ACCOUNT_SINA",
                                    "I_IS_ACTIVATE", "accountStatus",
                                    "accountStatusName")); // 账户状态
                        }
                    });
        }
        return pageList;
    }

    @Override
    @Transactional
    public CusInfo registAccount(String mobileNo, String clientIp) {
        // 创建油小二车主新浪帐户
        OilstationAccountSina accountSina = sinaAccountService
                .createPersonalMember(clientIp);

        // 创建油小二车主账号
        CusInfo cusInfo = new CusInfo();
        cusInfo.setAccount(mobileNo); // 设置用户账号
        cusInfo.setNickName("NC" + StringUtil.substring(mobileNo, mobileNo.length() - 4, mobileNo.length())); // 设置用户昵称 注册时默认账号（NC+手机号码后4位）
        cusInfo.setDelFlag(2); // 设置删除标识 2为否
        cusInfo.setStatus(1); // 设置用户状态 1为激活
        cusInfo.setSinaAccountId(accountSina.getId());
        cusInfoBaseService.save(cusInfo);

        //根据用户标识（id）生成邀请码
        String inviteCode = ShareCodeUtil.toSerialCode(cusInfo.getId());
        cusInfo.setCusInviationCode(CusInfo.CHE_ZHU + inviteCode.toUpperCase());
        cusInfoBaseService.saveOrUpdate(cusInfo);

        // 创建用户账户
        CusAccount cusAccount = new CusAccount();
        cusAccount.setCusId(cusInfo.getId()); // 用户标识
        String jindouSetupValue = pubSetupBaseService
                .getSetupByCode(PubSetup.Code.jindouCode);
        int douNumPerL = MapUtil.getIntValue(
                JsonUtils.toObject(jindouSetupValue, Map.class),
                "registerJindou");
        cusAccount.setJindouNum(douNumPerL); // 金豆数量
        cusAccount.setConsumptionMoney(0D); // 初始化账户累计消费金额
        cusAccount.setAccountMoney(0D); // 初始化账户金额
        cusAccount.setCreditLine(0D); // 初始化信用额度
        cusAccountBaseService.save(cusAccount);

        // 用户领取油金豆数据记录
        Integer register = PayRecord.Type.Register.getValue(); //领取类型  -- 注册
        this.regisDelver(cusInfo, douNumPerL, register);

        return cusInfo;
    }

    public CusInfoByIdVo findById2Map(Long id) {
        // 同步新浪帐户状态
        sinaAccountService.syncAccountStatus(sinaAccountService
                .getSinaAccountIdByCusId(id));

        Map map = cusInfoDao.findById2Map(id);

        enumMgrService.transformEnum(map, new ArrayList<EnumTransformData>() {
            {
                add(new EnumTransformData("T_OILSTATION_ACCOUNT_SINA",
                        "I_IS_VERIFICATION", "isVerification",
                        "isVerificationName")); // 实名认证
                add(new EnumTransformData("T_OILSTATION_ACCOUNT_SINA",
                        "I_IS_BINDCARD", "isBindcard", "isBindcardName")); // 是否绑定卡
                add(new EnumTransformData("T_OILSTATION_ACCOUNT_SINA",
                        "I_IS_ACTIVATE", "accountStatus", "accountStatusName")); // 账户状态
                add(new EnumTransformData("T_OILSTATION_ACCOUNT_SINA",
                        "I_IS_SET_SINA_PASSWORD", "isSetSinaPassword",
                        "isSetSinaPasswordName")); // 是否设置新浪支付密码
            }
        });
        CusInfoByIdVo cusInfoByIdVo = BeanUtil.populate(CusInfoByIdVo.class,
                map);
        // 身份证和真实姓名转译,截取一定字段其他用*号替代
        String realName = CryptUtil
                .decryptDatabase(cusInfoByIdVo.getRealName());
        cusInfoByIdVo.setRealName(MaskUtil.maskName(realName));
        String identityCard = CryptUtil.decryptDatabase(cusInfoByIdVo
                .getIdentityCard());
        cusInfoByIdVo.setIdentityCard(MaskUtil.maskCertNo(identityCard));

        String shareUrl = pubSetupBaseService
                .getSetupByCode(PubSetup.Code.shareUrl);

        if (StringUtil.isNotBlank(cusInfoByIdVo.getCusInviationCode())) {
            cusInfoByIdVo.setShareUrl(shareUrl + "?shareCode=" + cusInfoByIdVo.getCusInviationCode());
        } else {
            cusInfoByIdVo.setShareUrl(shareUrl);
        }
        String wechatUrl = pubSetupBaseService
                .getSetupByCode(PubSetup.Code.wechatUrl);

        cusInfoByIdVo.setInviteRuleUrl(wechatUrl + Html5Url.inviteRule);
        cusInfoByIdVo.setOilGoldRuleUrl(wechatUrl + Html5Url.oilGoldRule);
        return cusInfoByIdVo;
    }

    public void saveOrUpdateInfo(Long id, String nickName, String email,
                                 String headpicPath) {
        // 根据ID获取对应注册用户信息
        CusInfo cusInfo = cusInfoBaseService.find(id);
        if (StringUtil.isNotBlank(nickName)) {
            cusInfo.setNickName(nickName);
        }
        cusInfo.setEmail(email);
        if (StringUtil.isNotBlank(headpicPath)) {
            cusInfo.setHeadpicPath(headpicPath);
        }
        cusInfoBaseService.update(cusInfo); // 修改用户
    }

    public Map<String, String> getCusRegis() {
        Map<String, String> map = new HashMap<>();
        // 获取累计注册用户数量
        List<CusInfo> list = cusInfoBaseService.findAll();
        map.put("regisTotal", StringUtil.toString(list.size())); // 累计注册用户数量

        // 获取昨天注册用户数量
        String lastTime = DateUtil.getNearByDay("yyyy-MM-dd 00:00:00", -1);
        List<Filter> filters = new ArrayList<>();
        Pageable pageable = new Pageable();
        filters.add(Filter.dgt("createDate", DateUtil.parseDate(lastTime)));
        filters.add(Filter.dlt("createDate", DateUtil.parseDate(lastTime)));
        pageable.setFilters(filters);
        Page<CusInfo> pageList = cusInfoBaseService.findPage(pageable);
        map.put("lastTotal", StringUtil.toString(pageList.getTotal())); // 昨天注册用户数量
        return map;
    }

    /**
     * 用户注册领取油金豆记录
     */
    private void regisDelver(CusInfo cusInfo, int registerJindou, Integer type) {
        PayRecord payRecord = new PayRecord();
        payRecord.setCusId(cusInfo.getId()); // 用户标识
        payRecord.setNickName(cusInfo.getNickName()); // 用户昵称
        payRecord.setType(type); // 领取类型
        payRecord.setJindouNum(Long.valueOf(registerJindou)); // 金豆数量
        payRecordBaseService.save(payRecord);
    }

    @Transactional
    public void enterInvitationCode(String inviationCode, Long id) {}

    /**
     * 车主输入邀请码后第一次下单成功
     */
    @Transactional
    public void saveInvitationAward(Long cusId) {}

    /**
     * 根据微信用户openId注册用户
     *
     * @param openId 微信用户openId
     * @param nickName 微信用户昵称
     * @return CusInfo 注册的对象
     */
    public   CusInfo registAccountByOpenId(String openId, String nickName){
        // 创建油小二车主账号
        CusInfo cusInfo = new CusInfo();
        cusInfo.setAccount(openId); // 设置用户账号
        cusInfo.setNickName(nickName); // 设置用户昵称 注册时默认账号（NC+手机号码后4位）
        cusInfo.setDelFlag(2); // 设置删除标识 2为否
        cusInfo.setStatus(1); // 设置用户状态 1为激活
        cusInfo.setRemark("微信直接扫码下单，后台自动创建账户");
        cusInfoBaseService.save(cusInfo);

        //根据用户标识（id）生成邀请码
        String inviteCode = ShareCodeUtil.toSerialCode(cusInfo.getId());
        cusInfo.setCusInviationCode(CusInfo.CHE_ZHU + inviteCode.toUpperCase());
        cusInfoBaseService.saveOrUpdate(cusInfo);

        // 创建用户账户
        CusAccount cusAccount = new CusAccount();
        cusAccount.setCusId(cusInfo.getId()); // 用户标识
        cusAccount.setJindouNum(0); // 金豆数量
        cusAccount.setConsumptionMoney(0D); // 初始化账户累计消费金额
        cusAccount.setAccountMoney(0D); // 初始化账户金额
        cusAccount.setCreditLine(0D); // 初始化信用额度
        cusAccountBaseService.save(cusAccount);

        return cusInfo;
    }

}
