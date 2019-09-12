package com.zrytech.framework.newshop.utils;

import com.zrytech.framework.app.entity.AgentProduct;
import com.zrytech.framework.app.entity.TbIndent;
import com.zrytech.framework.app.entity.TbIndentInfo;
import com.zrytech.framework.app.entity.TbProductStock;
import com.zrytech.framework.app.repository.TbIndentInfoRepository;
import com.zrytech.framework.app.repository.TbIndentRepository;
import com.zrytech.framework.app.repository.TbProductStockRepository;
import com.zrytech.framework.base.exception.BusinessException;
import com.zrytech.framework.newshop.repository.AgentProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryUtil {

    @Autowired
    private static AgentProductRepository agentProductRepository;

    @Autowired
    private static TbIndentInfoRepository tbIndentInfoRepository;

    @Autowired
    private static TbProductStockRepository tbProductStockRepository;

    public static void updateIncentory(TbIndent tbIndent, Integer i) {
        //根据订单查询商品信息
        List<TbIndentInfo> tbIndentInfos = tbIndentInfoRepository.findByIndentId(tbIndent.getId());
        if (tbIndentInfos == null && tbIndentInfos.size() <= 0) {
            throw new BusinessException(121, "订单错误");
        }
        for (TbIndentInfo tbIndentInfo : tbIndentInfos) {
            AgentProduct agentProduct = agentProductRepository.findOne(tbIndentInfo.getProductId());
            if (agentProduct == null) {
                throw new BusinessException(121, "商品有误");
            }
            TbProductStock tbProductStock = tbProductStockRepository.findOne(agentProduct.getSkuId());
            if (tbProductStock == null) {
                throw new BusinessException(121, "商品信息有误");
            }

            Integer qty = tbIndentInfo.getQty();

            //商品的实际库存
            Integer agentSNumber = agentProduct.getRealQty();
            //商品的锁定库存
            Integer agentRNumber = agentProduct.getRealQty();

            if (agentSNumber - agentRNumber < qty) {
                throw new BusinessException(121, "商品已售馨");
            }
            //下单
            if (i == 1) {
                //Integer inventory = agentProduct.getRealQty();
                tbProductStock.setLockStock(agentRNumber + qty);
                agentProduct.setLockQty(agentRNumber + qty);
                //支付
            } else if (i == 2) {
                tbProductStock.setRealStock(agentRNumber - qty);
                tbProductStock.setLockStock(agentRNumber - qty);

                agentProduct.setLockQty(agentRNumber - qty);
                agentProduct.setRealQty(agentRNumber - qty);
                //退款
            } else if (i == 3) {
                tbProductStock.setRealStock(agentRNumber + qty);
                agentProduct.setLockQty(agentRNumber + qty);
            }
            //修改商品库存
            agentProductRepository.save(agentProduct);
            tbProductStockRepository.save(tbProductStock);
        }
    }
}
