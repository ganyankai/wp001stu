package com.zrytech.framework.newshop.utils.weixin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;

/**
 * AES加密解密工具类
 * 
 * <p>高级加密标准（英语：Advanced Encryption Standard，缩写：AES）</p>
 * 
 */
@Slf4j
public class AESUtils {
	
	
	/**
	 * AES 解密
	 * 
	 * @param encryptedData
	 * 						密文字符串
	 * @param sessionKey
	 * 						秘钥
	 * @param iv
	 * 						初始向量
	 * @return
	 * 			当参数 encryptedData为null或者发生异常时返回null，否则返回解密后的字符串。
	 */
	public static String decrypt(final String encryptedData, final String sessionKey, final String iv) {
		String plaintext = null;
		if (encryptedData != null) {
			try {
				Cipher decipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				Key key = new SecretKeySpec(Base64.decodeBase64(sessionKey), "AES");
				decipher.init(Cipher.DECRYPT_MODE, key, generateIV(Base64.decodeBase64(iv)));
				byte[] result = decipher.doFinal(Base64.decodeBase64(encryptedData));
				plaintext = new String(result);
			} catch (Exception e) {
				log.error("AES 解密异常， {}", e.getMessage());
				// e.printStackTrace();
			}
		}
		return plaintext;
	}
	
	
	/**
	 * 生成对称解密算法初始向量
	 */
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }
	
    
    
    public static void main(String[] args) {
		String iv = "9xOnPtxipPLaUExeceIZWA==";
		String sessionKey = "rEdK\\/29ZtPxfvj4UNZLrSg==";
		String encryptedData = "H5NX+tebQebxszQW16rFr3pzLNp6VfS2mGSWpxd7dbAHprmgvyLeF6r3icF7IHeaFnNcQVFj"
				+ "MJ/rQi7volruipDNOQ7Oe0pVqOtNfq2tgCu9amy/GacuZsIKWh7w1CA9FiaOT7JAsWEBgIsnekC"
				+ "MVCznnjgDZWhQkjxhByIyMxQCfsANg8dk773KWFdet8Rb9hYMJ16uocxbRNlsnI6"
				+ "X3AUK2P3g2UZhO2jQdC8F3RyHkNdJZhjKF+qmNYLLCaN/8Bzg4MMC1+PsVFKVPWPrg5Mc8r"
				+ "MCjs0jKosXn/QjHPB0M3A16RcO07958BL8arHpKhxhSlsjuO48d62xyFIA4F5usUGTnT7plXB"
				+ "0CzFGBI3FDKJnQj8y5W8qjIAoRXyGobsnjWqfxVc/DeX7Beaoga1wi2uEmhKtcVQPw5PHs6as"
				+ "ZjZ7KrxLrCJkD4RVhqS/jC4xz/UL9tnpqUryNkjAF1pyh2vO34O5dzCjBy6NI4s=";
		String decrypt = decrypt(encryptedData , sessionKey , iv);
		System.out.println(decrypt);
	}
    
}
