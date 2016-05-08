package com.ijustyce.diancan.fragment;

import android.content.Context;
import android.os.Bundle;

import com.ijustyce.fastandroiddev.base.BaseListFragment;
import com.ijustyce.fastandroiddev.irecyclerview.IAdapter;
import com.ijustyce.diancan.adapter.TuiJianAdapter;
import com.ijustyce.diancan.http.HttpNews;
import com.ijustyce.diancan.model.TuiJianItem;
import com.ijustyce.diancan.model.TuiJianModel;

import java.util.List;

public class NewsFragment extends BaseListFragment<TuiJianItem> {

    private int type = -1;
    @Override
    public Class getType() {
        return TuiJianModel.class;
    }

    @Override
    public boolean getMoreData() {

        switch (getNewsType()){
            case 0:
                return HttpNews.listToutTiao(getTAG(), pageNo, mContext, httpListener);

            case 1:
                return HttpNews.listSport(getTAG(), pageNo, mContext, httpListener);

            case 2:
                return HttpNews.listKeJi(getTAG(), pageNo, mContext, httpListener);

            case 3:
                return HttpNews.listYule(getTAG(), pageNo, mContext, httpListener);
        }
        return HttpNews.listTuiJian(getTAG(), pageNo, mContext, httpListener);
    }

    private int getNewsType(){
        if (type != -1) return type;
        Bundle bundle = getArguments();
        return type = bundle == null ? -1 : bundle.getInt("type");
    }

    @Override
    public IAdapter<TuiJianItem> buildAdapter(Context mContext, List<TuiJianItem> data) {
        return new TuiJianAdapter(data, mContext);
    }
}
