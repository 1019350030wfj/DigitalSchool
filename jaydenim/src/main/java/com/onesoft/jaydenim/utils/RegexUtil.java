package com.onesoft.jaydenim.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jayden on 2016/9/24.
 */

public class RegexUtil {

    /**
     * 验证内容是否含有此表达式
     * @param reg
     * @param string
     * @return
     */
    public static boolean startCheck(String reg, String string) {
        boolean tem = false;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(string);

        tem = matcher.matches();
        return tem;
    }

    /**
     * 验证内容是否含有此表达式
     * @param reg
     * @param string
     * @return
     */
    public static String getCheck(String reg, String string) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()){
            return matcher.group(1);
        }
        return "";
    }
}
