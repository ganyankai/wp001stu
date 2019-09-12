package com.zrytech.framework.newshop.service;

import com.zrytech.framework.base.entity.ServerResponse;
import com.zrytech.framework.newshop.dto.YsWithdrawDto;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface YsWithdrawService {

    ServerResponse getYsWithdrawFront(YsWithdrawDto ysWithdrawDto, Integer pageNum, Integer pageSize);

    ServerResponse confirmRemit(List<Integer> idsList);

    /**
     * 打款页面文件导出
     * @param ysWithdrawDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    void getYsWithdrawFrontExcel(YsWithdrawDto ysWithdrawDto, Integer pageNum, Integer pageSize,HttpServletResponse response);

}
