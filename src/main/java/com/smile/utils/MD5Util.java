package com.smile.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author smilePlus
 * @version 1.0
 * @date 2021/3/4 17:35
 */
@Component
public class MD5Util {

    private static final String SALT = "1a2b3c4d";

    public static String md5Encode(String password) {
        return DigestUtils.md5Hex(password);
    }

    // 明文密码加密
    public static String inputPassToFormPass(String password) {
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + password + SALT.charAt(5) + SALT.charAt(4);
        return md5Encode(str);
    }
    // 第二次加密
    public static String formPassToDBPass(String pwEncoded, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + pwEncoded + salt.charAt(5) + salt.charAt(4);
        return md5Encode(str);
    }

    public static String inputPassToDBPass(String inputPass, String salt) {
        return formPassToDBPass(inputPassToFormPass(inputPass),salt);
    }

    public static void main(String[] args) {
        // d3b1294a61a07da9b49b6e22b2cbd7f9
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(formPassToDBPass("d3b1294a61a07da9b49b6e22b2cbd7f9","1a2b3c4d"));
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
    }

}
