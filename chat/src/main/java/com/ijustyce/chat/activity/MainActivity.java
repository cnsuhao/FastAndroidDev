package com.ijustyce.chat.activity;

import android.view.View;
import android.widget.TextView;

import com.ijustyce.chat.AppApplication;
import com.ijustyce.chat.R;
import com.ijustyce.chat.fragment.ChatFragment;
import com.ijustyce.chat.fragment.FriendsFragment;
import com.ijustyce.chat.fragment.MeFragment;
import com.ijustyce.fastandroiddev.base.BaseTabActivity;

import butterknife.Bind;

public class MainActivity extends BaseTabActivity {

    @Bind(R.id.right)
    TextView right;
    @Override
    public void addFragment() {

        mFragmentList.add(new FriendsFragment());
        mFragmentList.add(new ChatFragment());
        mFragmentList.add(new MeFragment());
    }

    @Override
    public void onPageSelect(int position) {
        super.onPageSelect(position);
    }

    @Override
    public void afterCreate() {

        if (!AppApplication.isLogin()){
            newActivity(LoginActivity.class);
            finish();
            return;
        }

        addTab(R.layout.tab_jiaowu, R.id.radioButton);
        addTab(R.layout.tab_kebiao, R.id.radioButton);
        addTab(R.layout.tab_me, R.id.radioButton);

    //    UpdateTool.update(this);
        onPageSelect(0);

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  newActivity(AppApplication.isTeacher() ? NewKeCheng.class : SearchCourse.class);
            }
        });
    }
}
