package com.hello.demo.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Pattern;

public class StudentVerificationUtil {
    private static final String candidate = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // 정규표현식을 통해 순천향대학교 학생임을 확인합니다.
    public static boolean isStudent(String studentEmail) {
        return Pattern.matches(".+@sch.ac.kr", studentEmail);
    }

    // 최초 회원가입시 타인의 부정가입을 막기위해 인증키를 생성합니다.
    // Member에서 userId로 사용됩니다.
    public static String createKey() throws NoSuchAlgorithmException {
        String key = "";
        // Math.random()는 의사 난수를 생성하므로 보안에 취약합니다.
        // 따라서 보다 안전한 SecureRandom을 사용했습니다.
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        for (int i = 0; i < 24; i++) {
            key += candidate.charAt(secureRandom.nextInt(candidate.length()));
        }
        return key;
    }
}
