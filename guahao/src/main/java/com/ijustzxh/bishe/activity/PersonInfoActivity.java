package com.ijustzxh.bishe.activity;

import android.view.View;

import com.ijustzxh.bishe.R;

import butterknife.OnClick;

/**
 * Created by zxh on 2016/1/16 0016.
 */
public class PersonInfoActivity extends AppBaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.person_info;
    }

    @OnClick({R.id.back})
    public void viewClick(View view) {

        if (view == null){
            return;
        }
        switch (view.getId()) {

            case R.id.back:
                finish();
                break;

            default:
                break;
        }
    }
}
