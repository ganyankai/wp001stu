package cn.dante.book.util;

import java.util.Date;
import java.util.Map;

public interface Claims extends Map<String, Object>, ClaimsMutator<Claims> {
    String ISSUER = "iss";
    String SUBJECT = "sub";
    String AUDIENCE = "aud";
    String EXPIRATION = "exp";
    String NOT_BEFORE = "nbf";
    String ISSUED_AT = "iat";
    String ID = "jti";

    String getIssuer();

    Claims setIssuer(String var1);

    String getSubject();

    Claims setSubject(String var1);

    String getAudience();

    Claims setAudience(String var1);

    Date getExpiration();

    Claims setExpiration(Date var1);

    Date getNotBefore();

    Claims setNotBefore(Date var1);

    Date getIssuedAt();

    Claims setIssuedAt(Date var1);

    String getId();

    Claims setId(String var1);

    <T> T get(String var1, Class<T> var2);
}
