package com.ijustyce.chat.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ijustyce.chat.AppApplication;
import com.ijustyce.chat.R;
import com.ijustyce.chat.http.HttpUser;
import com.ijustyce.chat.model.UserInfo;
import com.ijustyce.fastandroiddev.base.BaseActivity;
import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev.baseLib.utils.RegularUtils;
import com.ijustyce.fastandroiddev.baseLib.utils.ToastUtil;
import com.ijustyce.fastandroiddev.manager.AppManager;
import com.ijustyce.fastandroiddev.net.HttpParams;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by yc on 16-3-14.    登录界面
 */
public class LoginActivity extends BaseActivity<UserInfo> {

    @Bind(R.id.label)
    TextView label;

    @Bind(R.id.userId)
    EditText userId;

    @Bind(R.id.password)
    EditText password;

    private String pw, phone;

    private static LoginCallBack callBack;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void afterCreate() {
        super.afterCreate();

        AppManager.finishExcept(LoginActivity.class);
        HttpParams.addCommon("phone", null);
        HttpParams.addCommon("pw", null);
        label.setText("登录");
    }

    private void doLogin(){

        pw = CommonTool.getText(password);
        phone = CommonTool.getText(userId);

        if (!RegularUtils.isMobilePhone(phone)){

            ToastUtil.show(mContext, "手机号格式错误!");
            return;
        }
        showProcess("正在登陆");
        if(!HttpUser.login(getTAG(), phone, pw, mContext, httpListener)){
            dismiss();
        }
    }

    @Override
    public Class getType() {
        return UserInfo.class;
    }

    @Override
    public void onSuccess(String object, String taskId) {
        super.onSuccess(object, taskId);

        UserInfo userInfo = getData();
        if (userInfo == null||userInfo.getResult() == null) return;
        if (!userInfo.getResult().getCode().equals("100")){
            ToastUtil.show(mContext, userInfo.getResult().getMsg());
            return;
        }
        AppApplication.saveUserInfo(userInfo);
        if (callBack != null) callBack.onSuccess();
        newActivity(MainActivity.class);
    }

    @OnClick({R.id.register, R.id.login, R.id.back})
    public void onClick(View view){

        if (view == null){
            return;
        }

        switch (view.getId()){

            case R.id.register:
                newActivity(RegisterActivity.class);
                finish();
                break;

            case R.id.login:
                doLogin();
                break;

            case R.id.back:
                finish();
                break;

            default:
                break;
        }
    }

    public static void doLogin(LoginCallBack callBack, Activity activity){

        LoginActivity.callBack = callBack;
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }

    public interface LoginCallBack{

        public void onSuccess();
    }
}
