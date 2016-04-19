package com.ijustyce.fastandroiddev.base;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.baseLib.utils.IJson;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev.irecyclerview.IAdapter;
import com.ijustyce.fastandroiddev.irecyclerview.IRecyclerView;
import com.ijustyce.fastandroiddev.irecyclerview.PullToRefreshListener;
import com.ijustyce.fastandroiddev.net.IResponseData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 2015/12/11 0011.    列表Fragment的父类
 */
public abstract class BaseListFragment<T> extends BaseFragment {

    public IRecyclerView mIRecyclerView;
    public LinearLayout noData;

    private static final String FORMATTER = "yyyy-MM-dd HH:mm";

    public Handler handler;
    public IAdapter<T> adapter;
    private List<T> data;

    public int pageNo = 1;

    public static final int SHORT_DELAY = 100; // 刷新间隔

    @Override
    final void doInit() {

        init();
    }

    public final void refresh() {

        doResume();
    }

    @Override
    public void doResume() {

        //  刷新数据
        if (mIRecyclerView != null) {
            mIRecyclerView.onRefresh();
        }
    }

    public abstract Class getType();

    @Override
    public int getLayoutId() {
        return R.layout.fastandroiddev_fragment_list_common;
    }

    @Override
    public final void onSuccess(String object, String taskId) {
        if (data == null) {
            handler.post(hasNoData);
            return;
        }
        if (pageNo == 1){
            data.clear();
        }

        Object result = IJson.fromJson(object, getType());
        if (result instanceof IResponseData){

            List<T> objectsList = ((IResponseData<T>)result).getData();
            if (objectsList != null && !objectsList.isEmpty()){
                data.addAll(objectsList);
                handler.post(newData);
            }else{
                handler.post(hasNoData);
            }
        }
    }

    public final T getById(int position){

        if (position < 0 || position >= data.size()){
            return null;
        }
        return data.get(position);
    }

    @Override
    public void onFailed(int code, String msg, String taskId) {

        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        handler.post(hasNoData);
    }

    private void init() {

        mIRecyclerView = (IRecyclerView) mView.findViewById(R.id.recyclerView);
        noData = (LinearLayout) mView.findViewById(R.id.noData);

        if (noData != null){
            noData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIRecyclerView.onRefresh();
                }
            });
        }

        handler = new Handler();
        pageNo = 1;
        mIRecyclerView.setHasMore(true);
        mIRecyclerView.setPullToRefreshListener(refreshListener);
        data = new ArrayList<>();
        adapter = buildAdapter(mContext, data);
        if(adapter == null){
            ILog.e("===BaseListActivity===", "adapter can not be null ...");
            return;
        }
        mIRecyclerView.setAdapter(adapter);
    }

    private PullToRefreshListener refreshListener = new PullToRefreshListener() {
        @Override
        public void onRefresh() {

            if (noData == null) {  //  发生异常
                handler.post(hasNoData);
                return;
            }
            noData.setVisibility(View.GONE);  //  隐藏没有数据时，显示的view

            pageNo = 1;
            mIRecyclerView.setHasMore(true);
            if (!getMoreData()) {
                handler.post(hasNoData);
            }
        }

        @Override
        public void onLoadMore() {

            pageNo++;
            getMoreData();
        }
    };

    //  获取更多数据
    public abstract boolean getMoreData();

    public abstract IAdapter<T> buildAdapter(Context mContext, List<T> data);

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mContext != null) {
            mContext = null;
        }
        if (adapter != null) {
            adapter = null;
        }
        if (handler != null) {
            handler = null;
        }
        if (data != null) {
            data = null;
        }
    }

    public final Runnable newData = new Runnable() {
        @Override
        public void run() {

            mIRecyclerView.onLoadEnd();
            mIRecyclerView.onRefreshEnd();
            if (mContext != null && adapter != null && data != null) {
                adapter.notifyDataSetChanged();
            }

            if ((data == null || !data.isEmpty()) && noData != null) {
                noData.setVisibility(View.INVISIBLE);
            }
        }
    };

    public final Runnable hasNoData = new Runnable() {
        @Override
        public void run() {
            mIRecyclerView.onLoadEnd();
            mIRecyclerView.onRefreshEnd();

            mIRecyclerView.setHasMore(false);

            if (adapter != null){
                adapter.notifyDataSetChanged();
            }

            if (data != null && data.isEmpty() && noData != null) {
                noData.setVisibility(View.VISIBLE);
            }
        }
    };
}
