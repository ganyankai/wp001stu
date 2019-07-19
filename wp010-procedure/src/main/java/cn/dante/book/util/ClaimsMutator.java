package cn.dante.book.util;

import java.util.Date;

public interface ClaimsMutator<T extends ClaimsMutator> {
    T setIssuer(String var1);

    T setSubject(String var1);

    T setAudience(String var1);

    T setExpiration(Date var1);

    T setNotBefore(Date var1);

    T setIssuedAt(Date var1);

    T setId(String var1);
}