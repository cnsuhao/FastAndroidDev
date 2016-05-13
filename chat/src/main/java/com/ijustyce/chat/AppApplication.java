package com.ijustyce.chat;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.bugtags.library.Bugtags;
import com.ijustyce.fastandroiddev.IApplication;
import com.ijustyce.fastandroiddev.baseLib.callback.CallBackManager;
import com.ijustyce.fastandroiddev.baseLib.utils.IJson;
import com.ijustyce.fastandroiddev.baseLib.utils.RegularUtils;
import com.ijustyce.fastandroiddev.lifeCall.BugTagCall;
import com.ijustyce.fastandroiddev.net.HttpParams;
import com.ijustyce.fastandroiddev.umenglib.ActivityLifeTongJi;
import com.ijustyce.chat.model.DaoMaster;
import com.ijustyce.chat.model.DaoSession;
import com.ijustyce.chat.model.RecordDao;
import com.ijustyce.chat.model.UserInfo;

/**
 * Created by yc on 16-3-18.    Application 类
 */
public class AppApplication extends IApplication {

    private static UserInfo.DataEntity userInfo;
    private static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();

        Bugtags.start("173fab0fd0acfeaf0c33275339507a6f", this, Bugtags.BTGInvocationEventShake);
        CallBackManager.addActivityLifeCall(new ActivityLifeTongJi());
        CallBackManager.addActivityLifeCall(new BugTagCall());

        SharedPreferences shared = getSharedPreferences("userInfo", MODE_PRIVATE);
        String result = shared.getString("userInfo", null);
        if (result != null){
            UserInfo tmp = IJson.fromJson(result, UserInfo.class);

            if (tmp != null) {
                setUserInfo(tmp);
            }
        }
    }

    public static boolean isTeacher(){

        return isLogin() && userInfo.getTeacher().equals("1");
    }

    public static boolean isLogin(){

        return userInfo != null && userInfo.getPw() != null && userInfo.getPhone() != null;
    }

    public static UserInfo.DataEntity getUserInfo() {
        return userInfo;
    }

    public static String getHead(){

        if (userInfo == null || userInfo.getHead() == null){
            return null;
        }
        String head = userInfo.getHead();
        if (RegularUtils.isImage(head)){
            return head;
        }
        return null;
    }

    private static void initData(){

        if (daoSession != null) return;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getInstance(), "notes-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession (){
        if (daoSession == null) initData();
        return daoSession;
    }

    public static RecordDao getRecordDao (){
        if (daoSession == null) initData();
        return daoSession.getRecordDao();
    }

    public static void saveUserInfo(UserInfo userInfo){

        AppApplication.setUserInfo(userInfo);

        SharedPreferences shared = getInstance().getSharedPreferences("userInfo", MODE_PRIVATE);
        shared.edit().clear().putString("userInfo", IJson.toJson(userInfo, UserInfo.class)).apply();
    }

    public static void setUserInfo(UserInfo userInfo) {

        HttpParams.addCommon("email", userInfo == null ? null : userInfo.getUserId());
        HttpParams.addCommon("pw", userInfo == null ? null : userInfo.getPw());
        HttpParams.addCommon("pageSize", 10);
        AppApplication.userInfo = userInfo == null ? null : userInfo.getUserData();
    }
}
