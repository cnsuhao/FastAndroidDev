package com.ijustyce.contacts.fragment;

import android.content.Context;
import android.widget.BaseAdapter;

import com.ijustyce.contacts.adapter.RecordAdapter;
import com.ijustyce.contacts.constant.Constant;
import com.ijustyce.contacts.model.RecordModel;
import com.ijustyce.fastandroiddev.base.BaseListFragment;
import com.ijustyce.fastandroiddev.baseLib.utils.DateUtil;

import java.util.List;

/**
 * Created by yc on 16-2-8. 通话记录列表
 */
public class RecordFragment extends BaseListFragment<RecordModel> {

    RecordAdapter adapter;
    @Override
    public boolean getMoreData() {

        showData();
        return true;
    }

    private void showData(){

        data.clear();
        for (int i=0 ; i < 10; i++){

            RecordModel tmp = new RecordModel();
            tmp.setName("小丫头");
            tmp.setDate(DateUtil.getDateString(Constant.FULL_TIME_FORMATTER));
            tmp.setType(i % 3);
            data.add(tmp);
        }

        handler.post(newData);
    }

    @Override
    public BaseAdapter buildAdapter(Context mContext, List<RecordModel> data) {

        if (adapter == null){
            adapter = new RecordAdapter(mContext, data);
        }
        return adapter;
    }
}
