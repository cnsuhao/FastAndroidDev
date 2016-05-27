package com.ijustyce.fastandroiddev.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.ijustyce.fastandroiddev.IApplication;
import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;

/**
 * Created by yc on 2016/5/9 0009.  读写sharedpreferences
 */
public class PreferenceManager {

    private static SharedPreferences shared;
    static {
        shared = IApplication.getInstance().getSharedPreferences
                (CommonTool.getPkgName(IApplication.getInstance()), Context.MODE_PRIVATE);
    }

    public static int getInt(String key){
        return getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue){
        return shared.getInt(key, defaultValue);
    }

    public static void putInt(String key, int value){
        shared.edit().putInt(key, value).apply();
    }
}
