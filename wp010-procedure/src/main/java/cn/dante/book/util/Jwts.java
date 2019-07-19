////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package cn.dante.book.util;
//
//import io.jsonwebtoken.impl.DefaultClaims;
//import io.jsonwebtoken.impl.DefaultHeader;
//import io.jsonwebtoken.impl.DefaultJwsHeader;
//import io.jsonwebtoken.impl.DefaultJwtBuilder;
//import io.jsonwebtoken.impl.DefaultJwtParser;
//import java.util.Map;
//
//public final class Jwts {
//    private Jwts() {
//    }
//
//    public static Header header() {
//        return new DefaultHeader();
//    }
//
//    public static Header header(Map<String, Object> header) {
//        return new DefaultHeader(header);
//    }
//
//    public static JwsHeader jwsHeader() {
//        return new DefaultJwsHeader();
//    }
//
//    public static JwsHeader jwsHeader(Map<String, Object> header) {
//        return new DefaultJwsHeader(header);
//    }
//
//    public static Claims claims() {
//        return new DefaultClaims();
//    }
//
//    public static Claims claims(Map<String, Object> claims) {
//        return new DefaultClaims(claims);
//    }
//
//    public static JwtParser parser() {
//        return new DefaultJwtParser();
//    }
//
//    public static JwtBuilder builder() {
//        return new DefaultJwtBuilder();
//    }
//}
