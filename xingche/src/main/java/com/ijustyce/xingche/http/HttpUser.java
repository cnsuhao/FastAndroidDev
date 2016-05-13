package com.ijustyce.xingche.http;

import android.content.Context;

import com.ijustyce.fastandroiddev.net.HttpListener;
import com.ijustyce.fastandroiddev.net.HttpParams;
import com.ijustyce.fastandroiddev.net.INetWork;
import com.ijustyce.xingche.constant.Constant;
import com.ijustyce.xingche.constant.HttpConstant;

/**
 * Created by yc on 16-3-17.    用户账号相关接口
 */
public class HttpUser {

    public static boolean newUser(String tag, String phone, String pw, String name,
                                  Context mContext, HttpListener httpListener){

        HttpParams params = HttpParams.create(tag, HttpConstant.REGISTER);
        params.add("phone", phone).add("pw", pw).add("name", name).add("appId", Constant.APPID).add("shenfen", 0);
        return INetWork.sendGet(mContext, params, httpListener);
    }

    public static boolean login(String tag, String phone, String pw,
                                Context mContext, HttpListener httpListener){

        HttpParams params = HttpParams.create(tag, HttpConstant.LOGIN);
        params.add("phone", phone).add("pw", pw).add("appId", Constant.APPID);
        return INetWork.sendGet(mContext, params, httpListener);
    }

    public static boolean upHead(String tag, String head, Context context, HttpListener listener){

        HttpParams params = HttpParams.create(tag, HttpConstant.UP_HEAD).add("head", head);
        return INetWork.sendGet(context, params, listener);
    }
}
