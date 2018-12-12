package com.dh.commonutilslib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {
    /**
     * 验证密码，数字，字母以及符号至少两种
     *
     * @param str
     * @return true：满足条件 false：不满足
     */
    public static boolean checkPassword(String str) {
        String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)(?!([^(0-9a-zA-Z)]|[\\(\\)])+$)([^(0-9a-zA-Z)]|[\\(\\)]|[a-zA-Z]|[0-9]){8,32}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String regMatch(String source, String rgex) {
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(source);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

}
