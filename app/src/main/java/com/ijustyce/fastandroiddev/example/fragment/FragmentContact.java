package com.ijustyce.fastandroiddev.example.fragment;

import com.ijustyce.fastandroiddev.base.BaseTabFragment;
import com.ijustyce.fastandroiddev.example.R;

/**
 * Created by yc on 2016/9/5 0005.
 */

public class FragmentContact extends BaseTabFragment {

    @Override
    public void addTitle() {
        mTitleList.add(getResString(R.string.tab_contact_contacts));
        mTitleList.add(getResString(R.string.tab_contact_collect));
        mTitleList.add(getResString(R.string.tab_contact_history));
    }

    @Override
    public void addFragment() {
        mFragmentList.add(new FragmentUser());
        mFragmentList.add(new FragmentUser());
        mFragmentList.add(new FragmentUser());
    }
}
