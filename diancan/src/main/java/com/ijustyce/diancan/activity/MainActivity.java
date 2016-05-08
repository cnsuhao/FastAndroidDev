package com.ijustyce.diancan.activity;

import com.ijustyce.fastandroiddev.base.BaseTabActivity;
import com.ijustyce.fastandroiddev.manager.AppManager;
import com.ijustyce.fastandroiddev.umenglib.UpdateTool;
import com.ijustyce.diancan.R;
import com.ijustyce.diancan.fragment.InfoFragment;
import com.ijustyce.diancan.fragment.MeFragment;
import com.ijustyce.diancan.fragment.NewsFragment;

public class MainActivity extends BaseTabActivity {

    @Override
    public void addFragment() {

        mFragmentList.add(new NewsFragment());
        mFragmentList.add(new InfoFragment());
        mFragmentList.add(new MeFragment());
    }

    @Override
    public void onPageSelect(int position) {
        super.onPageSelect(position);
    }

    @Override
    public void afterCreate() {

        AppManager.finishExcept(MainActivity.class);
        addTab(R.layout.tab_index, R.id.radioButton);
        addTab(R.layout.tab_info, R.id.radioButton);
        addTab(R.layout.tab_me, R.id.radioButton);

        UpdateTool.update(this);
        onPageSelect(0);
    }
}
