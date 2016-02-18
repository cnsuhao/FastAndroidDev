package com.ijustyce.fastandroiddev.base;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.manager.AppManager;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by yc on 15-12-25.   引导页的封装
 */
public abstract class BaseGuideActivity extends AutoLayoutActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public List<String> mTitleList;
    public List<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fragment);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        AppManager.pushActivity(this);

        initData();

        addTitle();
        addFragment();
        setAdapter();
    }

    public void setTabIndicatorHeight(int height){

        mTabLayout.setSelectedTabIndicatorHeight(height);
    }

    private int getResColor(int color){

        return getResources().getColor(color);
    }

    public void setTabIndicatorColor(int color){

        mTabLayout.setSelectedTabIndicatorColor(getResColor(color));
    }

    public void setTabTextColor(int normalColor, int selectedColor){

        mTabLayout.setTabTextColors(getResColor(normalColor), getResColor(selectedColor));
    }

    private void initData(){

        mTabLayout = (TabLayout) findViewById(R.id.tabTitle);
        mTabLayout.setVisibility(View.GONE);

        findViewById(R.id.appBar).setVisibility(View.GONE);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
    }

    /**
     * 添加tab 标题
     */
    public abstract void addTitle();

    /**
     * 添加fragment
     */
    public abstract void addFragment();

    /**
     *  用adapter 关联起来
     */
    private void setAdapter() {

        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),
                mFragmentList, mTitleList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size() -1);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void setScrollMode(){

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        AppManager.moveActivity(this);
    }
}
