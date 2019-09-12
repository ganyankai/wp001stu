package com.zrytech.framework.newshop.utils.weixin;

import lombok.Data;

@Data
public class SnsUserInfos {
	
	private String openId;
	
	private String nickName;
	
	/**性别 0：未知、1：男、2：女*/
	private Integer gender;
	
	/**城市*/
	private String city;
	
	/**省*/
	private String province;
	
	/**国家*/
	private String country;
	
	/**头像*/
	private String avatarUrl;
	
	private String unionId;

	private String userInfo;

}
