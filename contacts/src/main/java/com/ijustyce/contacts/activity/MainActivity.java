package com.ijustyce.contacts.activity;

import com.ijustyce.contacts.R;
import com.ijustyce.contacts.fragment.FriendsFragment;
import com.ijustyce.contacts.fragment.MeFragment;
import com.ijustyce.contacts.fragment.MsgFragment;
import com.ijustyce.fastandroiddev.base.BaseTabActivity;

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
}
