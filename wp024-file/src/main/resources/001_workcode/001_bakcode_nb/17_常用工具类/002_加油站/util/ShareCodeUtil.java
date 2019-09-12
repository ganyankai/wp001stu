package util;

import org.springframework.util.DigestUtils;

import java.util.Random;
import java.util.UUID;

/**
 * 邀请码生成器，算法原理：<br/>
 * 1) 获取id: 1127738 <br/>
 * 2) 使用自定义进制转为：gpm6 <br/>
 * 3) 转为字符串，并在后面加'o'字符：gpm6o <br/>
 * 4）在后面随机产生若干个随机数字字符：gpm6o7 <br/>
 * 转为自定义进制后就不会出现o这个字符，然后在后面加个'o'，这样就能确定唯一性。最后在后面产生一些随机字符进行补全。<br/>
 * @author houhuateng
 */
public class ShareCodeUtil {

    /** 自定义进制(0,1没有加入,容易与o,l混淆) */
    private static final char[] r = new char[]{'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p', '5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h'};


    /** (不能与自定义进制有重复) */
    private static final char b = 'o';

    /** 进制长度 */
    private static final int binLen = r.length;

    /** 序列最小长度 */
    private static int minLen = 6;

    /**
     * 根据ID生成*位随机码
     * @param id ID
     * @param format 位数
     * @return 随机码
     */
    public static String toSerialCode(long id, int format){
        char[] buf = new char[32];
        int charPos = 32;

        int num =0 ;
        while((id / binLen) > 0) {
            int ind = (int)(id % binLen);
            //System.out.println(num + "-->" + ind);
            buf[--charPos] = r[ind];
            id /= binLen;
        }
        buf[--charPos] = r[(int)(id % binLen)];
        //System.out.println(num + "-->" + num % binLen);
        String str = new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if(str.length() < format) {
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            Random rnd = new Random();
            for(int i = 1; i < format - str.length(); i++) {
                sb.append(r[rnd.nextInt(binLen)]);
            }
            str+=sb.toString();
        }
        return str;
    }

    /**
     * 根据ID生成六位随机码
     * @param id ID
     * @return 随机码
     */
    public static String toSerialCode(long id) {
        return ShareCodeUtil.toSerialCode(id, minLen);
    }

    /**
     * 邀请码解码
     * */
    public static long codeToId(String code) {
        char chs[] = code.toCharArray();
        long res = 0L;
        for(int i = 0; i < chs.length; i++) {
            int ind = 0;
            for(int j = 0; j < binLen; j++) {
                if(chs[i] == r[j]) {
                    ind = j;
                    break;
                }
            }
            if(chs[i] == b) {
                break;
            }
            if(i > 0) {
                res=res * binLen + ind;
            }else{
                res = ind;
            }
            // System.out.println(ind + "-->" + res);
        }
        return res;
    }

    public static void main(String[] args) {
//        System.out.println(ShareCodeUtil.toSerialCode(2l+15559900000000003l, 15));
//        for (int i= 3; i<20; i++){
//            System.out.println(ShareCodeUtil.toSerialCode(3).toUpperCase());
//        }
//        byte[] sb = "cv64fc3".getBytes();
        DigestUtils.md5DigestAsHex("admin".getBytes());
        System.out.println(CryptUtil.encryptDatabase("wxe3d23a8baa8c8959"));
//        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
//        System.out.println(CryptUtil.encryptDatabase("d4b46d45c17edbb852661543afd7ef77"));
//        System.out.println(CryptUtil.decryptDatabase("5F2A27FD41224B54BE1DB4E338EC26AA"));
        byte[] a = new byte[2];
        int aa = Integer.parseInt("3200", 16);
        a[0]=(byte)(aa&0xff); //获得低位字节
        a[1]=(byte)(aa>>>8);//获得高位字节
        int i = (a[0] << 8) | a[1];
         int value = 6263;
         int big = (value & 0xFF00) >> 8;
         int little = value & 0xFF;
        System.out.println(CryptUtil.encryptDatabase("wxe3d23a8baa8c8959"));
    }
}
