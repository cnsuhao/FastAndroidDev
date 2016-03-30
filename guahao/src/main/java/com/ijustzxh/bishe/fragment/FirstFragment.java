package com.ijustzxh.bishe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ijustyce.fastandroiddev.ui.SlidingViewPager;
import com.ijustzxh.bishe.R;
import com.ijustzxh.bishe.activity.LoginActivity;
import com.ijustzxh.bishe.adapter.MainPagerViewAdapter;

import java.util.ArrayList;

/**
 * Created by zxh on 2016/1/13 0013.
 */
public class FirstFragment extends BaseFragment {

    private SlidingViewPager mMainViewPager;
    private ArrayList<Fragment> mListFragment;
    private MainPagerViewAdapter mViewPagerAdapter;
    private Handler handler;

    private int current;
    private final static int DELAY = 9000; //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mViewRecent == null){
            mViewRecent = inflater.inflate(R.layout.fragment_order,container,false);
            mViewRecent.findViewById(R.id.orderLogin).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
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
        handler = new Handler();
        initViewPager();
    }

    private void initViewPager() {

        mListFragment = new ArrayList<>();

        mListFragment.add(new OnePointFragment());
        mListFragment.add(new TwoPointFragment());
        mListFragment.add(new ThreePointFragment());

        mMainViewPager = (SlidingViewPager) mViewRecent.findViewById(R.id.order_pager);
        mMainViewPager.setCanScroll(true);
        mViewPagerAdapter = new MainPagerViewAdapter(getChildFragmentManager(), mListFragment);
        mMainViewPager.setAdapter(mViewPagerAdapter);

        handler.post(scroll);
    }
    private Runnable scroll = new Runnable() {
        @Override
        public void run() {

            if (mMainViewPager != null && mListFragment != null && !mListFragment.isEmpty()){

                if (current < mListFragment.size()){
                    mMainViewPager.setCurrentItem(current, true);
                    current++;
                }else{
                    current = 0;
                }
                handler.postDelayed(scroll, DELAY);
            }else{
                handler.removeCallbacksAndMessages(null);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null){
            handler.removeCallbacksAndMessages(null);
        }
    }
}
