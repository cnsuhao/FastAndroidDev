package com.ijustyce.fastandroiddev.example.fragment;

import com.ijustyce.fastandroiddev.base.BaseFragment;
import com.ijustyce.fastandroiddev.example.R;
import com.ijustyce.fastandroiddev.example.databinding.ContactFragmentView;
import com.ijustyce.fastandroiddev.example.model.UserInfo;

/**
 * Created by yc on 2016/9/5 0005.
 */

public class FragmentUser extends BaseFragment<ContactFragmentView>{

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public void afterCreate() {
        UserInfo userInfo = new UserInfo();
        userInfo.nickName.set(getResString(R.string.user_default_nickName));
        userInfo.phone.set(getResString(R.string.user_default_phone));
        contentView.setUser(userInfo);
    }
}