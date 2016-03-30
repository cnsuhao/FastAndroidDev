package com.ijustzxh.bishe.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import com.ijustzxh.bishe.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zxh on 2016/1/22 0022.
 */
public class ModifyPasswordActivity extends AppBaseActivity {
    private TimeCount time;
    @Bind(R.id.getIdentify)
    Button checking;

    @Override
    public void afterCreate() {
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            checking.setText("重新获取");
            checking.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            checking.setClickable(false);
            checking.setText("重新获取"+"("+millisUntilFinished / 1000 + ")");
        }
    }

    /** 返回到上一界面 */
    @OnClick({R.id.getIdentify, R.id.back})
    public void viewClick(View view) {

        if (view == null){
            return;
        }

        switch (view.getId()) {

            case R.id.back:
                finish();
                break;

            case R.id.getIdentify:
                time.start();
                break;

            default:
                break;
        }
    }

}
