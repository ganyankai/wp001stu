package cn.dante.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2019/4/25 11:46
 * @atuther wangbo
 */
public class JwtUtil {

    //密钥
    public static final String SECRET = "sdjhakdhajdklsl;o653632";
    //过期时间:秒
    public static final int EXPIRE = 3600;

    public static final String PUBLIC_KEY = "13a3ab24f0a62d9b988bd778d12ef35a";

    /**
     * 生成Token
     * @param userId
     * @param userName
     * @return
     * @throws Exception
     */
    public static String createToken(String userId, String userName) throws Exception {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, EXPIRE);
        Date expireDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create()
                .withHeader(map)//头
                .withClaim("userId", userId)
                .withClaim("userName", userName)
                .withClaim("age",String.valueOf(16))
                .withSubject("测试")//
                .withIssuedAt(new Date())//签名时间
                .withExpiresAt(expireDate)//过期时间
                .sign(Algorithm.HMAC256(SECRET));//签名
        return token;
    }

    /**
     * 验证Token
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token)throws Exception{
//        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(PUBLIC_KEY)).build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("凭证已过期，请重新登录");
        }
        return jwt.getClaims();
    }

    /**
     * 解析Token
     * @param token
     * @return
     */
    public static Map<String, Claim> parseToken(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaims();
    }

    public static void main(String[] args) {
        try {
            String token = JwtUtil.createToken("123", "dante");
            System.out.println("token=" + token);
            //Thread.sleep(5000);
//            token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiLmtYvor5UiLCJ1c2VyTmFtZSI6ImRhbnRlIiwiZXhwIjoxNTYzODcxMDA5LCJ1c2VySWQiOiIxMjMiLCJpYXQiOjE1NjM4Njc0MDksImFnZSI6IjE2In0.av_r5v6zWqEOEsAgh5Mk5YvY8AhmAIm8iTs22Wa2HYM";
            token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyNyIsInJvbGVzIjoiY3VzdG9tZXIiLCJpYXQiOjE1NjM4NjkxMDR9.VcJCud8LbN-YWTywcWBPkAs7zs1f2Jnx1Ben8JWI5-E";
            Map<String, Claim> map = JwtUtil.verifyToken(token);
            //Map<String, Claim> map = JwtUtil.parseToken(token);
            //遍历
            for (Map.Entry<String, Claim> entry : map.entrySet()) {
                if (entry.getValue().asString() != null) {
                    System.out.println(entry.getKey() + "===" + entry.getValue().asString());
                } else {
                    System.out.println(entry.getKey() + "===" + entry.getValue().asDate());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}