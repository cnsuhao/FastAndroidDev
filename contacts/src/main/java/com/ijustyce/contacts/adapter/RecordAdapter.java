package com.ijustyce.contacts.adapter;

import android.content.Context;

import com.ijustyce.contacts.R;
import com.ijustyce.contacts.model.RecordModel;
import com.ijustyce.fastandroiddev.base.CommonAdapter;
import com.ijustyce.fastandroiddev.base.ViewHolder;

import java.util.List;

/**
 * Created by yc on 16-2-8. 联系人列表适配器
 */
public class RecordAdapter extends CommonAdapter<RecordModel> {

    private String[] typeArr = {"去电", "来电", "未接"};
    public RecordAdapter(Context context, List<RecordModel> datas) {
        super(context, datas, R.layout.item_record);
    }

    @Override
    public void convert(ViewHolder holder, RecordModel object) {

        holder.setText(R.id.name, object.getName());
        holder.setText(R.id.dateAndType, object.getDate() + "    " + getType(object.getType()));
    }

    private String getType(int type){

        if (type < 1 || type > 3){
            return "";
        }
        return typeArr[type -1];
    }
}
