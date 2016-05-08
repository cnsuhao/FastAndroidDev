package com.ijustyce.diancan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ijustyce.fastandroiddev.base.BaseTabFragment;

public class InfoFragment extends BaseTabFragment {

    private static final int TOTAL = 4;
    @Override
    public void addTitle() {

        mTitleList.add("头条");
        mTitleList.add("体育");
        mTitleList.add("科技");
        mTitleList.add("娱乐");
    }

    @Override
    public void addFragment() {

        for (int i = 0; i < TOTAL; i++){

            Fragment newsFragment = new NewsFragment();
            Bundle tmp = new Bundle();
            tmp.putInt("type", i);
            newsFragment.setArguments(tmp);
            mFragmentList.add(newsFragment);
        }
    }
}
