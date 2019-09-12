package com.zrytech.framework.newshop.utils.weixin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class SnsCode2Session {

	private String openid;
	
	/**会话密钥*/
	@JSONField(name="session_key")
	private String sessionKey;
	
	private String unionid;
	
	/**错误码*/
	private String errcode;
	
	/**错误信息*/
	private String errmsg;
	
	@JSONField(name="expires_in")
	private Integer expiresIn;
	
}
