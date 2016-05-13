package com.ijustyce.chat.fragment;

import android.content.Context;

import com.ijustyce.chat.adapter.ChatAdapter;
import com.ijustyce.chat.model.Record;
import com.ijustyce.fastandroiddev.base.BaseListFragment;
import com.ijustyce.fastandroiddev.irecyclerview.IAdapter;

import java.util.List;

public class ChatFragment extends BaseListFragment<Record> {

    @Override
    public Class getType() {
        return Record.class;
    }

    @Override
    public boolean getMoreData() {

        addData();
        return true;
    }

    private void addData(){


    }

    @Override
    public IAdapter<Record> buildAdapter(Context mContext, List<Record> data) {
        return new ChatAdapter(data, mContext);
    }
}
