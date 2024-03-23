package com.hello.demo.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Pattern;

public class StudentVerificationUtil {
    private static final String candidate = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static boolean isStudent(String studentEmail) {
        return Pattern.matches(".+@sch.ac.kr", studentEmail);
    }

    public static String createKey() throws NoSuchAlgorithmException {
        String key = "";
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        for (int i = 0; i < 24; i++) {
            key += candidate.charAt(secureRandom.nextInt(candidate.length()));
        }
        return key;
    }
}
