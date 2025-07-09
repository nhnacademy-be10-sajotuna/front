package shop.nhnteam04.front.utils;

import org.springframework.stereotype.Component;

@Component
public class MaskingUtils {

    /**
     * 이메일 주소를 마스킹 처리합니다. (예: te***@example.com)
     * 아이디의 앞 2글자만 보여주고 나머지는 '*'로 대체합니다.
     * @param email 원본 이메일 문자열
     * @return 마스킹된 이메일 문자열
     */
    public static String maskEmail(String email) {
        int atIndex = email.indexOf("@");
        String id = email.substring(0, atIndex);
        String domain = email.substring(atIndex);

        if (id.length() <= 2) {
            return id.charAt(0) + "*" + domain; // 아이디가 2자 이하일 경우
        }

        String maskedId = id.substring(0, 2) + id.substring(2).replaceAll(".", "*");

        return maskedId + domain;
    }
}