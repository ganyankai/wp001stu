package util;

import com.catt.common.util.crypto.DESUtil;

/**
 * 油小二加解密工具类
 * 
 * @author 纪建宏
 *
 */
public class CryptUtil {

	/**
	 * 数据库密钥
	 */
	private static final String KEY_DATABASE = "cattsoft.mroil";

	/**
	 * 数据库加密
	 * 
	 * @param src
	 *            明文
	 * @return 密文
	 */
	public static String encryptDatabase(String src) {
		if(src == null){
			return null;
		}
		return DESUtil.encrypt(KEY_DATABASE, src);
	}

	/**
	 * 数据库解密
	 * 
	 * @param src
	 *            密文
	 * @return 明文
	 */
	public static String decryptDatabase(String src) {
		if(src == null){
			return null;
		}
		return DESUtil.decrypt(KEY_DATABASE, src);
	}
	
	public static void main(String[] args) {
		String pwd = CryptUtil.decryptDatabase("8404DF52544A6C0D67CB4BC74EA541E7416753942B4A57E9");
		System.out.println(pwd);
		String pwd2 = CryptUtil.encryptDatabase("4d9471d4d487690f56cf6c9c3a8159c9");
		System.out.println(pwd2);
	}

}
