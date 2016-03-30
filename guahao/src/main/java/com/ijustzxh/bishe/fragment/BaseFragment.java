package com.ijustzxh.bishe.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by zxh on 2015/5/6 0006.
 */
public class BaseFragment extends Fragment {

    public String TAG = "zxh";
    public View mViewRecent;
    public Context context;

    @Override
    public void onResume(){

        super.onResume();
        context = getActivity();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString("FOR_BUG", "FOR_BUG");
        super.onSaveInstanceState(outState);
    }
}
