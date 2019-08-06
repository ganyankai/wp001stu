package cn.dante.book;

import java.util.regex.Pattern;

public final class UicodeBackslashU {
	// 单个字符的正则表达式
	private static final String singlePattern = "[0-9|a-f|A-F]";
	// 4个字符的正则表达式
	private static final String pattern = singlePattern + singlePattern +
			singlePattern + singlePattern;
	

	
	/**
	 * 把 \\u 开头的单字转成汉字，如 \\u6B65 ->　步
	 * @param str
	 * @return
	 */
	private static String ustartToCn(final String str) {
		StringBuilder sb = new StringBuilder().append("0x")
				.append(str.substring(2, 6));
		Integer codeInteger = Integer.decode(sb.toString());
		int code = codeInteger.intValue();
		char c = (char)code;
		return String.valueOf(c);
	}
	
	/**
	 * 字符串是否以Unicode字符开头。约定Unicode字符以 \\u开头。
	 * @param str 字符串
	 * @return true表示以Unicode字符开头.
	 */
	private static boolean isStartWithUnicode(final String str) {
		if (null == str || str.length() == 0) {
			return false;
		}
		if (!str.startsWith("\\u")) {
			return false;
		}
		// \u6B65
		if (str.length() < 6) {
			return false;
		}
		String content = str.substring(2, 6);
		
		boolean isMatch = Pattern.matches(pattern, content);
		return isMatch;
	}
	
	/**
	 * 字符串中，所有以 \\u 开头的UNICODE字符串，全部替换成汉字
	 * @param str
	 * @return
	 */
	public static String unicodeToCn(final String str) {
		// 用于构建新的字符串
		StringBuilder sb = new StringBuilder();
		// 从左向右扫描字符串。tmpStr是还没有被扫描的剩余字符串。
		// 下面有两个判断分支：
		// 1. 如果剩余字符串是Unicode字符开头，就把Unicode转换成汉字，加到StringBuilder中。然后跳过这个Unicode字符。
		// 2.反之， 如果剩余字符串不是Unicode字符开头，把普通字符加入StringBuilder，向右跳过1.
		int length = str.length();
		for (int i = 0; i < length;) {
			String tmpStr = str.substring(i);
			if (isStartWithUnicode(tmpStr)) { // 分支1
				sb.append(ustartToCn(tmpStr));
				i += 6;
			} else { // 分支2
				sb.append(str.substring(i, i + 1));
				i++;
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
//		String str = "\u6cb9\u5c0f\u4e8c\u540e\u53f0\u7ba1\u7406\u7cfb\u7edf";
//		String s = unicodeToCn(str);
//		System.out.println(s);

//		String str = "\\u6cb9";
//		String s = ustartToCn(str);
//		System.out.println(s);
//		System.out.println("0x6cb9".toString());
//		System.out.println(Integer.decode("0x6cb9"));
//		System.out.println(Integer.decode("0x7518").intValue());
//		char c = (char)Integer.decode("0x7518").intValue();
//		System.out.println(c);
//
////		System.out.println((int)"甘".toCharArray()[0]);
////		System.out.println(new ExampleUnitTest().byteToHex(29976));
//		String s = numToHex16(29976);
//		System.out.println(s);
		String s = unicodeToCn("\\u64CD\\u4F5C\\u6210\\u529F");
		System.out.println(s);

	}

	public static String numToHex16(int b) {
		return String.format("%02x", 29976);
	}
}
