package com.ijustyce.fastandroiddev.example.activity;

import com.ijustyce.fastandroiddev.base.BaseListActivity;
import com.ijustyce.fastandroiddev.example.BR;
import com.ijustyce.fastandroiddev.example.EventHandler.ShowMVVMEvent;
import com.ijustyce.fastandroiddev.example.R;
import com.ijustyce.fastandroiddev.example.model.User;
import com.ijustyce.fastandroiddev.irecyclerview.BindingInfo;
/**
 * Created by yangchun on 16/7/19.
 */

public class RecyclerViewActivity extends BaseListActivity<User> {

    @Override
    public Class getType() {
        return User.class;
    }

    @Override
    public boolean getMoreData() {
        data.clear();
        for (int i =0; i < 100; i++){
            User tmp = new User();
            tmp.setAge(20 + i);
            tmp.setName("tmp " + i);
            data.add(tmp);
        }
        adapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public BindingInfo getBindingInfos() {
        ShowMVVMEvent event = new ShowMVVMEvent(this);
        return new BindingInfo(R.layout.item_user, BR.user, null).add(BR.handler, event);
    }
}
