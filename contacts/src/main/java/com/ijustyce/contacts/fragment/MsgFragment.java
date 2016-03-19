package com.ijustyce.contacts.fragment;

import android.content.Context;
import android.widget.BaseAdapter;

import com.ijustyce.contacts.adapter.MsgAdapter;
import com.ijustyce.contacts.model.MsgModel;
import com.ijustyce.fastandroiddev.base.BaseListFragment;

import java.util.List;

/**
 * Created by yc on 16-2-8. 短信会话列表
 */
public class MsgFragment extends BaseListFragment<MsgModel> {

    MsgAdapter adapter;
    @Override
    public boolean getMoreData() {

        showData();
        return true;
    }

    private void showData(){

        data.clear();
        for (int i=0 ; i < 10; i++){

            MsgModel tmp = new MsgModel();
            tmp.setName("小丫头");
            tmp.setMessage("我想你了，怎么办呢？");
            data.add(tmp);
        }

        handler.post(newData);
    }

    @Override
    public Class getType() {
        return null;
    }

    @Override
    public BaseAdapter buildAdapter(Context mContext, List<MsgModel> data) {

        if (adapter == null){
            adapter = new MsgAdapter(mContext, data);
        }
        return adapter;
    }
}