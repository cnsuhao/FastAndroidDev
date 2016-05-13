package com.ijustyce.xingche.fragment;

import android.content.Context;
import android.os.Handler;

import com.ijustyce.fastandroiddev.base.BaseListFragment;
import com.ijustyce.fastandroiddev.irecyclerview.IAdapter;
import com.ijustyce.xingche.adapter.XinXiAdapter;
import com.ijustyce.xingche.data.RecordDB;
import com.ijustyce.xingche.model.Record;

import java.util.ArrayList;
import java.util.List;

public class RecordListFragment extends BaseListFragment<Record> {

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

        if (data == null) data = new ArrayList<>();
        if (pageNo == 1) data.clear();
        List<Record> tmp = RecordDB.listRecord(10, pageNo);
        if (handler == null) handler = new Handler();
        if (tmp == null){
            handler.post(hasNoData);
         }
        else {
            data.addAll(tmp);
            handler.post(newData);
        }
    }

    @Override
    public IAdapter<Record> buildAdapter(Context mContext, List<Record> data) {
        return new XinXiAdapter(data, mContext);
    }
}