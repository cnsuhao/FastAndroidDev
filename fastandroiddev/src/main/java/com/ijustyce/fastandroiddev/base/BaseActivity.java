package com.ijustyce.fastandroiddev.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.baseLib.callback.CallBackManager;
import com.ijustyce.fastandroiddev.manager.AppManager;
import com.ijustyce.fastandroiddev.net.HttpListener;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * base Activity for all Activity
 *
 * @author yc
 */
public abstract class BaseActivity extends AutoLayoutActivity {

    public Activity mContext;
    public SweetAlertDialog dialog;
    public Handler handler;

    /**
     * onCreate .
     */
    @Override
    protected final void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        AppManager.pushActivity(this);

        mContext = this;
        handler = new Handler();
        afterCreate();

        CallBackManager.getActivityLifeCall().onCreate();
    }

    @Override
    protected void onStop() {
        super.onStop();
        CallBackManager.getActivityLifeCall().onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CallBackManager.getActivityLifeCall().onResume();
    }

    public abstract int getLayoutId();

    public void afterCreate(){};

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null){
            handler.post(dismiss);
        }
        CallBackManager.getActivityLifeCall().onPause();
    }

    private Runnable dismiss = new Runnable() {
        @Override
        public void run() {

            if (dialog != null && dialog.isShowing() && mContext != null){

                dialog.cancel();
            }
        }
    };

    public void dismiss(){

        if (handler == null){
            handler = new Handler();
        }
        handler.post(dismiss);
    }

    public String getResString(int resId){

        return getResources().getString(resId);
    }

    public void showProcess(int resId){

        dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText(getResString(resId));
        dialog.getProgressHelper().setBarColor(R.color.colorAccent);
        dialog.show();
    }

    @Override
    protected void onDestroy() {

        CallBackManager.getActivityLifeCall().onDestroy();
        super.onDestroy();
        AppManager.moveActivity(this);
        ButterKnife.unbind(this);
        if (httpListener != null) {
            httpListener = null;
        }
        mContext = null;
    }

    //  你可以在这里加入界面切换的动画，或者统计页面等
    public void newActivity(Intent intent) {

        startActivity(intent);
    }

    public void newActivity(Class<?> gotoClass) {

        newActivity(new Intent(this, gotoClass));
    }

    public HttpListener httpListener = new HttpListener() {
        @Override
        public void success(JSONObject data, String taskId) {

            if (mContext == null) {
                return;
            }
            onSuccess(data, taskId);
        }

        @Override
        public void fail(int code, String msg, String taskId) {

            if (mContext == null) {
                return;
            }
            onFailed(code, msg, taskId);
        }

        @Override
        public void success(Object object, String taskId) {

            if (mContext == null) {
                return;
            }
            onSuccess(object, taskId);
        }
    };

    public void onSuccess(Object object, String taskId){

    }

    public void onSuccess(JSONObject data, String taskId) {

        //  TODO 覆写这个方法，以获取http响应
    }

    public void onFailed(int code, String msg, String taskId) {

        dismiss();
        //  ToastUtil.show(mContext, msg);
    }

    public void backPress() {

        this.finish();
    }

    public String getResText(int id){

        return getResources().getString(id);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            backPress();
        }
        return true;
    }
}
