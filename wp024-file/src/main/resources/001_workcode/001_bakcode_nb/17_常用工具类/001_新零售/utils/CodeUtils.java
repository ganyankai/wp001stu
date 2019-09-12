package com.zrytech.framework.newshop.utils;


import com.zrytech.framework.app.constants.Constant;
import com.zrytech.framework.newshop.contants.NewShopConstant;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @Desinition:随机码生成工具
 * @Author:Jxx
 */
public class CodeUtils {

    public String createSoNumber() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        Date now2 = new Date();
        // 2192 4148 0433 7503
        long a = getPK();
        return simpleDateFormat.format(now2)
                + (String.valueOf(a)).substring(3, 13);
    }

    /**
     * @Deinition:随机生成8位邀请码
     * @param:id主键ID
     */
    public static String getCodeInvicate(Integer id) {
        String currentTimeMillisStr = String.valueOf(System.currentTimeMillis()); //获取当前毫秒值的后六位数  例如 123456
        currentTimeMillisStr = currentTimeMillisStr.substring(currentTimeMillisStr.length() - 6);
        return currentTimeMillisStr + checkId(id, 2);
    }

    /**
     * @Deinition:
     * @param:
     */
    public static String getCodeName() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return NewShopConstant.NICHEN_PROFIX + uuid.substring(0, 5);
    }

    public static String checkId(int id, int width) {
        String idStr = String.valueOf(id);
        int length = idStr.length();
        if (length > width) {
            return getWidhtCode(idStr, width);
        }
        if (length == width) {
            return idStr;
        }
        int padded = width - length;
        for (int i = 1; i <= padded; i++) {
            idStr = "0" + idStr;
        }
        return idStr;
    }

    public static String getWidhtCode(String str, int width) {
        String[] arr = str.split("");
        arr[0] = arr[arr.length - 1];
        arr[arr.length - 1] = arr[0];
        return appendStr(arr, width);
    }

    public static String appendStr(String[] arr, int width) {
        StringBuilder builder = new StringBuilder();
        for (String str : arr) {
            builder.append(str);
        }
        return builder.toString().trim().substring(0, width);
    }

    /**
     * @Deinition:随机生成20位卡号
     */
    public static String autoRandom() {
        String numStr = "";
        String trandStr = String.valueOf((Math.random() * 9 + 1) * 10000);
        String dataStr = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
        numStr = trandStr.toString().substring(0, 5);
        numStr = numStr + dataStr;
        if (numStr.length() < 16) {
            int num = 16 - numStr.length();
            for (int i = 1; i <= num; i++) {
                numStr = numStr + i;
            }
        } else if ((numStr.length() > 16)) {
            return numStr.substring(0, 16);
        }
        return numStr;
    }

    private long[] ls = new long[3000];
    private int li = 0;

    public synchronized long getPK() {
        long lo = getPrivateKey();
        for (int i = 0; i < 3000; i++) {
            long lt = ls[i];
            if (lt == lo) {
                lo = getPK();
                break;
            }
        }
        ls[li] = lo;
        li++;
        if (li == 3000) {
            li = 0;
        }
        return lo;
    }
    private long getPrivateKey() {

        String a = (String.valueOf(System.currentTimeMillis()))
                .substring(3, 13);
        if (StringUtils.startsWithIgnoreCase(a, "0")) {
            Random random = new Random(System.currentTimeMillis());
            int ranNum = random.nextInt(9) + 1;
            a = ranNum + a.substring(1);
        }
        String d = (String.valueOf(Math.random())).substring(2, 8);
        return Long.parseLong(a + d);
    }

}
