package com.ijustzxh.bishe.activity;

import android.content.Intent;
import android.view.View;

import com.ijustzxh.bishe.R;

import butterknife.OnClick;

/**
 * Created by zxh on 2016/1/25 0025.
 */
public class OrderActivity extends AppBaseActivity {

    @OnClick({R.id.orderLogin})
    public void onClick(View view){

        if (view == null){
            return;
        }

        switch (view.getId()){

            case R.id.orderLogin:
                Intent intent = new Intent(OrderActivity.this,LoginActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order;
    }
}
