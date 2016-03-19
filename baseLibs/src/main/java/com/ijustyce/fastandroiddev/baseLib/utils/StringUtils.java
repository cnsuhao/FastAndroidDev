package com.ijustyce.fastandroiddev.baseLib.utils;

import android.support.annotation.NonNull;

/**
 * Created by yc on 15-12-24.
 */
public class StringUtils {

    public static boolean isEmpty(@NonNull String text){

        return text.replaceAll(" ", "").length() == 0;
    }

    public static int getInt(String value){

        try {
            return Integer.parseInt(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断是否为网址
     */
    public static boolean isUrl(@NonNull String text){

        return !isEmpty(text) && (text.startsWith("http://") || text.startsWith("https://"));
    }
}
