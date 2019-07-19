package cn.dante.book;

import cn.dante.book.constant.Constant;
//import cn.dante.book.util.Jwts;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

public class AllTest {

    @Test
    public void test(){
//        String jwtToken = Jwts.builder().setId(1).setIssuer(Constant.ISS_URL)
//                .setSubject(134).claim("roles", userExtendInfo.getRole("test")).setIssuedAt(new Date())
//                .signWith(SignatureAlgorithm.HS256, Constant.PUBLIC_KEY).compact();
        System.out.println(134);
    }

}
