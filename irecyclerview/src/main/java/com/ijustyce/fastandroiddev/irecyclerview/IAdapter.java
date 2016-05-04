package com.ijustyce.fastandroiddev.irecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ijustyce.fastandroiddev.baseLib.utils.ILog;

import java.util.List;

/**
 * Created by yangchun on 16/4/15.  通用的RecyclerView adapter
 */
public abstract class IAdapter<T> extends RecyclerView.Adapter<CommonHolder> {

    private List<T> mData;
    private Context mContext;
    private View mHeaderView, mFooterView;
    private int size, position;

    private static final int TYPE_FOOTER = 1, TYPE_HEADER = 2, TYPE_NORMAL = 3;

    public IAdapter(List<T> mData, Context mContext) {

        this.mData = mData;
        this.size = mData.size();
        this.mContext = mContext;
    }

    public final Context getContext(){
        return mContext;
    }

    void reAddFooter(){

        size = mData.size();
        if (mFooterView != null){
            size++;
        }if (mHeaderView != null){
            size++;
        }
    }

    public final void removeItem(int position){

        if (mData == null || position < 0 || position >= mData.size()) return;
        mData.remove(position);
        reAddFooter();
        notifyItemRemoved(position);
    }

    public final boolean isFooterVisible() {
        return position >= getItemCount() -2;
    }

    /**
     * 返回最后可见的item的position
     */
    public final int getLastPosition(){

        return position;
    }

    public final void setFooterView(View mFooterView) {

        if (mFooterView == null){
            return;
        }
        if (this.mFooterView == null){
            this.size++;
        }
        this.mFooterView = mFooterView;
    }

    public final void setHeaderView(View mHeaderView) {

        if (mHeaderView == null){
            return;
        }if (this.mHeaderView == null){
            this.size++;
        }
        this.mHeaderView = mHeaderView;
    }

    public final int getDataSize(){

        return mData == null ? 0 : mData.size();
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
        return size;
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

        this.position = position;
        if ((position == 0 && mHeaderView != null) || (position == size -1 && mFooterView != null)){

            ILog.i("===object===", "is footer or header not createView ...");
        }else {
            if (holder != null) holder.setItemPosition(position);
            createView(holder, getObject(mHeaderView == null ? position : position-1));  //  扣除header占用的位置
        }
    }

    public final T getObject(int position) {

        if (mData == null || position < 0 || position >= mData.size()) {

            return null;
        }
        return mData.get(position);
    }
}