package com.ijustyce.fastandroiddev.baseLib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yc on 2015/8/14.  常用正则表达式
 */
public class RegularUtils {

    public static boolean isMobilePhone(String s){

        if (s == null){
            return false;
        }if (s.startsWith("+86")){
            s = s.replace("+86", "");
        }

        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(145)|(147)|(17[6-8]))\\d{8}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 判断是否是通用手机号，即：正常11位手机号、6位城市短号、通用固话号码
     * @param s String 字符串
     * @return
     */
    public static boolean isCommonPhone(String s){

        return isMobilePhone(s) || isFixedPhone(s) || isShortPhone(s);
    }

    public static boolean isShortPhone(String s){

        if (s == null){
            return false;
        }

        Pattern p = Pattern
                .compile("^([0-9]{6})$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean isFixedPhone(String s){

        if (s == null){
            return false;
        }

        Pattern p = Pattern.compile("^([0-9]{3,5}(-)?)?([0-9]{7,8})((-)?[0-9]{1,4})?$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean isEmail(String s){

        if (s == null){
            return false;
        }

        Pattern p = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}"
                        + "\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static String delStringBlank(String s){

        if (s == null){
            return null;
        }
        return s.replaceAll(" ", "");
    }
}
