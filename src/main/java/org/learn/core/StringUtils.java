package org.learn.core;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class StringUtils {
    public static String base64Encode(String str) {
        Base64.Encoder encoder = Base64.getMimeEncoder();
        byte[] encoded = encoder.encode(str.getBytes());
        return new String(encoded);
    }

    public static String base64Decode(String str) {
        Base64.Decoder decoder = Base64.getMimeDecoder();
        byte[] decoded = decoder.decode(str.getBytes());
        return new String(decoded, StandardCharsets.UTF_8);
    }
}
