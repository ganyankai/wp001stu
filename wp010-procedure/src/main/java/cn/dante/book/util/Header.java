//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.dante.book.util;

import java.util.Map;

public interface Header<T extends Header<T>> extends Map<String, Object> {
    String JWT_TYPE = "JWT";
    String TYPE = "typ";
    String CONTENT_TYPE = "cty";
    String COMPRESSION_ALGORITHM = "calg";

    String getType();

    T setType(String var1);

    String getContentType();

    T setContentType(String var1);

    String getCompressionAlgorithm();

    T setCompressionAlgorithm(String var1);
}
