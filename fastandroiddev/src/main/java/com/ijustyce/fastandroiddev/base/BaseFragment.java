package com.ijustyce.fastandroiddev.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.net.HttpListener;
import com.ijustyce.fastandroiddev.net.VolleyUtils;

/**
 * Created by yc on 2015/8/14.  baseFragment for all fragment
 */
public abstract class BaseFragment<Bind extends ViewDataBinding> extends Fragment {

    public Context mContext;
    public View mView;
    public Handler handler;

    public String TAG ;
    public Bind contentView;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        if (mView != null) {
            return mView;
        }

        contentView = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mView = contentView.getRoot();

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        if (TAG == null) {
            TAG = getClass().getName();
        }if (mContext == null) {
            mContext = getContext();
        }if (handler == null){
            handler = new Handler();
        }
        doInit();
        afterCreate();
        return mView;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    public abstract int getLayoutId();

    public void afterCreate() {
    }

    public void doInit(){};

    public void doResume() {
    }

    public void toolBarClick(){

    }

    public void toolBarDoubleClick(){

    }

    @Override
    public final void onResume() {
        super.onResume();

        if (mView == null) {
            return;
        }
        if (mContext != null) {
            doResume();
            return;
        }
        doResume();
    }

    public String getResString(int id) {

        return getResources().getString(id);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mContext != null && TAG != null && VolleyUtils
                .getVolleyRequestQueue(mContext) != null){
            VolleyUtils.getVolleyRequestQueue(mContext).cancelAll(TAG);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mContext != null && TAG != null && VolleyUtils
                .getVolleyRequestQueue(mContext) != null){
            VolleyUtils.getVolleyRequestQueue(mContext).cancelAll(TAG);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mContext != null && TAG != null && VolleyUtils
                .getVolleyRequestQueue(mContext) != null){
            VolleyUtils.getVolleyRequestQueue(mContext).cancelAll(TAG);
        }
        if (mContext != null) mContext = null;
        if (httpListener != null) httpListener = null;
        if (TAG != null) TAG = null;
        if (handler != null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    public String getTAG() {
        return TAG;
    }

    public final void newActivity(Intent intent, Bundle bundle){

        if (intent == null || getActivity() == null){
            return;
        }
        if (bundle != null){
            intent.putExtras(bundle);
        }
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public final void newActivity(Intent intent) {

        newActivity(intent, null);
    }

    public final void newActivity(Class<? extends Activity> gotoClass) {

        newActivity(new Intent(mContext, gotoClass), null);
    }

    public final void newActivity(Class<? extends Activity> gotoClass, Bundle bundle) {

        newActivity(new Intent(mContext, gotoClass), bundle);
    }

    public HttpListener httpListener = new HttpListener() {

        @Override
        public void fail(int code, String msg, String taskId) {

            if (mContext == null) {
                return;
            }
            onFailed(code, msg, taskId);
        }

        @Override
        public void success(String object, String taskId) {

            if (mContext == null) {
                return;
            }
            onSuccess(object, taskId);
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //first saving my state, so the bundle wont be empty.
        //https://code.google.com/p/android/issues/detail?id=19917
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    public void onSuccess(String object, String taskId) {

        //  TODO 覆写这个方法，以获取http响应
    }

    public void onFailed(int code, String msg, String taskId) {

        //  ToastUtil.show(mContext, msg);
    }
}
