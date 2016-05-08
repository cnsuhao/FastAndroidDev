package com.ijustyce.diancan.http;

import android.content.Context;

import com.ijustyce.fastandroiddev.net.HttpListener;
import com.ijustyce.fastandroiddev.net.HttpParams;
import com.ijustyce.fastandroiddev.net.INetWork;
import com.ijustyce.diancan.constant.Constant;

/**
 * Created by yc on 16-4-29.    新闻获取
 */
public class HttpNews {

    public static boolean listTuiJian(String tag, int pageNo, Context context, HttpListener listener){

        HttpParams params = HttpParams.create(tag, Constant.LIST_TUIJIAN + "&offset=" + ((pageNo - 1) * 20 + 1));
        params.addCacheKey(pageNo).setCacheTime(-1);
        return INetWork.sendGet(context, params, listener);
    }

    public static boolean listToutTiao(String tag, int pageNo, Context context, HttpListener listener){

        HttpParams params = HttpParams.create(tag, Constant.LIST_TOP + "&pull_times=" + pageNo);
        params.addCacheKey(pageNo).setCacheTime(-1);
        return INetWork.sendGet(context, params, listener);
    }

    public static boolean listSport(String tag, int pageNo, Context context, HttpListener listener){

        HttpParams params = HttpParams.create(tag, Constant.LIST_TIYU + "&p=" + pageNo);
        params.addCacheKey(pageNo).setCacheTime(-1);
        return INetWork.sendGet(context, params, listener);
    }

    public static boolean listKeJi(String tag, int pageNo, Context context, HttpListener listener){

        HttpParams params = HttpParams.create(tag, Constant.LIST_KEJI + "&p=" + pageNo);
        params.addCacheKey(pageNo).setCacheTime(-1);
        return INetWork.sendGet(context, params, listener);
    }

    public static boolean listYule(String tag, int pageNo, Context context, HttpListener listener){

        HttpParams params = HttpParams.create(tag, Constant.LIST_YULE);
        params.addCacheKey(pageNo).setCacheTime(-1);
        return INetWork.sendGet(context, params, listener);
    }
}
