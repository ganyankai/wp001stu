package com.catt.oil.web.controller.admin.customerMgr;

import com.catt.common.base.pojo.search.Page;
import com.catt.common.base.pojo.search.Pageable;
import com.catt.common.util.collections.CollectionUtil;
import com.catt.common.util.lang.StringUtil;
import com.catt.common.web.Message;
import com.catt.common.web.controller.BaseController;
import com.catt.oil.repository.data.CusInfoVo;
import com.catt.oil.repository.form.customerMgr.CusInfoForm;
import com.catt.oil.service.customerMgr.CusInfoService;
import com.catt.oil.service.customerMgr.CusOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.CryptUtil;
import util.MaskUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pachage: com.catt.oil.web.controller.admin.customerMgr.customerQuery
 * Description:注册用户Controller
 * date: 2015-11-19 下午5:31
 * author: Zhou mingxiang
 */
@RequestMapping({"/admin/customerMgr/cusInfo"})
@Controller("admin.customerMgr.cusInfo")
public class CusInfoController extends BaseController {

    /**
     * 注册用户表服务层对象
     */
    @Resource(name = "cusInfoServiceImpl")
    private CusInfoService cusInfoService;

    /**
     * 用户订单服务接口实现
     */
    @Resource(name = "cusOrderServiceImpl")
    private CusOrderService cusOrderService;

    /**
     * 跳转注册用户查询首页
     */
    @RequestMapping(value = {"/toIndex"}, method = RequestMethod.GET)
    public String toIndex() {
        return "/admin/customerMgr/customerQuery/index";
    }

    /**
     * 跳转选择邀请人页面
     */
    @RequestMapping(value = {"/selectCus.html"}, method = RequestMethod.GET)
    public String selectCus() {
        return "/admin/customerMgr/customerQuery/selectCus";
    }

    /**
     * 冻结成功显示
     *
     * @return
     */
    @RequestMapping(value = {"/enableDisplay.html"}, method = RequestMethod.GET)
    public String enableDisplay() {
        return "/admin/oilStationMgr/query/enableDisplay";
    }

    /**
     * 取消显示
     *
     * @return
     */
    @RequestMapping(value = {"/disableDisplay.html"}, method = RequestMethod.GET)
    public String disableDisplay() {
        return "/admin/oilStationMgr/query/disableDisplay";
    }

    /**
     * 跳转到用户详情界面
     *
     * @param id    用户ID
     * @param model
     * @return
     */
    @RequestMapping(value = "/toView/{id}", method = RequestMethod.GET)
    public String toView(@PathVariable("id") Long id, Model model) {
        CusInfoVo cusInfo = cusInfoService.getCusInfoById(id);
        model.addAttribute("cusInfo", cusInfo);
        return "/admin/customerMgr/customerQuery/detail";
    }

    /**
     * 跳转到银行卡认证界面
     *
     * @param id    用户ID
     * @param model
     * @return
     */
    @RequestMapping(value = "/toBankView/{id}", method = RequestMethod.GET)
    public String toBankView(@PathVariable("id") Long id, Model model) {
        CusInfoVo cusInfo = cusInfoService.getCusInfoById(id);
        if (StringUtil.isNotBlank(cusInfo.getBankCard())) {
            String bankCard = CryptUtil.decryptDatabase(cusInfo.getBankCard());
            cusInfo.setBankCard(MaskUtil.maskBankCard(bankCard));
        }
        if (StringUtil.isNotBlank(cusInfo.getBankTel())) {
            String bankTel = CryptUtil.decryptDatabase(cusInfo.getBankTel());
            cusInfo.setBankTel(MaskUtil.maskMobileNo(bankTel));
        }
        if (StringUtil.isNotBlank(cusInfo.getBankName())) {
            String bankName = CryptUtil.decryptDatabase(cusInfo.getBankName());
            cusInfo.setBankName(MaskUtil.maskName(bankName));
        }
        model.addAttribute("cusInfo", cusInfo);
        return "/admin/customerMgr/customerQuery/bankCard";
    }

    /**
     * 跳转到身份证信息界面
     *
     * @param id    用户ID
     * @param model
     * @return
     */
    @RequestMapping(value = "/toVerificationView/{id}", method = RequestMethod.GET)
    public String toVerificationView(@PathVariable("id") Long id, Model model) {
        CusInfoVo cusInfo = cusInfoService.getCusInfoById(id);
        //身份证和真实姓名转译
        if (StringUtil.isNotBlank(cusInfo.getRealName())) {
            String realName = CryptUtil.decryptDatabase(cusInfo.getRealName());
            //截取一定字段其他用*号替代
            cusInfo.setRealName(MaskUtil.maskName(realName));
        }
        if (StringUtil.isNotBlank(cusInfo.getIdentityCard())) {
            String identityCard = CryptUtil.decryptDatabase(cusInfo.getIdentityCard());
            cusInfo.setIdentityCard(MaskUtil.maskCertNo(identityCard));
        }
        model.addAttribute("cusInfo", cusInfo);
        return "/admin/customerMgr/customerQuery/verification";
    }

    /**
     * 修改账户状态
     *
     * @param ids           用户id集合
     * @param accountStatus 1或2  1为冻结 2为解除冻结
     * @return
     */
    @RequestMapping(value = "/updateAccountStatus", method = RequestMethod.POST)
    @ResponseBody
    public Message updateAccountStatus(@RequestParam("id") List<Long> ids, Integer accountStatus) {
        Assert.state(CollectionUtil.isNotEmpty(ids), "请选择油站");
        cusInfoService.updateByIdInAndStatus(ids, accountStatus);
        return SUCCESS_MSG;
    }

    /**
     * 查询注册用户信息
     *
     * @param cusInfoForm 用户管理主表表单对象
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = {"/getCusInfoByList"}, method = RequestMethod.POST)
    @ResponseBody
    public Page<Map> getCusInfoByList(CusInfoForm cusInfoForm, Integer pageNo, Integer pageSize) {
        Pageable pageable = new Pageable(pageNo, pageSize);
        Page<Map> page = cusInfoService.getCusInfoByList(cusInfoForm, pageable);
        return page;
    }

    /**
     * 获取累计注册用户和昨日注册用户数量
     */
    @RequestMapping(value = {"/getCusRegis"})
    @ResponseBody
    public Map<String, String> getCusRegis() {
        Map<String, String> map = cusInfoService.getCusRegis();
        return map;
    }

    /**
     * 用户加油记录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/{cusId}/findCusRefuelRecord"}, method = RequestMethod.GET)
    public Map<String, Object> findCusRefuelRecord(@PathVariable Long cusId) {
        List<Map> cusOrderList = cusOrderService.findCusRefuelRecord(cusId);
        Map<String, Object> map = new HashMap<>();
        map.put("refuelTotal", cusInfoService.getCusInfoById(cusId).getConsumptionMoney()); // 用户累计加油金额
        map.put("refuelRecord", cusOrderList); // 当年用户加油记录(报表数据)
        return map;
    }
}
