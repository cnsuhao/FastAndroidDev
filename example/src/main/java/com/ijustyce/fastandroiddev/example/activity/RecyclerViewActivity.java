package com.ijustyce.fastandroiddev.example.activity;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ijustyce.fastandroiddev.base.BaseListActivity;
import com.ijustyce.fastandroiddev.example.BR;
import com.ijustyce.fastandroiddev.example.EventHandler.ShowMVVMEvent;
import com.ijustyce.fastandroiddev.example.R;
import com.ijustyce.fastandroiddev.example.model.User;
import com.ijustyce.fastandroiddev.irecyclerview.IAdapter;
import com.ijustyce.fastandroiddev.irecyclerview.IBindingHolder;

import java.util.List;
/**
 * Created by yangchun on 16/7/19.
 */

public class RecyclerViewActivity extends BaseListActivity<User> {

    @Override
    public Class getType() {
        return null;
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
    public IAdapter<User> buildAdapter(Context mContext, List<User> data) {
        final ShowMVVMEvent event = new ShowMVVMEvent(RecyclerViewActivity.this);
        return new IAdapter<User>(mContext, data, R.layout.item_user) {
            @Override
            public void OnBinding(@NonNull IBindingHolder commonHolder, @NonNull User object) {
                commonHolder.getBinding().setVariable(BR.user, object);
                commonHolder.getBinding().setVariable(BR.handler, event);
            }
        };
    }
}
