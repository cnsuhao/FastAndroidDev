package com.ijustyce.fastandroiddev.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.net.HttpListener;

import org.json.JSONObject;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by yc on 2015/8/14.  baseFragment for all fragment
 */
public abstract class BaseFragment extends Fragment {

    public Context mContext;
    public View mView;
    public SweetAlertDialog dialog;
    public Handler handler;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        if (mView != null) {
            return mView;
        }
        mView = inflater.inflate(getLayoutId(), container, false);
        mContext = getActivity();
        ButterKnife.bind(this, mView);
        handler = new Handler();
        afterCreate();
        return mView;
    }

    public abstract int getLayoutId();

    public void afterCreate() {
    }

    public void doResume() {
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
        dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();
        dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismiss();
        ButterKnife.unbind(this);
        if (mContext != null) {
            mContext = null;
        }
        if (httpListener != null) {
            httpListener = null;
        }
    }

    private Runnable dismiss = new Runnable() {
        @Override
        public void run() {

            if (dialog != null && dialog.isShowing() && mContext != null) {

                dialog.cancel();
            }
        }
    };

    public void dismiss() {

        if (handler == null) {
            handler = new Handler();
        }
        handler.post(dismiss);
    }

    public void showProcess(int resId) {

        dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText(getResString(resId));
        dialog.getProgressHelper().setBarColor(R.color.colorAccent);
        dialog.show();
    }

    public void newActivity(Class<?> gotoClass) {

        mContext.startActivity(new Intent(mContext, gotoClass));
    }

    public void newActivity(Intent intent) {

        mContext.startActivity(intent);
    }

    public HttpListener httpListener = new HttpListener() {
        @Override
        public void success(JSONObject data, String taskId) {

            dismiss();
            if (mContext == null) {
                return;
            }
            onSuccess(data, taskId);
        }

        @Override
        public void fail(int code, String msg, String taskId) {

            dismiss();
            if (mContext == null) {
                return;
            }
            onFailed(code, msg, taskId);
        }

        @Override
        public void success(Object object, String taskId) {
            onSuccess(object, taskId);
        }
    };

    public void onSuccess(Object object, String taskId) {

        //  TODO 覆写这个方法，以获取http响应
    }

    public void onSuccess(JSONObject data, String taskId) {

        //  TODO 覆写这个方法，以获取http响应
    }

    public void onFailed(int code, String msg, String taskId) {

        //  ToastUtil.show(mContext, msg);
    }
}
