package com.ijustyce.fastandroiddev.greendaojava;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangchun on 16/4/19.  测试
 */
public class test {

    public static void main(String[] args){

        System.out.println(deleteNoNumber("v 0 哈哈. —— 999"));
        System.out.println(deleteNoInt("v 0 哈哈. —— 999"));

        System.out.println(deleteNoNumber("v0.9.5.4"));
        System.out.println(deleteNoInt("v09.5.4."));

        System.out.println(deleteNoInt("v0.9.5.4"));
        System.out.println(deleteNoNumber("v09.5.4."));

        System.out.println(deleteNoNumber("v1.0.3.2beta"));
        System.out.println(deleteNoInt("v1.0.3.2beta"));

        System.out.println(deleteNoNumber("v1.0.3.2beta2"));
        System.out.println(deleteNoInt("v1.0.3.2beta2"));
    }

    /**
     * 删除不是0-9 以及. 的内容
     * @param value 原始字符串
     * @return  删除不是0-9以及.剩余的部分
     */
    public static String deleteNoNumber(String value){

        Pattern p = Pattern
                .compile("[^0-9.]");
        Matcher m = p.matcher(value);
        return m.replaceAll("").trim();
    }

    /**
     * 删除不是0-9 的内容
     * @param value 原始字符串
     * @return  删除不是0-9 剩余的部分
     */
    public static String deleteNoInt(String value){

        Pattern p = Pattern
                .compile("[^0-9]");
        Matcher m = p.matcher(value);
        return m.replaceAll("").trim();
    }
}
