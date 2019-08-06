package cn.dante.book;

import cn.dante.book.constant.Constant;
//import cn.dante.book.util.Jwts;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultJwtParser;
import org.junit.Test;

import java.util.Date;

public class AllTest {
    @Test
    public void test3() throws Exception{
        String str = "\\u6cb9\\u5c0f\\u4e8c\\u540e\\u53f0\\u7ba1\\u7406\\u7cfb\\u7edf";
//        String str2 = new String(str.getBytes("utf-8"));

//        System.out.println(str2);
    }

    @Test
    public void test2(){
//        Jwts.parser();
        String jwttoken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyNyIsInJvbGVzIjoiY3VzdG9tZXIiLCJpYXQiOjE1NjM4NjkxMDR9.VcJCud8LbN-YWTywcWBPkAs7zs1f2Jnx1Ben8JWI5-E";
        Jwt jwt = new DefaultJwtParser().parse(jwttoken);
        System.out.println(jwt.getBody());
    }

    @Test
    public void test(){
//        String jwtToken = Jwts.builder().setId(1).setIssuer(Constant.ISS_URL)
//                .setSubject(134).claim("roles", userExtendInfo.getRole("test")).setIssuedAt(new Date())
//                .signWith(SignatureAlgorithm.HS256, Constant.PUBLIC_KEY).compact();
        System.out.println(134);
    }

}
