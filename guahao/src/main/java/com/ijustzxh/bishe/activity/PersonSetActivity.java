package com.ijustzxh.bishe.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.ijustzxh.bishe.R;

import butterknife.OnClick;

/**
 * Created by zxh on 2016/1/20 0020.
 */
public class PersonSetActivity extends AppBaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.person_set;
    }

    @Override
    public void afterCreate() {
        super.afterCreate();
    }

    /** 返回到上一界面 */
    @OnClick({R.id.exit, R.id.setLayout3, R.id.back})
    public void viewClick(View view) {

        if (view == null){
            return;
        }
        switch (view.getId()) {

            case R.id.back:
                finish();
                break;

            case R.id.exit:
                doExit();
                break;

            case R.id.setLayout2:
                Intent intent = new Intent(PersonSetActivity.this,ModifyPasswordActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    private void doExit(){

        AlertDialog isExit = new AlertDialog.Builder(PersonSetActivity.this).create();
        isExit.setTitle(getResources().getString(R.string.cue));
        isExit.setMessage(getResources().getString(R.string.issue));
        isExit.setButton(getResources().getString(R.string.ok),listener);
        isExit.setButton2(getResources().getString(R.string.no),listener);
        isExit.show();
    }

    final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog,int which){
            switch (which){
                case AlertDialog.BUTTON_POSITIVE:
                    AppManager.finishAll();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    break;
                default:
                    break;
            }
        }

    };

}
