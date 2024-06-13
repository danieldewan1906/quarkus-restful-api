package org.learn.core;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

public class StringUtils {
    private static final String ALLOWED_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGRHIJKLMNOPQRSTUVWXYZ";
    private static final String ORDER_NO = "abcdefghijklmnopqrstuvwxyz123456789";

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

    public static String randomString(Integer length, String firstLetter) {
        Random random = new Random();
        StringBuilder strings = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (firstLetter != null) {
                strings.append(firstLetter + ORDER_NO.charAt(random.nextInt(ORDER_NO.length())));
            } else {
                strings.append(ALLOWED_CHAR.charAt(random.nextInt(ALLOWED_CHAR.length())));
            }
        }
        return strings.toString();
    }
}
