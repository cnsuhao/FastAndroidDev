package com.ijustzxh.bishe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ijustzxh.bishe.R;

/**
 * Created by zxh on 2016/1/14 0014.
 */
public class ThreePointFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mViewRecent == null){
            mViewRecent = inflater.inflate(R.layout.threepointfragemnt,container,false);
        }
        ViewGroup parent = (ViewGroup)mViewRecent.getParent();
        if (parent != null){
            parent.removeView(mViewRecent);
        }
        return mViewRecent;
    }
}
