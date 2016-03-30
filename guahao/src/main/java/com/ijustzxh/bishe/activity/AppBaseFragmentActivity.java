package com.ijustzxh.bishe.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by zxh on 2016/1/21 0021.
 */
public class AppBaseFragmentActivity extends FragmentActivity {

    /**
     * onCreate .
     */
    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        AppManager.pushActivity(this);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        AppManager.moveActivity(this);
    }

}
