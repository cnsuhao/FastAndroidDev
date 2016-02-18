package com.ijustyce.fastandroiddev.umenglib;

import com.ijustyce.fastandroiddev.baseLib.callback.ActivityLifeCall;

/**
 * Created by yc on 16-2-7. umeng 统计 你可以在你的Application类里设置：
 *      CallBackManager.setActivityLifeCall(new ActivityLifeTongJi());
 *      如果你有多个类似的需求，可以自己写一起，或者通过继承，这里只是umeng统计
 */
public class ActivityLifeTongJi implements ActivityLifeCall {

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
