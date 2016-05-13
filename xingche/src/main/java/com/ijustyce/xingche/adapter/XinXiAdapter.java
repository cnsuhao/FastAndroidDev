package com.ijustyce.xingche.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.ijustyce.fastandroiddev.irecyclerview.CommonHolder;
import com.ijustyce.fastandroiddev.irecyclerview.IAdapter;
import com.ijustyce.xingche.R;
import com.ijustyce.xingche.activity.VideoPlay;
import com.ijustyce.xingche.model.Record;

import java.util.List;

/**
 * Created by yc on 16-4-29.    推荐适配器
 */
public class XinXiAdapter extends IAdapter<Record> {

    public XinXiAdapter(List<Record> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public CommonHolder createViewHolder(Context mContext, ViewGroup parent) {
        return CommonHolder.getInstance(R.layout.item_news, mContext, parent);
    }

    @Override
    public void createView(CommonHolder commonHolder, final Record object) {
        commonHolder.setText(R.id.name, object.getDate());
        commonHolder.setText(R.id.phone, object.getDesc());
        commonHolder.setOnClickListener(R.id.item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VideoPlay.class);
                intent.putExtra("uri", object.getUrl());
                getContext().startActivity(intent);
            }
        });
    }
}
