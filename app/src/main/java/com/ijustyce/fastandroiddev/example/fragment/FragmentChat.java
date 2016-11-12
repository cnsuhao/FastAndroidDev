package com.ijustyce.fastandroiddev.example.fragment;

import com.ijustyce.fastandroiddev.base.BaseTabFragment;
import com.ijustyce.fastandroiddev.example.R;

/**
 * Created by yc on 2016/9/5 0005.
 */

public class FragmentChat extends BaseTabFragment {

    @Override
    public void addTitle() {
        mTitleList.add(getResString(R.string.tab_chat_message));
        mTitleList.add(getResString(R.string.tab_chat_chat));
        mTitleList.add(getResString(R.string.tab_chat_voip));
    }

    @Override
    public void addFragment() {
        mFragmentList.add(new FragmentContactList());
        mFragmentList.add(new FragmentContactList());
        mFragmentList.add(new FragmentContactList());
    }
}
