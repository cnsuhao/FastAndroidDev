package com.ijustyce.fastandroiddev.example.activity;

import com.ijustyce.fastandroiddev.base.BaseTabActivity;
import com.ijustyce.fastandroiddev.example.R;
import com.ijustyce.fastandroiddev.example.fragment.FragmentChat;
import com.ijustyce.fastandroiddev.example.fragment.FragmentContact;
import com.ijustyce.fastandroiddev.example.fragment.FragmentUser;

public class MainActivity extends BaseTabActivity {

    @Override
    public void addFragment() {

        mFragmentList.add(new FragmentContact());
        mFragmentList.add(new FragmentChat());
        mFragmentList.add(new FragmentUser());
    }

    @Override
    public void afterCreate() {

        addTab(R.layout.view_tab_contact, R.id.radioButton);
        addTab(R.layout.view_tab_chat, R.id.radioButton);
        addTab(R.layout.view_tab_user, R.id.radioButton);

        showToolBar(false); //  不显示顶部的导航栏
    }
}