package com.ijustzxh.unit;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by memacjay on 2014/4/16.
 * 清除应用缓存管理类
 */
public class CleanCacheManager {

    /**
     * 清除本应用内部缓存（/data/data/com.zjseek.app/cache）
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFileByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库（/data/data/com.zjseek.app/databases）
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFileByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
    }

    /**
     * 清除本应用的SharedPreference(/data/data/com.zjseek.app/shared_prefs)
     *
     * @param context
     */
    public static void cleanSharePreference(Context context) {
        deleteFileByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 按名字清除本应用数据库      *       * @param context      * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容      *       * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFileByDirectory(context.getFilesDir());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)      *       * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFileByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 删除文件夹下面的文件,如果diretory是个文件,不做处理
     *
     * @param diretory
     */
    private static void deleteFileByDirectory(File diretory) {
        if (diretory != null && diretory.exists() && diretory.isDirectory()) {
            for (File item : diretory.listFiles()) {
                item.delete();
            }
        }
    }
}
