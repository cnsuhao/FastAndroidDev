package com.ijustyce.fastandroiddev.example.activity;

import com.ijustyce.fastandroiddev.base.BaseActivity;
import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev.contentprovider.CommonData;
import com.ijustyce.fastandroiddev.example.EventHandler.ShowMVVMEvent;
import com.ijustyce.fastandroiddev.example.R;
import com.ijustyce.fastandroiddev.example.databinding.MainView;
import com.ijustyce.fastandroiddev.example.model.User;

public class MainActivity extends BaseActivity<MainView, User> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void afterCreate() {

        final User user = new User();
        user.setAge(21);
        user.setName("haha");
        contentView.setUser(user);
        contentView.setHandler(new ShowMVVMEvent(this));

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                user.setAge(32);
                user.setName("xixixixi");
            }
        }, 5000);
        /***
         *  框架自带CommonData 它可以跨进程共享数据，但是key是唯一的，言外之意就是，后面的数据会覆盖前面的数据
         *  如果是多线程保存数据，而且key一样，可以保证存入的数据是最后一次存入的数据，是安全的，但是多进程存储，
         *  且key是同一个，则无法保证数据合法性！另，支持多用户，具体请看CommonData
         */
        int version = CommonTool.getVersionCode(mContext);
        CommonData.put("version", version);
    }
}