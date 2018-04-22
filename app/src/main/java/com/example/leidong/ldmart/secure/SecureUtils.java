package com.example.leidong.ldmart.secure;

public class SecureUtils {
    /**
     * 密码是否合法
     * @param password1
     * @param password2
     * @return
     */
    public static boolean isPasswordLegal(String password1, String password2) {
        return isEqual(password1, password2)
                && isLengthEnough(password1);
    }

    /**
     * 长度是否达标
     * @param password1
     * @return
     */
    private static boolean isLengthEnough(String password1) {
        return password1.length() >= 6;
    }

    /**
     * 字符串是否相等
     * @param password1
     * @param password2
     * @return
     */
    private static boolean isEqual(String password1, String password2) {
        return password1.equals(password2);
    }
}
