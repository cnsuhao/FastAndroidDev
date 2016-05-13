package com.ijustyce.chat.fragment;

import android.content.Context;

import com.ijustyce.chat.adapter.FriendsAdapter;
import com.ijustyce.chat.model.UserInfo;
import com.ijustyce.fastandroiddev.base.BaseListFragment;
import com.ijustyce.fastandroiddev.irecyclerview.IAdapter;

import java.util.List;

public class FriendsFragment extends BaseListFragment<UserInfo.DataEntity> {

    @Override
    public Class getType() {
        return UserInfo.class;
    }

    @Override
    public boolean getMoreData() {
        return true;
    }

    @Override
    public IAdapter<UserInfo.DataEntity> buildAdapter(Context mContext, List<UserInfo.DataEntity> data) {
        return new FriendsAdapter(data, mContext);
    }
}
