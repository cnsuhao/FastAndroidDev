package com.ijustyce.contacts.adapter;

import android.content.Context;

import com.ijustyce.contacts.R;
import com.ijustyce.contacts.model.FriendsModel;
import com.ijustyce.fastandroiddev.base.CommonAdapter;
import com.ijustyce.fastandroiddev.base.ViewHolder;

import java.util.List;

/**
 * Created by yc on 16-2-8. 联系人列表适配器
 */
public class FriendsAdapter extends CommonAdapter<FriendsModel> {

    public FriendsAdapter(Context context, List<FriendsModel> datas) {
        super(context, datas, R.layout.item_friends);
    }

    @Override
    public void convert(ViewHolder holder, FriendsModel object) {

        holder.setText(R.id.name, object.getName());
        holder.setText(R.id.phone, object.getPhone());
    }
}
