package com.ijustyce.fastandroiddev.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev.baseLib.callback.CallBackManager;
import com.ijustyce.fastandroiddev.manager.AppManager;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by yc on 15-12-25. baseFragmentActivity
 */
public abstract class BaseFragmentActivity extends AutoLayoutActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LinearLayout extraView;

    public Context mContext;
    public List<String> mTitleList;
    public List<Fragment> mFragmentList;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        ButterKnife.bind(this);

        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        AppManager.pushActivity(this);
        initData();

        addTitle();
        addFragment();
        setAdapter();

        CallBackManager.getActivityLifeCall().onCreate();
    }

    public void setTabBackground(int color){

        mTabLayout.setBackgroundColor(getResColor(color));
    }

    public void setCurrentFragment(int id){

        if (mViewPager.getAdapter() == null) {
            ILog.e(getLocalClassName() , "===this function must called after addTitle and addFragment, " +
                    "or will not work ...");
        }
        if (id > -1 && id < mFragmentList.size() && mViewPager != null){
            mViewPager.setCurrentItem(id, true);
        }else{
            ILog.e("===BaseTabFragment===", "setCurrentFragment, id overflow or mViewPager is null");
        }
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
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        extraView = (LinearLayout) findViewById(R.id.extraView);
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
    }

    public void addHeaderView(View view){

        if (extraView != null && view != null){
            extraView.addView(view);
        }
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
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public String getResString(int id){

        return getResources().getString(id);
    }

    @Override
    public void onDestroy(){

        super.onDestroy();
        ButterKnife.unbind(this);
        AppManager.moveActivity(this);
        CallBackManager.getActivityLifeCall().onDestroy();
    }

    public void setScrollMode(){

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    //  你可以在这里加入界面切换的动画，或者统计页面等
    public void newActivity(Intent intent) {

        startActivity(intent);
    }
    public void newActivity(Class<?> gotoClass) {

        newActivity(new Intent(this, gotoClass));
    }

    @Override
    protected void onStop() {
        super.onStop();
        CallBackManager.getActivityLifeCall().onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CallBackManager.getActivityLifeCall().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CallBackManager.getActivityLifeCall().onResume();
    }
}
