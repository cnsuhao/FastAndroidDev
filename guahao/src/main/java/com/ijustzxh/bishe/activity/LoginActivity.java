package com.ijustzxh.bishe.activity;

import android.content.Intent;
import android.view.View;

import com.ijustzxh.bishe.R;

import butterknife.OnClick;

/**
 * Created by zxh on 2016/1/25 0025.
 */
public class LoginActivity extends AppBaseActivity {

    @OnClick({R.id.newUser, R.id.forget_pwd})
    public void onClick(View view){

        if (view == null){
            return;
        }

        switch (view.getId()){

            case R.id.newUser:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.forget_pwd:
                Intent intent2 = new Intent(LoginActivity.this, ForgetPwdActivity.class);
                startActivity(intent2);
                break;

            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.login;
    }
}
