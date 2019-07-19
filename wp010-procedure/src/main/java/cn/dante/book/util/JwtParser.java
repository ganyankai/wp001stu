//package cn.dante.book.util;
//
//import java.util.Date;
//
//public interface JwtParser {
//    char SEPARATOR_CHAR = '.';
//
//    JwtParser requireId(String var1);
//
//    JwtParser requireSubject(String var1);
//
//    JwtParser requireAudience(String var1);
//
//    JwtParser requireIssuer(String var1);
//
//    JwtParser requireIssuedAt(Date var1);
//
//    JwtParser requireExpiration(Date var1);
//
//    JwtParser requireNotBefore(Date var1);
//
//    JwtParser require(String var1, Object var2);
//
//    JwtParser setSigningKey(byte[] var1);
//
//    JwtParser setSigningKey(String var1);
//
//    JwtParser setSigningKey(Key var1);
//
//    JwtParser setSigningKeyResolver(SigningKeyResolver var1);
//
//    JwtParser setCompressionCodecResolver(CompressionCodecResolver var1);
//
//    boolean isSigned(String var1);
//
//    Jwt parse(String var1) throws ExpiredJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;
//
//    <T> T parse(String var1, JwtHandler<T> var2) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;
//
//    Jwt<Header, String> parsePlaintextJwt(String var1) throws UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;
//
//    Jwt<Header, Claims> parseClaimsJwt(String var1) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;
//
//    Jws<String> parsePlaintextJws(String var1) throws UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;
//
//    Jws<Claims> parseClaimsJws(String var1) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;
//}