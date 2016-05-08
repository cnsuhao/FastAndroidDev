package com.ijustyce.diancan;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.ijustyce.fastandroiddev.baseLib.callback.CallBackManager;
import com.ijustyce.fastandroiddev.baseLib.utils.IJson;
import com.ijustyce.fastandroiddev.baseLib.utils.RegularUtils;
import com.ijustyce.fastandroiddev.net.HttpParams;
import com.ijustyce.fastandroiddev.umenglib.ActivityLifeTongJi;
import com.ijustyce.diancan.model.UserInfo;

/**
 * Created by yc on 16-3-18.    Application 类
 */
public class IApplication extends Application {

    private static Context context;
    private static UserInfo userInfo;
    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        CallBackManager.setActivityLifeCall(new ActivityLifeTongJi());

        SharedPreferences shared = getSharedPreferences("userInfo", MODE_PRIVATE);
        String result = shared.getString("userInfo", null);
        if (result != null){
            UserInfo tmp = IJson.fromJson(result, UserInfo.class);

            if (tmp != null) {
                setUserInfo(tmp);
            }
        }
    }

    public static void saveUserInfo(UserInfo userInfo){

        IApplication.setUserInfo(userInfo);

        SharedPreferences shared = context.getSharedPreferences("userInfo", MODE_PRIVATE);
        shared.edit().clear().putString("userInfo", IJson.toJson(userInfo, UserInfo.class)).apply();
    }

    public static boolean isLogin(){

        return userInfo != null && userInfo.getData() !=null &&
                userInfo.getData().getPw() != null && userInfo.getData().getPhone() != null;
    }

    public static void setHead(String head){

        if (!isLogin()){
            return;
        }
        userInfo.getData().setHead(head);
    }

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static String getHead(){

        if (userInfo == null || userInfo.getData() == null){
            return null;
        }
        String head = userInfo.getData().getHead();
        if (RegularUtils.isImage(head)){
            return head;
        }
        return null;
    }

    public static void setUserInfo(UserInfo userInfo) {

        IApplication.userInfo = userInfo;
        if (!isLogin()){
            return;
        }
        HttpParams.addCommon("phone", userInfo == null ? null : userInfo.getData().getPhone());
        HttpParams.addCommon("pw", userInfo == null ? null : userInfo.getData().getPw());
        HttpParams.addCommon("pageSize", 10);
    }
}
