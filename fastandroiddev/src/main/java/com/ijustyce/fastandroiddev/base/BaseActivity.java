package com.ijustyce.fastandroiddev.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.baseLib.callback.CallBackManager;
import com.ijustyce.fastandroiddev.baseLib.utils.IJson;
import com.ijustyce.fastandroiddev.manager.AppManager;
import com.ijustyce.fastandroiddev.net.HttpListener;
import com.ijustyce.fastandroiddev.net.VolleyUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * base Activity for all Activity
 *
 * @author yc
 */
public abstract class BaseActivity<T> extends AutoLayoutActivity {

    public Activity mContext;
    public SweetAlertDialog dialog;
    public Handler handler;

    public String TAG ;
    private T mData;

    private static final int SHORT_DELAY = 1000;
    private boolean clicked;

    /**
     * onCreate .
     */
    @Override
    protected final void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        if (!beforeCreate()){

            finish();
            return;
        }

        mContext = this;
        setContentView(getLayoutId());

        TAG = getClass().getName();

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clicked){
                        toolBarDoubleClick();
                    }else{
                        clicked = true;
                        toolBarClick();
                        handler.postDelayed(resetClick, SHORT_DELAY);
                    }
                }
            });
        }

        View back = findViewById(R.id.back);
        if (back != null) back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPress();
            }
        });

        AppManager.pushActivity(this);

        handler = new Handler();
        doInit();
        afterCreate();
        CallBackManager.onCreate(this);
    }

    private Runnable resetClick = new Runnable() {
        @Override
        public void run() {
            clicked = false;
        }
    };

    public void toolBarClick(){

    }

    public void toolBarDoubleClick(){

    }

    void doInit(){}

    @Override
    protected void onStop() {
        super.onStop();
        VolleyUtils.getInstance().getVolleyRequestQueue(mContext).cancelAll(TAG);
        CallBackManager.onStop(this);
    }

    /**
     *  onCreate 之前调用，返回值决定代码是否向下执行
     * @return  true 代码继续执行 false finish 并且 return
     */
    public boolean beforeCreate(){
        return true;
    }

    public void afterCreate() {
    }

    public void doResume() {
    }

    @Override
    protected final void onResume() {
        super.onResume();
        CallBackManager.onResume(this);
        doResume();
    }

    public abstract int getLayoutId();

    @Override
    protected void onPause() {
        super.onPause();
        if (mContext != null && TAG != null && VolleyUtils.getInstance()
                .getVolleyRequestQueue(mContext) != null){
            VolleyUtils.getInstance().getVolleyRequestQueue(mContext).cancelAll(TAG);
        }
        if (handler != null){
            handler.post(dismiss);
        }
        CallBackManager.onPause(this);
    }

    public String getTAG() {
        return TAG;
    }

    private Runnable dismiss = new Runnable() {
        @Override
        public void run() {

            if (dialog != null && dialog.isShowing() && mContext != null){

                dialog.cancel();
            }
        }
    };

    /**
     * 让dialog消失
     * @param delay 0 - 5000 大于 5000 按5000计算，小于0按0计算
     */
    public final void dismiss(int delay){

        if (handler == null){
            handler = new Handler();
        }
        if (delay <= 0){
            handler.post(dismiss);
            return;
        }if (delay > 10000){
            delay = 5000;
        }
        handler.postDelayed(dismiss, delay);
    }

    public final void dismiss() {

        dismiss(0);
    }

    public final String getResString(int resId){

        return getResources().getString(resId);
    }

    public final void showProcess(String text){

        dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText(text);
        dialog.getProgressHelper().setBarColor(R.color.colorAccent);
        dialog.show();
    }

    public final void showProcess(int resId){

        dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText(getResString(resId));
        dialog.getProgressHelper().setBarColor(R.color.colorAccent);
        dialog.show();
    }

    @Override
    protected void onDestroy() {

        if (mContext != null && TAG != null && VolleyUtils.getInstance().getVolleyRequestQueue(mContext) != null){
            VolleyUtils.getInstance().getVolleyRequestQueue(mContext).cancelAll(TAG);
        }
        CallBackManager.onDestroy(this);
        super.onDestroy();
        AppManager.moveActivity(this);
        ButterKnife.unbind(this);
        if (httpListener != null) {
            httpListener = null;
        }if (handler != null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }if (mContext != null) {
            mContext = null;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        CallBackManager.dispatchTouchEvent(event, this);
        return super.dispatchTouchEvent(event);
    }

    public final void newActivity(Intent intent, Bundle bundle){

        if (intent == null){
            return;
        }
        if (bundle != null){
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

    public final void newActivity(Intent intent) {

        newActivity(intent, null);
    }

    public final void newActivity(Class gotoClass) {

        newActivity(new Intent(mContext, gotoClass), null);
    }

    public final void newActivity(Class gotoClass, Bundle bundle) {

        newActivity(new Intent(this, gotoClass), bundle);
    }

    public HttpListener httpListener = new HttpListener() {

        @Override
        public void fail(int code, String msg, String taskId) {

            dismiss();
            if (mContext == null) {
                return;
            }
            onFailed(code, msg, taskId);
        }

        @Override
        public void success(String object, String taskId) {

            dismiss();
            if (mContext == null) {
                return;
            }
            Class type = getType();
            if (type != null) {
                mData = IJson.fromJson(object, getType());
            }
            onSuccess(object, taskId);
        }
    };

    public final T getData(){

        return mData;
    }

    public Class getType(){

        return null;
    }

    public void onSuccess(String object, String taskId){

    }

    public void onFailed(int code, String msg, String taskId) {

        dismiss();
        //  ToastUtil.show(mContext, msg);
    }

    public void backPress() {

        this.finish();
    }

    public final String getResText(int id){

        return getResources().getString(id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //first saving my state, so the bundle wont be empty.
        //https://code.google.com/p/android/issues/detail?id=19917
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            backPress();
        }
        return true;
    }
}
