package com.ijustyce.fastandroiddev.greendaojava;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangchun on 16/4/19.  测试
 */
public class test {

    public static void main(String[] args){

        System.out.println(isNewVersion("v1.0.5beta1", "1.0.5"));
        System.out.println(isNewVersion("v1.0.5beta1", "1.0.5beta2"));
        System.out.println(isNewVersion("v1.0.51", "1.0.5beta2"));
        System.out.println(isNewVersion("v1.0.5", "v1.0.5.1"));

        System.out.println(isNewVersion("v1.0.51", "v1.0.52"));
        System.out.println(isNewVersion("v1.0.52", "v1.0.51"));

        System.out.println(isNewVersion("v1.0.51", "1.0.52"));
        System.out.println(isNewVersion("v1.0.52", "1.0.51"));

        System.out.println(isNewVersion("1.0.51", "v1.0.52"));
        System.out.println(isNewVersion(" hahhaha1.0.52", "xixiv1.0.51"));
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
     * 判断是否为新版本
     * @param net   网络返回的版本 比如 0.5.09
     * @param local 本地版本      比如 0.5.1
     * @return  是否为新版本
     */
    public static boolean isNewVersion(String net, String local){

        net = deleteNoNumber(net);
        local = deleteNoNumber(local);
        if (isEmpty(net) || isEmpty(local)) return false;
        String[] netVersion = net.split("\\.");
        String[] localVersion = local.split("\\.");

        int size = netVersion.length > localVersion.length ? localVersion.length : netVersion.length;
        for (int i =0; i < size; i++){
            double netTmp = getDouble("0." + netVersion[i]);
            double localTmp = getDouble("0." + localVersion[i]);
            if (netTmp > localTmp){
                return true;
            }if (netTmp < localTmp){
                return false;
            }
        }
        return netVersion.length > localVersion.length; //  除非长度不一且前面每位都相等，否则不可能到这步，
    }

    public static double getDouble(String value){

        return getDouble(value, 0.0);
    }

    public static double getDouble(String value, double defaultValue){

        if (!isNumber(value)) return defaultValue;
        try {
            return Double.parseDouble(value);
        }catch (Exception e){
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static boolean isNumber(String value){

        if (isEmpty(value)) return false;
        Pattern p = Pattern
                .compile("^(([0-9])|(\\.))+$");
        Matcher m = p.matcher(value);
        return m.matches();
    }

    public static boolean isEmpty(String text){

        return text== null || text.replaceAll(" ", "").length() == 0;
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
