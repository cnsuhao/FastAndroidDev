package com.ijustyce.fastandroiddev.irecyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by yangchun on 16/4/15.  通用的RecyclerView adapter
 */
public class IAdapter<T> extends RecyclerView.Adapter<IBindingHolder> {

    private List<T> mData;
    private Context mContext;
    private View mHeaderView, mFooterView;
    private int position;
    private RecyclerView recyclerView;
    private Handler handler;
    static final int TYPE_FOOTER = -10, TYPE_HEADER = -20;
    public int layoutId;
    private BindingInfo bindingInfos;

    public IAdapter(Context mContext, List<T> mData, @NonNull BindingInfo bindingInfos) {

        this.mData = mData;
        this.mContext = mContext;
        handler = new Handler();
        this.layoutId = bindingInfos.layoutId;
        this.bindingInfos = bindingInfos;
    }

    public IAdapter(Context mContext, List<T> mData){
        this.mContext = mContext;
        this.mData = mData;
        handler = new Handler();
    }

    public final Context getContext(){
        return mContext;
    }

    public final void removeItem(int position){

        if (mData == null || position < 0 || position >= mData.size()) return;
        mData.remove(position);
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
        this.mFooterView = mFooterView;
    }

    public final void setHeaderView(View mHeaderView) {

        if (mHeaderView == null){
            return;
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
            return mHeaderView == null ? position : position-1;
        }
    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }

    @Override
    public final int getItemCount() {
        int size = getDataSize();
        if (mFooterView != null) size++;
        if (mHeaderView != null) size++;
        return size;
    }

    @Override
    public final IBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case TYPE_FOOTER:
                return new IBindingHolder(mFooterView, mContext);

            case TYPE_HEADER:
                return new IBindingHolder(mHeaderView, mContext);

            default:
                parent.setTag(R.string.tag_item_position, viewType);
                IBindingHolder holder = createViewHolder(mContext, parent);
                AutoUtils.autoSize(holder.itemView);
                return holder;
        }
    }

    public IBindingHolder createViewHolder(Context mContext, ViewGroup parent){
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext), layoutId, parent, false);
        IBindingHolder holder = new IBindingHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }


    @Override
    public final void onBindViewHolder(IBindingHolder holder, int position) {

        this.position = position;
        if ((position == 0 && mHeaderView != null) || (position == getItemCount() -1 && mFooterView != null)){

            ILog.i("===object===", "is footer or header not OnBinding ...");
        }else {
            if (holder == null) return;
            if (holder.getBinding() != null) {
                holder.itemPosition = position;
                T item = getObject(mHeaderView == null ? position : position-1);//  扣除header占用的位置
                ViewDataBinding binding = holder.getBinding();
                for (int i =0; i < bindingInfos.size; i++){
                    Object value = bindingInfos.info.valueAt(i);
                    binding.setVariable(bindingInfos.info.keyAt(i), value == null ? item : value);
                }
                binding.executePendingBindings();
            }
        }
    }

    /**
     * 处理之前的任务，比如itemchanged、datachanged、itemremoved等 一般不需要手动触发
     */
    public final void doEvent(){
        if (recyclerView == null || recyclerView.isComputingLayout()) {
            doDelayEvent();
            return;
        }
        if (handler != null) handler.removeCallbacksAndMessages(null);
        int size = changedItem == null ? 0 : changedItem.size();
        for (int i = 0; i < size; i++){
            int position = changedItem.indexOfKey(i);
            int type = changedItem.indexOfValue(i);
            switch (type){
                case 1:
                    itemChanged(position);
                    break;
                case 2:
                    removeItem(position);
                    break;
                case 3:
                    itemInsert(position);
                    break;
                case 4:
                    dataChanged();
                    break;

            }
        }
    }

    private void doDelayEvent(){
        if (mData == null || mContext == null){
            ILog.i("===IAdapter===", "mData or mContext is null, destroy handler ...");
            mData = null;
            mContext = null;
            if (handler != null){
                handler.removeCallbacksAndMessages(null);
                handler = null;
            }
            return;
        }
        if (handler == null) return;
        if (changedItem == null || changedItem.size() < 1) handler.removeCallbacksAndMessages(null);
        else handler.postDelayed(run, 737);
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            doEvent();
        }
    };

    private SparseIntArray changedItem = new SparseIntArray(); //  key is position and value is type, 1->changed, 2->remove, 3->insert, 4->DataChanged
    public final void itemChanged(int position){
        if (recyclerView != null && !recyclerView.isComputingLayout()) notifyItemChanged(position);
        else {
            changedItem.put(position, 1);
            doDelayEvent();
        }
    }

    public final void itemRemove(int position){
        if (recyclerView != null && !recyclerView.isComputingLayout()) notifyItemRemoved(position);
        else {
            changedItem.put(position, 2);
            doDelayEvent();
        }
    }

    public final void itemInsert(int position){
        if (recyclerView != null && !recyclerView.isComputingLayout()) notifyItemInserted(position);
        else {
            changedItem.put(position, 3);
            doDelayEvent();
        }
    }

    public final void dataChanged(){
        if (recyclerView != null && !recyclerView.isComputingLayout()) notifyDataSetChanged();
        else {
            changedItem.put(4, 0);
            doDelayEvent();
        }
    }

    public final T getObject(int position) {

        if (mData == null || position < 0 || position >= mData.size()) {

            return null;
        }
        return mData.get(position);
    }
}