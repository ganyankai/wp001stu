package cn.dante.book.util;

public interface JwsHeader<T extends JwsHeader<T>> extends Header<T> {
    String ALGORITHM = "alg";
    String JWK_SET_URL = "jku";
    String JSON_WEB_KEY = "jwk";
    String KEY_ID = "kid";
    String X509_URL = "x5u";
    String X509_CERT_CHAIN = "x5c";
    String X509_CERT_SHA1_THUMBPRINT = "x5t";
    String X509_CERT_SHA256_THUMBPRINT = "x5t#S256";
    String CRITICAL = "crit";

    String getAlgorithm();

    T setAlgorithm(String var1);

    String getKeyId();

    T setKeyId(String var1);
}
