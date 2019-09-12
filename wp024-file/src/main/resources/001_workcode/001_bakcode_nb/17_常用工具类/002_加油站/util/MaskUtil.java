package util;

/**
 * 字符串遮掩工具类
 * 
 * @author 纪建宏
 *
 */
public class MaskUtil {

	/**
	 * 遮掩姓名
	 * 
	 * @param name
	 * @return
	 */
	public static String maskName(String name) {
		return mask(name, 0, 1);
	}

	/**
	 * 遮掩手机号
	 * 
	 * @param mobileNo
	 * @return
	 */
	public static String maskMobileNo(String mobileNo) {
		return mask(mobileNo, 3, 4);
	}

	/**
	 * 遮掩身份证号
	 * 
	 * @param certNo
	 * @return
	 */
	public static String maskCertNo(String certNo) {
		return mask(certNo, 2, 2);
	}

	/**
	 * 遮掩银行卡号
	 * 
	 * @param bankCard
	 * @return
	 */
	public static String maskBankCard(String bankCard) {
		return mask(bankCard, 2, 2);
	}

	/**
	 * 用户统一遮掩（abc123 给为 a***7）
	 *
	 * @param name
	 * @return
	 */
	public static String maskNickName(String name) {
		if (name != null && name.length() > 1) {
			name = name.substring(0, name.length() - 1)
					+ name.substring(name.length() - 1);
			name = name.substring(0, 1) + "***"
					+ name.substring(name.length() - 1);
		}
		return name;
	}

	/**
	 * 遮掩字符串
	 * 
	 * @param src
	 *            源字符串
	 * @param leadCount
	 *            开头保留字符个数
	 * @param tailCount
	 *            末尾保留字符个数
	 * @return
	 */
	private static String mask(String src, int leadCount, int tailCount) {
		int totalCount = leadCount + tailCount;
		if (src != null && src.length() > totalCount) {
			String lead = src.substring(0, leadCount);
			String middle = src.substring(leadCount, src.length() - tailCount)
					.replaceAll(".", "*");
			String tail = src.substring(src.length() - tailCount);
			src = lead + middle + tail;
		}

		return src;
	}

}
