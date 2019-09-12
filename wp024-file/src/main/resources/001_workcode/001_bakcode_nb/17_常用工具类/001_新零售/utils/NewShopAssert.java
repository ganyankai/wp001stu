package com.zrytech.framework.newshop.utils;

import com.zrytech.framework.base.enums.BaseResult;
import com.zrytech.framework.base.enums.BaseResultEnum;
import com.zrytech.framework.base.exception.BusinessException;
import com.zrytech.framework.common.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class NewShopAssert {


	/**
	 * @Desinition：校验数据不为空和''
	 * @author:qufei
	 * @return:ServerResponse
	 */
	public static void isNotBlank(Object src,String msg) {
		ResultEnum resultEnum = ResultEnum.PARAMETER_ERROR;
		if(StringUtils.isEmpty(msg)){
			msg=resultEnum.getMsg();
		}
		if(src == null) {
			throw new BusinessException(resultEnum.getCode(),msg);
		}
		if(src.getClass().isArray()){
			Object[] bb=(Object[])src;
			if(bb.length==0){
				throw new BusinessException(resultEnum.getCode(),msg);
			}
		}
		if(src.getClass().getName().equals(String.class.getName())) {
			if(StringUtils.isBlank(src.toString())){
				throw new BusinessException(resultEnum.getCode(),msg);
			}
		}
	}

	public static void isNotNull(Object src, BaseResultEnum baseResultEnum) {
		if(src == null) {
			throw new BusinessException(new BaseResult(baseResultEnum));
		}
		if(src.getClass().getName().equals(String.class.getName())) {
			if(StringUtils.isBlank(src.toString())){
				throw new BusinessException(new BaseResult(baseResultEnum));
			}
		}
	}




	/**
	 * @Desinition：校验数据库操作
	 * @author:qufei
	 * @return:ServerResponse
	 */
	public static void sqlIsSuccess(int src) {
		ResultEnum resultEnum = ResultEnum.FAIL;
		if(src < 1) {
			throw new BusinessException(resultEnum.getCode(), resultEnum.getMsg());
		}
	}


	/**
	 * @Desinition：校验List
	 * @author:qufei
	 * @return:ServerResponse
	 */
	public static Boolean listIsNotBlank(List list) {
		if(list != null && list.size() > 0) {
			return  true;
		}else{
			return  false;
		}
	}



















	/**
	 * @Desinition：校验List
	 * @author:qufei
	 * @return:ServerResponse
	 */
	public static void listIsNotBlank(List list, String msg) {
		if(list == null || list.size() ==0) {
	//		throw new BusinessException(BaseResultEnum.LIST_IS_NULL.getCode(), msg);
		}
	}









	/**
	 * @Desinition：校验数据不为空和''
	 * @author:qufei
	 * @return:ServerResponse
	 */
	public static void LoginNotBlank(Object src) {
		BaseResultEnum msg = BaseResultEnum.VERIFCODE_ERROR;
		if(src == null) {
			throw new BusinessException(msg.getCode(), msg.getMsg());
		}
		if(src.getClass().getName().equals(String.class.getName())) {
			if(StringUtils.isBlank(src.toString())){
				throw new BusinessException(msg.getCode(), msg.getMsg());
			}
		}
	}




	/**
	 * @Desinition：校验电话
	 * @author:qufei
	 * @return:ServerResponse
	 */
	public static void isPhone(String phone) {
		ResultEnum resultEnum = ResultEnum.TEL_ERROR;
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		if (phone.length() != 11) {
			throw new BusinessException(resultEnum.getCode(), resultEnum.getMsg());
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(phone);
			boolean isMatch = m.matches();
			if (!isMatch) {
				throw new BusinessException(resultEnum.getCode(), resultEnum.getMsg());
			}
		}
	}

//	/**
//	 * @Desinition：校验邮箱
//	 * @author:qufei
//	 * @return:ServerResponse
//	 */
//	public static void isEmail(String string) {
//		ResultEnum msg = ResultEnum.EMIAL_ERROR;
//		if (string == null)
//			throw new BusinessException(msg.getCode(), msg.getMsg());
//			String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
//			Pattern p;
//			Matcher m;
//			p = Pattern.compile(regEx1);
//			m = p.matcher(string);
//			if (!m.matches()){
//				throw new BusinessException(msg.getCode(), msg.getMsg());
//			}
//		}
//
//
//	/**
//	 * @Desinition：校验身份证
//	 * @author:qufei
//	 * @return:ServerResponse
//	 */
//	public static void isIDNumber(String IDNumber) {
//		ResultEnum msg = ResultEnum.IDCARD_ERROR;
//		if (IDNumber == null || "".equals(IDNumber)) {
//			throw new BusinessException(msg.getCode(), msg.getMsg());
//		}
//		String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
//				"(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
//		boolean matches = IDNumber.matches(regularExpression);
//		if (matches) {
//
//			if (IDNumber.length() == 18) {
//				try {
//					char[] charArray = IDNumber.toCharArray();
//					int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
//					String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
//					int sum = 0;
//					for (int i = 0; i < idCardWi.length; i++) {
//						int current = Integer.parseInt(String.valueOf(charArray[i]));
//						int count = current * idCardWi[i];
//						sum += count;
//					}
//					char idCardLast = charArray[17];
//					int idCardMod = sum % 11;
//					if (!idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
//						throw new BusinessException(msg.getCode(), msg.getMsg());
//					}
//				} catch (Exception e) {
//					throw new BusinessException(msg.getCode(), msg.getMsg());
//				}
//			}
//
//		}else {
//			throw new BusinessException(msg.getCode(), msg.getMsg());
//		}
//	}

	//判断该对象是否: 返回ture表示所有属性为null  返回false表示不是所有属性都是null
	public static boolean isAllFieldNull(Object obj) throws Exception{
		Class stuCla = (Class) obj.getClass();// 得到类对象
		Field[] fs = stuCla.getDeclaredFields();//得到属性集合
		boolean flag = true;
		for (Field f : fs) {//遍历属性
			f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
			Object val = f.get(obj);// 得到此属性的值
			if(val!=null) {//只要有1个属性不为空,那么就不是所有的属性值都为空
				flag = false;
				break;
			}
		}
		return flag;
	}

}
