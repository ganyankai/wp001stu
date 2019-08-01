package cn.dante.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {
	
	public static void main(String args[]) {
		String str = "A123456.";
//		String pattern = "^.*(?=.{6,16})(?=.*d)(?=.*[A-Za-z])(?=.*[p{Punct}]).*$";
//		String pattern = "^.*(?=.{6,16})(?=.*\\d)(?=.*[A-Za-z])(?=.*[\\p{Punct}]).*$";
//		String pattern = "^.*(?=.{6,16})(?=.*\\d)(?=.*[A-Za-z])(?=.*[\\p{Punct}]).*$";
//		String pattern = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{6,16}$";
		String pattern = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)[a-zA-Z0-9\\W]{6,16}$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		System.out.println(m.matches());
	}

}