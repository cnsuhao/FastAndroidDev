package com.ijustyce.contacts.fragment;

import com.ijustyce.contacts.R;
import com.ijustyce.fastandroiddev.base.BaseTabFragment;

/**
 * Created by yc on 16-2-8. 通讯tab
 */
public class FriendsFragment extends BaseTabFragment {

    @Override
    public void addTitle() {

        mTitleList.add(getResString(R.string.friends_contacts));
        mTitleList.add(getResString(R.string.friends_call));
        mTitleList.add(getResString(R.string.friends_record));
    }

    @Override
    public void addFragment() {

        mFragmentList.add(new ContactsFragment());
        mFragmentList.add(new CallFragment());
        mFragmentList.add(new RecordFragment());

//        setTabIndicatorColor(R.color.select);
//        setTabBackground(R.color.home_tab);
//        setTabTextColor(R.color.textColos, R.color.select);
    }
}
