package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 靓号判断工具类
 *
 * @author 袁幸成
 * @version 1.0
 * @date 2016-10-13 14:38:08
 */
public class GoodNumberUtil {

    private static List<String> patterns;

    static {
        patterns = new ArrayList<>();
        // 重复号码，镜子号码  
        patterns.add("^(\\d)(\\d)(\\d)\\1\\2\\3$");
        patterns.add("^(\\d)(\\d)(\\d)\\3\\2\\1$");
        // AABB
        patterns.add("^\\d*(\\d)\\1(\\d)\\2\\d*$");
        // AAABBB
        patterns.add("^\\d*(\\d)\\1\\1(\\d)\\2\\2\\d*$");
        // ABABAB
        patterns.add("^(\\d)(\\d)\\1\\2\\1\\2\\1\\2$");
        // ABCABC
        patterns.add("^(\\d)(\\d)(\\d)\\1\\2\\3$");
        // ABBABB
        patterns.add("^(\\d)(\\d)\\2\\1\\2\\2$");
        // AABAAB
        patterns.add("^(\\d)\\1(\\d)\\1\\1\\2$");
        // 4-8 位置重复
        patterns.add("^\\d*(\\d)\\1{2,}\\d*$");
        // 4位以上 位递增或者递减（7890也是递增）
        patterns.add("(?:(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)|9(?=0)){2,}|(?:0(?=9)|9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){2,})\\d");
    }

    /**
     * 靓号判断
     *
     * @param code 号码
     * @return
     */
    public static boolean checkGoodNumber(String code) {
        for (String pattern : patterns){
            Pattern pa = Pattern.compile(pattern);
            Matcher matcher = pa.matcher(code);
            if (matcher.matches()){
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        System.out.println(checkGoodNumber(123 + ""));
    }
}
