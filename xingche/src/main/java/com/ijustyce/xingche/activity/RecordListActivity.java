package com.ijustyce.xingche.activity;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ijustyce.fastandroiddev.base.BaseListActivity;
import com.ijustyce.fastandroiddev.irecyclerview.IAdapter;
import com.ijustyce.xingche.R;
import com.ijustyce.xingche.adapter.XinXiAdapter;
import com.ijustyce.xingche.data.RecordDB;
import com.ijustyce.xingche.model.Record;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class RecordListActivity extends BaseListActivity<Record> {

    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.right)
    TextView right;
    @Override
    public Class getType() {
        return Record.class;
    }

    @Override
    public boolean getMoreData() {
        addData();
        return true;
    }

    @Override
    public void afterCreate() {
        editText.setVisibility(View.VISIBLE);
        right.setText("搜索");
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNo = 1;
                getMoreData();
            }
        });
    }

    private String getKey(){

        return editText.getText().toString();
    }

    private void addData(){

        if (data == null) data = new ArrayList<>();
        if (pageNo == 1) data.clear();
        List<Record> tmp = RecordDB.searchRecord(getKey(), 10, pageNo);
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