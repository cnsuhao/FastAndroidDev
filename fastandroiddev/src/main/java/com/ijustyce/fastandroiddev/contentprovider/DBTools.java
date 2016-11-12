package com.ijustyce.fastandroiddev.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.ijustyce.fastandroiddev.IApplication;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yangchun on 16/6/19.
 */
class DBTools {

    private static ContentResolver contentResolver;
    private static String COMMON;

    static {
        contentResolver = IApplication.getInstance().getContentResolver();
        COMMON = "content://" + IApplication.getInstance().getPackageName() + ".provider/common";
    }

    static void saveCommonData(String key, String value, String userId) {
        ContentValues values = new ContentValues();
        values.put("key", key);
        values.put("value", value);
        values.put("userId", userId);
        contentResolver.insert(Uri.parse(COMMON), values);
        values.clear();
    }

    @NonNull
    static HashMap<String, String> getCommonValue(String userId){
        Uri uri = Uri.parse(COMMON);
        String[] projection = {"_id", "key", "value"};
        String sortOrder = "_id ASC";
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(uri, projection, "userId = '" + userId + "'", null, sortOrder);
        }catch (Exception ignore){

        }
        HashMap<String, String> result = new HashMap<>();
        if (cursor == null) return result;
        if (cursor.moveToFirst()) {
            do {
                String value = cursor.getString(cursor.getColumnIndex("value"));
                String key = cursor.getString(cursor.getColumnIndex("key"));
                result.put(key, value);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    static ArrayList<String> getCommonValue(String key, String userId) {
        Uri uri = Uri.parse(COMMON);
        String[] projection = {"_id", "key", "value"};
        String sortOrder = "_id ASC";
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(uri, projection, "key = '" + key + "' and userId = '" + userId + "'", null, sortOrder);
        }catch (Exception ignore){

        }
        ArrayList<String> result = new ArrayList<>();
        if (cursor == null) return result;
        if (cursor.moveToFirst()) {
            do {
                String value = cursor.getString(cursor.getColumnIndex("value"));
                result.add(value);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public static void deleteAllCommon(String userId) {
        Uri uri = Uri.parse(COMMON);
        try {
            String sql = userId == null ? null : "userId = '" + userId + "'";
            contentResolver.delete(uri, sql, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteCommonByKey(String key, String userId) {
        Uri uri = Uri.parse(COMMON);
        try {
            contentResolver.delete(uri, "key = '" + key + "' and userId = '" + userId + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
