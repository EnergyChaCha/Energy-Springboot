package com.chacha.energy.common.util;

public class MaskingUtil {

    public static String maskLoginId(String loginId) {
        if(loginId.length() < 3) return loginId;

        return loginId.substring(0, 3)+"**";
    }

    public static String maskName(String name) {
        if (name.length() == 1) return name;
        if (name.length() == 2) return name.charAt(0) + "*";
        return name.charAt(0) + "*" + name.charAt(name.length() - 1);
    }

    public static String maskPhone(String phone) {
        if(phone.length() < 10) return phone;

        return phone.substring(0, 4)+"****"+phone.substring(phone.length()-5);
    }
}
