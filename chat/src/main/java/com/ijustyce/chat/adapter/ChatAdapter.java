package com.ijustyce.chat.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.ijustyce.chat.R;
import com.ijustyce.chat.model.Record;
import com.ijustyce.fastandroiddev.irecyclerview.CommonHolder;
import com.ijustyce.fastandroiddev.irecyclerview.IAdapter;

import java.util.List;

/**
 * Created by yc on 16-4-29.    推荐适配器
 */
public class ChatAdapter extends IAdapter<Record> {

    public ChatAdapter(List<Record> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public CommonHolder createViewHolder(Context mContext, ViewGroup parent) {
        return CommonHolder.getInstance(R.layout.item_common, mContext, parent);
    }

    @Override
    public void createView(final CommonHolder commonHolder, final Record object) {
        commonHolder.setText(R.id.name, object.getFrom());
        commonHolder.setText(R.id.phone, object.getMsg());
    //    ImageUtils.load(getContext(), (ImageView) commonHolder.getView(R.id.header), Constant.UPLOAD_DIR + object.getImg());
    }
}
