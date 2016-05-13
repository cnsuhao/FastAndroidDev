package com.ijustyce.xingche.http;

import android.content.Context;

import com.ijustyce.fastandroiddev.net.HttpListener;
import com.ijustyce.fastandroiddev.net.HttpParams;
import com.ijustyce.fastandroiddev.net.INetWork;
import com.ijustyce.xingche.constant.HttpConstant;

/**
 * Created by yc on 16-3-17.    用户账号相关接口
 */
public class HttpAccount {

    public static boolean login(String tag, String userId, String pw, Context context, HttpListener listener){

        HttpParams params = HttpParams.create(tag, HttpConstant.LOGIN)
                .add("email", userId).add("pw", pw);
        return INetWork.sendGet(context, params, listener);
    }

    public static boolean register(String tag, String email, String pw, String name,
                                   int isTeacher, Context context, HttpListener listener){

        HttpParams params = HttpParams.create(tag, HttpConstant.REGISTER)
                .add("email", email).add("pw", pw).add("name", name).add("isTeacher", isTeacher);
        return INetWork.sendGet(context, params, listener);
    }

    public static boolean upHead(String tag, String head, Context context, HttpListener listener){

        HttpParams params = HttpParams.create(tag, HttpConstant.UP_HEAD).add("head", head);
        return INetWork.sendGet(context, params, listener);
    }
}
