package com.ijustyce.chat.activity;

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

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by yc on 16-3-14.    注册界面
 */
public class RegisterActivity extends BaseActivity<UserInfo> {

    @Bind(R.id.label)
    TextView label;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.passwordAgain)
    EditText passwordAgain;

//    @Bind(R.id.isStudent)
//    CheckBox isStudent;
//    @Bind(R.id.isTeacher)
//    CheckBox isTeacher;

  //  private int identity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void afterCreate() {
        super.afterCreate();

        label.setText("注册");
        AppManager.finishExcept(RegisterActivity.class);
    }

    @Override
    public Class getType() {
        return UserInfo.class;
    }

    @Override
    public void onSuccess(String object, String taskId) {
        UserInfo userInfo = getData();
        if (userInfo == null||userInfo.getResult() == null) return;
        if (!userInfo.getResult().getCode().equals("100")){
            ToastUtil.show(mContext, userInfo.getResult().getMsg());
            return;
        }
        AppApplication.saveUserInfo(userInfo);
        newActivity(MainActivity.class);
    }

    private synchronized void register(){

        String phoneText = CommonTool.getText(phone);
        String nameText = CommonTool.getText(name);
        String passwordText = CommonTool.getText(password);
        String passwordAgainText = CommonTool.getText(passwordAgain);

        if (!RegularUtils.isMobilePhone(phoneText)){

            ToastUtil.show(mContext, "手机号格式错误!");
            return;
        }

        if (nameText == null || nameText.length() < 2){

            ToastUtil.show(mContext, "姓名格式错误!");
            return;
        }

        if (passwordText == null || passwordText.length() < 6){

            ToastUtil.show(mContext, "密码不能少于6位！");
            return;
        }

        if (!passwordText.equals(passwordAgainText)){

            ToastUtil.show(mContext, "两次密码输入不一致!");
            return;
        }

//        getIdentity();
//        if (identity!= Constant.USER && identity!= Constant.ADMIN){
//            ToastUtil.show(mContext, "请选择身份");
//            return;
//        }

        showProcess("正在注册");
        if(!HttpUser.newUser(getTAG(), phoneText, passwordText, nameText, mContext, httpListener)){
            dismiss();
        }
    }

//    private void unCheckAll(){
//
//        isStudent.setChecked(false);
//        isTeacher.setChecked(false);
//    }

//    private void getIdentity(){
//
//        if (isStudent.isChecked()) identity = Constant.USER;
//        else if (isTeacher.isChecked()) identity = Constant.ADMIN;
//        else identity = -1;
//    }

    @OnClick({R.id.back, R.id.register})
    public void onClick(View view){

        if (view == null){

            return;
        }

        switch (view.getId()){

            case R.id.back:
                this.finish();
                break;

            case R.id.register:
                register();
                break;
        }
    }
}
