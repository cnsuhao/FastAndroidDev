package com.ijustyce.fastandroiddev.irecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangchun on 16/4/15.  通用的RecyclerView adapter
 */
public abstract class IAdapter<T> extends RecyclerView.Adapter<CommonHolder> {

    private List<T> mData;
    private Context mContext;
    private View mHeaderView, mFooterView;
    private boolean isFooterVisible;

    private static final int TYPE_FOOTER = 1, TYPE_HEADER = 2, TYPE_NORMAL = 3;

    public IAdapter(List<T> mData, Context mContext) {

        this.mData = new ArrayList<>();
        this.mData.addAll(mData);
        this.mContext = mContext;
    }

    public boolean isFooterVisible() {
        return isFooterVisible;
    }

    public void setFooterView(View mFooterView) {

        if (mFooterView == null){
            return;
        }
        if (this.mFooterView == null){
            mData.add(mData.size(), null);
        }
        this.mFooterView = mFooterView;
    }

    public void setHeaderView(View mHeaderView) {

        if (mHeaderView == null){
            return;
        }if (this.mHeaderView == null){
            mData.add(0, null);
        }
        this.mHeaderView = mHeaderView;
    }

    @Override
    public final int getItemViewType(int position) {

        if (position == 0 && mHeaderView != null) {
            return TYPE_HEADER;
        }
        if (position + 1 == getItemCount() && mFooterView != null) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public final int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public final CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case TYPE_FOOTER:
                return new CommonHolder(mFooterView, mContext);

            case TYPE_HEADER:
                return new CommonHolder(mHeaderView, mContext);

            case TYPE_NORMAL:
                return createViewHolder(mContext, parent);

        }
        return null;
    }

    public abstract CommonHolder createViewHolder(Context mContext, ViewGroup parent);

    public abstract void createView(CommonHolder commonHolder, T object);

    @Override
    public final void onBindViewHolder(CommonHolder holder, int position) {

        isFooterVisible = position >= getItemCount() -2;
        createView(holder, getObject(position));
    }

    public T getObject(int position) {

        if (mData == null || position < 0 || position > mData.size()) {

            return null;
        }
        return mData.get(position);
    }
}
