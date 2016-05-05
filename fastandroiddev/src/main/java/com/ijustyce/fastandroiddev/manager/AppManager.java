package com.ijustyce.fastandroiddev.manager;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 15-12-25.   activity管理类
 */
public class AppManager {

    private static List<WeakReference<Activity>> allActivity;

    static {

        allActivity = new ArrayList<>();
    }

    public static void pushActivity(Activity activity) {

        allActivity.add(new WeakReference<>(activity));
    }

    public static void moveActivity(Activity activity) {

        finishExcept(getClass(activity));
    }

    public static void finishActivity(Class className){

        if (className == null){
            return;
        }

        for (WeakReference<Activity> tmp : allActivity) {
            if (tmp == null || tmp.get() == null) {
                continue;
            }
            String tmpClass = getClassName(tmp.get());
            if (tmpClass.equals(className.getName())) {
                tmp.get().finish();
            }
        }
    }

    public static void finishAll(){

        for (WeakReference<Activity> tmp : allActivity) {
            if (tmp != null && tmp.get() != null){
                tmp.get().finish();
            }
        }
        allActivity.clear();
    }

    /**
     *  结束其他所有Activity
     * @param className 要保留的activity
     */
    public static void finishExcept(Class className) {

        if (allActivity == null || className == null) {
            return;
        }
        List<WeakReference<Activity>> remove = new ArrayList<>();
        for (WeakReference<Activity> tmp : allActivity) {
            if (tmp == null || tmp.get() == null) {
                continue;
            }
            String tmpClass = getClassName(tmp.get());
            if (!tmpClass.equals(className.getName())) {
                remove.add(tmp);
                tmp.get().finish();
            }
        }
        if (!remove.isEmpty()) {
            allActivity.removeAll(remove);
        }
    }

    private static Class getClass(Activity activity){

        return activity == null ? null : activity.getClass();
    }

    private static String getClassName(Activity activity){

        if (activity == null){
            return "";
        }
        return activity.getClass().getName();
    }
}