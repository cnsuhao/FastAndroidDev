package com.ijustyce.fastandroiddev.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by yangchun on 16/3/8.   volley 工具类
 */
public class VolleyUtils {

    private static RequestQueue mRequestQueue;
    private static VolleyUtils volleyUtils;

    private VolleyUtils() {

    }

    public static VolleyUtils getInstance() {

        if (volleyUtils == null) {
            volleyUtils = new VolleyUtils();
        }

        return volleyUtils;
    }

    /**
     * Returns a Volley request queue for creating network requests
     *
     * @return {@link com.android.volley.RequestQueue}
     */
    public RequestQueue getVolleyRequestQueue(Context context) {

        if (mRequestQueue == null && context != null) {
            mRequestQueue = Volley.newRequestQueue(context, new IOkHttpStack(new OkHttpClient()));
        }
        return mRequestQueue;
    }

    /**
     * Adds a request to the Volley request queue with a given tag
     *
     * @param request is the request to be added
     * @param tag     is the tag identifying the request
     */
    public static void addRequest(Request<?> request, String tag, Context context) {

        request.setTag(tag);
        addRequest(request, context);
    }

    /**
     * Adds a request to the Volley request queue
     *
     * @param request is the request to add to the Volley queue
     */
    public static void addRequest(Request<?> request, Context context) {

        getInstance().getVolleyRequestQueue(context).add(request);
    }

    /**
     * Cancels all the request in the Volley queue for a given tag
     *
     * @param tag associated with the Volley requests to be cancelled
     */
    public static void cancelAllRequests(String tag, Context context) {

        if (getInstance().getVolleyRequestQueue(context) != null) {
            getInstance().getVolleyRequestQueue(context).cancelAll(tag);
        }
    }

    public static void cancelAll() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {

                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
        }
    }

//    public interface Method {
//        int DEPRECATED_GET_OR_POST = -1;
//        int GET = 0;
//        int POST = 1;
//        int PUT = 2;
//        int DELETE = 3;
//        int HEAD = 4;
//        int OPTIONS = 5;
//        int TRACE = 6;
//        int PATCH = 7;
//    }
}
