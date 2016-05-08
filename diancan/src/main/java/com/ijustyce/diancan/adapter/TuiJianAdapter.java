package com.ijustyce.diancan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ijustyce.fastandroiddev.baseLib.utils.ImageUtils;
import com.ijustyce.fastandroiddev.irecyclerview.CommonHolder;
import com.ijustyce.fastandroiddev.irecyclerview.IAdapter;
import com.ijustyce.diancan.R;
import com.ijustyce.diancan.activity.WebViewActivity;
import com.ijustyce.diancan.model.TuiJianItem;

import java.util.List;

/**
 * Created by yc on 16-4-29.    推荐适配器
 */
public class TuiJianAdapter extends IAdapter<TuiJianItem>{

    public TuiJianAdapter(List<TuiJianItem> mData, Context mContext) {
        super(mData, mContext);
    }

    @Override
    public CommonHolder createViewHolder(Context mContext, ViewGroup parent) {
        return CommonHolder.getInstance(R.layout.item_news, mContext, parent);
    }

    @Override
    public void createView(CommonHolder commonHolder, final TuiJianItem object) {
        commonHolder.setText(R.id.name, object.getTitle());
        commonHolder.setText(R.id.phone, object.getIntro());
        ImageUtils.load(getContext(), (ImageView)commonHolder.getView(R.id.header), object.getKpic());
        commonHolder.setOnClickListener(R.id.item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("url", object.getLink());
                getContext().startActivity(intent);
            }
        });
    }
}
