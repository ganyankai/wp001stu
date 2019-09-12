package com.zrytech.framework.newshop.dto.appDTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CustomerExtendUpdateDto {
    @NotNull(message = "客服id不能为空为空")
    private Integer userServiceId;

    @NotNull(message = "客户id集合不能为空")
    @Size(min = 1, message = "至少有一个客户id")
    private List<Integer> idsList;
}
