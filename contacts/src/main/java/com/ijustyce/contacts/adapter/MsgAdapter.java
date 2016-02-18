package com.ijustyce.contacts.adapter;

import android.content.Context;

import com.ijustyce.contacts.R;
import com.ijustyce.contacts.model.MsgModel;
import com.ijustyce.fastandroiddev.base.CommonAdapter;
import com.ijustyce.fastandroiddev.base.ViewHolder;

import java.util.List;

/**
 * Created by yc on 16-2-8. 联系人列表适配器
 */
public class MsgAdapter extends CommonAdapter<MsgModel> {

    public MsgAdapter(Context context, List<MsgModel> datas) {
        super(context, datas, R.layout.item_msg);
    }

    @Override
    public void convert(ViewHolder holder, MsgModel object) {

        holder.setText(R.id.name, object.getName());
        holder.setText(R.id.msg, object.getMessage());
    }
}
