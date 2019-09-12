package com.catt.oil.web.controller.admin.lockPrice;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.catt.common.module.enumMgr.service.EnumMgrService;
import com.catt.oil.repository.entity.lockPriceMgr.LockPrice;
import com.catt.oil.repository.entity.oilStationGroup.OilstationGroup;
import com.catt.oil.repository.form.lockPrice.LockPriceForm;
import com.catt.oil.service.base.lockPrice.LockPriceService;
import com.catt.oil.service.oilStationGroup.OilstationGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.catt.common.base.pojo.search.Page;
import com.catt.common.base.pojo.search.Pageable;
import com.catt.common.web.Message;
import com.catt.common.web.controller.BaseController;


/**
 * 锁价的Controller
 */
@RequestMapping({"/admin/lockPrice"})
@Controller("admin.lockPrice")
public class LockPriceController extends BaseController {
    @Resource(name = "lockPriceServiceImpl")
    private LockPriceService lockPriceService;


    @Resource(name = "oilstationGroupServiceImpl")
    private OilstationGroupService oilstationGroupService;


    /**
     * 枚举服务层对象
     */
    @Resource(name = "enumMgrServiceImpl")
    private EnumMgrService enumMgrService;

    /**
     * 删除锁价
     * @param model
     * @return
     */
    @RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
    public String delete(LockPriceForm lockPriceForm,Model model){
        Assert.notNull(lockPriceForm.getIds(),"id不能为空");

        String[] idsArr = lockPriceForm.getIds().split(",");
        lockPriceService.delete(idsArr);
        return "/admin/lockPrice/lockPriceIndex";
    }

    /**
     * 修改状态
     * @param model
     * @return
     */
    @RequestMapping(value = {"/updateStatus"}, method = RequestMethod.POST)
    public String updateStatus(LockPriceForm lockPriceForm,Model model){
        Assert.notNull(lockPriceForm.getId(),"id不能为空");
        Assert.notNull(lockPriceForm.getStatus(),"状态不能为空");

        lockPriceService.updateStatus(lockPriceForm.getId(),lockPriceForm.getStatus().toString());
        return "/admin/lockPrice/lockPriceIndex";
    }

    /**
     * 跳转到新增
     * @param model
     * @return
     */
    @RequestMapping(value = {"/toAdd/{groupId}"}, method = RequestMethod.GET)
    public String toAdd(@PathVariable("groupId") String groupId,Model model){
        model.addAttribute("groupId",groupId);

        List stationList = lockPriceService.findOilStationListByGroup(groupId);
        model.addAttribute("stationList",stationList);
        return "/admin/lockPrice/lockPriceAdd";
    }


    /**
     * 跳转到修改
     * @param model
     * @return
     */
    @RequestMapping(value = {"/toEdit/{id}/{groupId}"}, method = RequestMethod.GET)
    public String toEdit(@PathVariable("id") String id,@PathVariable("groupId") String groupId, Model model){
        model.addAttribute("groupId",groupId);
        LockPrice lockPrice = lockPriceService.find(id);

        //查询集团下的油价id
        List stationList = lockPriceService.findOilStationListByGroup(groupId);
        model.addAttribute("stationList",stationList);

        List lockList = lockPriceService.findOilStationList(id);
        model.addAttribute("lockList",lockList);

        model.addAttribute("lockPrice",lockPrice);
        return "/admin/lockPrice/lockPriceEdit";
    }



    /**
     * 跳转到列表页
     * @return
     */
    @RequestMapping(value = "/lockPriceIndex")
    public String toIndex(String groupId,Model model){
        model.addAttribute("groupId", groupId);
        //获取集团列表信息
        List<OilstationGroup> list = oilstationGroupService.getOilStationGroupList();
        model.addAttribute("oilstationGroupList", list);

        return "/admin/lockPrice/lockPriceIndex";
    }


    /**
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = {"/lockPriceList"}, method = RequestMethod.POST)
    @ResponseBody
    public Page<Map> lockPriceList(LockPriceForm lockPriceForm, Integer pageNo, Integer pageSize) {
        Pageable pageable = new Pageable(pageNo, pageSize);
        Page<Map> page = lockPriceService.lockPriceList(lockPriceForm, pageable);
        return page;
    }

    /**
     * 锁价修改
     */
    @RequestMapping(value = {"/updateLockPrice"}, method = RequestMethod.POST)
    @ResponseBody
    public Message updateLockPrice(LockPriceForm lockPriceForm){
        lockPriceService.updateLockPrice(lockPriceForm);

        return SUCCESS_MSG;
    }



    /**
     * 锁价新增
     */
    @RequestMapping(value = {"/addLockPrice"}, method = RequestMethod.POST)
    @ResponseBody
    public Message addLockPrice(LockPriceForm lockPriceForm){


        lockPriceService.addLockPrice(lockPriceForm);
        return SUCCESS_MSG;
    }


    /**
     * 根据id查询
     */
    @RequestMapping(value = {"/findById"}, method = RequestMethod.POST)
    @ResponseBody
    public LockPrice findById(LockPriceForm lockPriceForm) {
//        System.out.println("id:"+lockPriceForm.getId());
        Assert.notNull(lockPriceForm.getId(),"id不能为空");
        LockPrice lockPrice = lockPriceService.find(lockPriceForm.getId());

        return lockPrice;
    }



}

