package com.zrytech.framework.newshop.service.Impl;

import com.github.pagehelper.PageHelper;
import com.zrytech.framework.base.entity.PageData;
import com.zrytech.framework.base.entity.ServerResponse;
import com.zrytech.framework.base.exception.BusinessException;
import com.zrytech.framework.common.repository.CustomerRepository;
import com.zrytech.framework.distribution.entity.CommissionLog;
import com.zrytech.framework.distribution.enums.CommissionType;
import com.zrytech.framework.distribution.enums.VipType;
import com.zrytech.framework.distribution.repository.CommissionLogRepository;
import com.zrytech.framework.distribution.repository.CustomerExtRepository;
import com.zrytech.framework.newshop.dto.YsWithdrawDto;
import com.zrytech.framework.newshop.entity.YsWithdraw;
import com.zrytech.framework.newshop.entity.app.CustomerExtend;
import com.zrytech.framework.newshop.mapper.CustomerExtendMapper;
import com.zrytech.framework.newshop.mapper.YsWithdrawMapper;
import com.zrytech.framework.newshop.service.YsWithdrawService;
import com.zrytech.framework.newshop.utils.DataBean;
import com.zrytech.framework.newshop.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class YsWithdrawServiceExtImpl implements YsWithdrawService{
    @Autowired
    private YsWithdrawMapper ysWithdrawMapper;
    @Autowired
    private CustomerExtendMapper customerExtendMapper;
    @Autowired
    private CommissionLogRepository commissionLogRepository;
    @Value("${file.tmp.dir}")
    private String filePath;

    @Override
    public ServerResponse getYsWithdrawFront(YsWithdrawDto ysWithdrawDto, Integer pageNum, Integer pageSize) {
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<YsWithdraw> list = ysWithdrawMapper.getYsWithdrawFront(ysWithdrawDto);
        PageData<YsWithdraw> pageData = new PageData<>(page.getPageSize(), page.getPageNum(), page.getTotal(), list);

        return ServerResponse.successWithData(pageData);
    }

    public void getYsWithdrawFrontExcel(YsWithdrawDto ysWithdrawDto, Integer pageNum, Integer pageSize, HttpServletResponse response) {
        com.github.pagehelper.Page<Object> page = PageHelper.startPage(pageNum, pageSize);
        List<YsWithdraw> list = ysWithdrawMapper.getYsWithdrawFront(ysWithdrawDto);

        List<String> titles = new ArrayList<>();
        titles.add("1_id");
        titles.add("2_提现金额");
        titles.add("3_订单号");
        titles.add("4_用户id");
        titles.add("5_可提现金额");

        titles.add("6_当前余额");
        titles.add("7_收款名称");
        titles.add("8_收款类型");
        titles.add("9_收款账号");
        titles.add("10_开户行");

        titles.add("11_开户行支行");
        titles.add("12_银盛商户号");

        List<DataBean> varList  = new ArrayList<>();
        for (int i = 0; i < list.size() ; i++) {
            YsWithdraw ysWithdraw = list.get(i);
            DataBean vpd = new DataBean();
            if (ysWithdraw.getId()!=null)
                vpd.put("var1",ysWithdraw.getId().toString());
            if (ysWithdraw.getTotalAmount()!=null)
                vpd.put("var2",ysWithdraw.getTotalAmount().toString());
            if (ysWithdraw.getOutTradeNo()!=null)
                vpd.put("var3",ysWithdraw.getOutTradeNo().toString());
            if (ysWithdraw.getUserId()!=null)
                vpd.put("var4",ysWithdraw.getUserId().toString());
            if (ysWithdraw.getTodayMoney()!=null)
                vpd.put("var5",ysWithdraw.getTodayMoney().toString());

            if (ysWithdraw.getBalance()!=null)
                vpd.put("var6",ysWithdraw.getBalance().toString());
            if (ysWithdraw.getName()!=null)
                vpd.put("var7",ysWithdraw.getName().toString());
            if (ysWithdraw.getType()!=null)
                vpd.put("var8",ysWithdraw.getType().toString());
            if (ysWithdraw.getCardNo()!=null)
                vpd.put("var9",ysWithdraw.getCardNo().toString());
            if (ysWithdraw.getBankType()!=null)
                vpd.put("var10",ysWithdraw.getBankType().toString());

            if (ysWithdraw.getBankName()!=null)
                vpd.put("var11",ysWithdraw.getBankName().toString());
            if (ysWithdraw.getUsercode()!=null)
                vpd.put("var12",ysWithdraw.getUsercode());

            varList.add(vpd);
        }
        String fileName=filePath+"/打款信息"+System.currentTimeMillis()+".xls";//设置文件名
        FileInputStream fileInputStream = null;
        try {
            ExcelUtils.toExcel(titles,varList,fileName);
            fileInputStream = new FileInputStream(fileName);
            response.reset();
            response.setContentType("application/msexcel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename= " + fileName);
            byte[] bytes = IOUtils.toByteArray(fileInputStream);

            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }catch (Exception e){
            e.printStackTrace();
            log.error("文件导出异常");
            throw new BusinessException(112,"文件导出异常");
        }

    }


    @Transactional
    @Override
    public ServerResponse confirmRemit(List<Integer> idsList) {
        //更新状态
        String idsStr = listToStr(idsList);
        ysWithdrawMapper.updateYsWithdrawStatus(idsStr);
        List<CommissionLog> commissionLogList = new ArrayList<>();

        //向CommissionLog批量插入记录
        for (Integer id:
             idsList) {
            YsWithdraw ysWithdraw = ysWithdrawMapper.findOne(id);
            Integer userId = ysWithdraw.getUserId();
            CustomerExtend customerExtend = customerExtendMapper.getCustomerExtendById(userId);

            CommissionLog commissionLog = new CommissionLog();
            commissionLog.setOrderNo(ysWithdraw.getOutTradeNo());
            commissionLog.setMoney(ysWithdraw.getTotalAmount());
            commissionLog.setBalance(customerExtend.getBalance());
            commissionLog.setCommissionType(CommissionType.getMoney.getCode());

            commissionLog.setSelfCode(customerExtend.getRandomCode());
            commissionLog.setParentCode(customerExtend.getInviteCode());
            commissionLog.setLevelIndex(VipType.getIndexByCode(customerExtend.getLevel()));
            commissionLog.setCreateId(userId);
            commissionLogList.add(commissionLog);
        }
        if (commissionLogList!=null && commissionLogList.size()>0){
            commissionLogRepository.save(commissionLogList);
        }

        return ServerResponse.success();
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
