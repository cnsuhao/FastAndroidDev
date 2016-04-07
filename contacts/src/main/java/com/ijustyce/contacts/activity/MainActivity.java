package com.ijustyce.contacts.activity;

import com.alibaba.fastjson.JSON;
import com.ijustyce.contacts.R;
import com.ijustyce.contacts.constant.Constant;
import com.ijustyce.contacts.fragment.FriendsFragment;
import com.ijustyce.contacts.fragment.MeFragment;
import com.ijustyce.contacts.fragment.MsgFragment;
import com.ijustyce.contacts.model.test;
import com.ijustyce.fastandroiddev.base.BaseTabActivity;
import com.ijustyce.fastandroiddev.baseLib.utils.IJson;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;

public class MainActivity extends BaseTabActivity {

    @Override
    public void addFragment() {

        mFragmentList.add(new FriendsFragment());
        mFragmentList.add(new MsgFragment());
        mFragmentList.add(new MeFragment());
    }

    @Override
    public void afterCreate() {

        addTab(R.layout.tab_friends, R.id.radioButton);
        addTab(R.layout.tab_msg, R.id.radioButton);
        addTab(R.layout.tab_me, R.id.radioButton);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {

                test gson = null, fast = null;

                long gsonStart = System.currentTimeMillis();
                for(int i = 0; i < 1; i ++) {
                    gson = IJson.fromJson(Constant.test, test.class);
                }
                ILog.i("===Gson===", "start " + (System.currentTimeMillis() - gsonStart));

                long fastStart = System.currentTimeMillis();
                for(int i = 0; i < 1; i ++) {
                    fast = JSON.parseObject(Constant.test, test.class);
                }
                ILog.i("===fast===", "start " + (System.currentTimeMillis() - fastStart));

                long gsonStart1 = System.currentTimeMillis();
                for(int i = 0; i < 1; i ++) {
                    IJson.toJson(gson, test.class);
                }
                ILog.i("===Gson===", "start " + (System.currentTimeMillis() - gsonStart1));

                long fastStart1 = System.currentTimeMillis();
                for(int i = 0; i < 1; i ++) {
                    JSON.toJSONString(fast);
                }
                ILog.i("===fast===", "start " + (System.currentTimeMillis() - fastStart1));

            }
        }).start();
    }
}
