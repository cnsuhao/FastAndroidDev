package com.ijustzxh.bishe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ijustzxh.bishe.R;
import com.ijustzxh.bishe.activity.AboutActivity;
import com.ijustzxh.bishe.activity.PersonInfoActivity;
import com.ijustzxh.bishe.activity.PersonSetActivity;

/**
 * Created by zxh on 2016/1/13 0013.
 */
public class PersonFragment extends BaseFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mViewRecent == null){
            mViewRecent = inflater.inflate(R.layout.fragment_person,container,false);
            mViewRecent.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), PersonInfoActivity.class));
                }
            });
            mViewRecent.findViewById(R.id.personLayout7).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), AboutActivity.class));
                }
            });
            mViewRecent.findViewById(R.id.personLayout4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), PersonSetActivity.class));
                }
            });
        }
        ViewGroup parent = (ViewGroup)mViewRecent.getParent();
        if (parent != null){
            parent.removeView(mViewRecent);
        }
        return mViewRecent;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
