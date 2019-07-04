import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.Test;
import org.springframework.util.DigestUtils;
import sun.security.provider.MD5;

public class Html5 {
    // my-500688375
    @Test
    public void test6(){
        //ok
        //        https://mta.qq.com/h5/api/ctr_page_land
        String secret = "9e1b175737865611c99e06452783086f";
        Integer app_id = 500688375;
        //        String sign= secret+"app_id=500688375idx=pv";
        //        String sign= secret+"app_id=500688196page=1";
//        String sign= secret+"app_id=500688196&urls=127.0.0.1:8020/test/yemian1.html&start_date=2019-6-26&end_date=2019-6-27";
        String sign= secret+"app_id=500688375end_date=2019-6-27start_date=2019-6-26urls=/c:/users/lenovo/desktop/h5test-my.html";

        String signSecret = DigestUtils.md5DigestAsHex(sign.getBytes());
        System.out.println(signSecret);


    }

    //入口页面8408
//    var real_url = 'https://mta.qq.com/h5/api/ctr_page_land?app_id=500688408&urls=http://127.0.0.1:8020/test/yemian1.html&start_date=2019-6-26&end_date=2019-6-27';
    @Test
    public void test5(){
        //ok
        //        https://mta.qq.com/h5/api/ctr_page_land
        String secret = "512ae468095f59b5aea6168d0cf83377";
        Integer app_id = 500688196;
        //        String sign= secret+"app_id=500688375idx=pv";
        //        String sign= secret+"app_id=500688196page=1";
//        String sign= secret+"app_id=500688196&urls=127.0.0.1:8020/test/yemian1.html&start_date=2019-6-26&end_date=2019-6-27";
        String sign= secret+"app_id=500688196end_date=2019-6-27start_date=2019-6-26urls=127.0.0.1:8020/test/yemian1.html";

        String signSecret = DigestUtils.md5DigestAsHex(sign.getBytes());
        System.out.println(signSecret);


    }

    //入口页面
//    var real_url = 'https://mta.qq.com/h5/api/ctr_page_land?app_id=500688408&urls=http://127.0.0.1:8020/test/yemian1.html&start_date=2019-6-26&end_date=2019-6-27';
    @Test
    public void test4(){
        //ok
    //        https://mta.qq.com/h5/api/ctr_page_land
        String secret = "512ae468095f59b5aea6168d0cf83377";
        Integer app_id = 500688196;
    //        String sign= secret+"app_id=500688375idx=pv";
    //        String sign= secret+"app_id=500688196page=1";
//        String sign= secret+"app_id=500688196&urls=127.0.0.1:8020/test/yemian1.html&start_date=2019-6-26&end_date=2019-6-27";
        String sign= secret+"app_id=500688196end_date=2019-6-27start_date=2019-6-26urls=127.0.0.1:8020/test/yemian1.html";

        String signSecret = DigestUtils.md5DigestAsHex(sign.getBytes());
        System.out.println(signSecret);


    }

    @Test
    public void test3(){
        //ok

//        https://mta.qq.com/h5/api/ctr_page_land
        String secret = "512ae468095f59b5aea6168d0cf83377";
        Integer app_id = 500688196;
//        String sign= secret+"app_id=500688375idx=pv";
//        String sign= secret+"app_id=500688196page=1";
        String sign= secret+"app_id=500688196page=1";

        String signSecret = DigestUtils.md5DigestAsHex(sign.getBytes());
        System.out.println(signSecret);


    }

    @Test
    public void test1(){
        //ok
        String secret = "9e1b175737865611c99e06452783086f";
        Integer app_id = 500688375;
//        String sign= secret+"app_id=500688375idx=pv";
        String sign= secret+"app_id=500688375page=1";


        String signSecret = DigestUtils.md5DigestAsHex(sign.getBytes());
        System.out.println(signSecret);


    }

    @Test
    public void test2(){
        //ok
        String secret = "512ae468095f59b5aea6168d0cf83377";
        Integer app_id = 500688196;
//        String sign= secret+"app_id=500688375idx=pv";
        String sign= secret+"app_id=500688196page=1";


        String signSecret = DigestUtils.md5DigestAsHex(sign.getBytes());
        System.out.println(signSecret);


    }


}
