package com.zrytech.framework.newshop.utils.weixin;

import lombok.Data;

@Data
public class SnsAuthDto {

	private String encryptedData;     //密文

	private String iv;

	private String code;

	private Integer type;

	private Integer userId;
	
}
