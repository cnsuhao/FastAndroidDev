package com.ijustyce.fastandroiddev.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev.baseLib.callback.CallBackManager;
import com.ijustyce.fastandroiddev.manager.AppManager;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by yc on 15-12-25.   底部是tab的activity的父类
 */
public abstract class BaseTabActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TextView label;

    public List<Fragment> mFragmentList;
    private List<RadioButton> mRadioButton;

    private boolean isPressed;
    private static final int DELAY = 2000, SHORT_DELAY = 1000;
    private boolean canClick = true;

    @Override
    void doInit() {
        View back = findViewById(R.id.back);
        if (back != null) back.setVisibility(View.GONE);
        initData();

        addFragment();
        setAdapter();
    }

    @Override
    public void doResume() {
        if (mFragmentList == null || mFragmentList.isEmpty())return;
        for (Fragment fragment : mFragmentList){
            fragment.onResume();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fastandroiddev_activity_tab;
    }

    private Runnable checkExit = new Runnable() {
        @Override
        public void run() {

            isPressed = false;
        }
    };

    @Override
    public void toolBarClick() {
        if (mViewPager == null) return;
        int id = mViewPager.getCurrentItem();
        if (id < 0 || mFragmentList == null || mFragmentList.isEmpty() || id >= mFragmentList.size()){
            return;
        }
        Fragment fragment = mFragmentList.get(id);
        if (fragment instanceof BaseFragment) ((BaseFragment)fragment).toolBarClick();
    }

    @Override
    public void toolBarDoubleClick() {
        if (mViewPager == null) return;
        int id = mViewPager.getCurrentItem();
        if (id < 0 || mFragmentList == null || mFragmentList.isEmpty() || id >= mFragmentList.size()){
            return;
        }
        Fragment fragment = mFragmentList.get(id);
        if (fragment instanceof BaseFragment) ((BaseFragment)fragment).toolBarDoubleClick();
    }

    public final void backPress() {

        if (isPressed) {
            this.finish();
        } else {
            isPressed = true;
            Toast.makeText(this, R.string.hint_exit, Toast.LENGTH_LONG).show();
            if (handler == null) {
                handler = new Handler();
            }
            handler.postDelayed(checkExit, DELAY);
        }
    }

    private void initData() {

        mTabLayout = (TabLayout) findViewById(R.id.tabTitle);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        label = (TextView) findViewById(R.id.label);
        mFragmentList = new ArrayList<>();
        mRadioButton = new ArrayList<>(mFragmentList.size());
    }

    public final int getCurrentTab() {

        return mViewPager == null ? 0 : mViewPager.getCurrentItem();
    }

    /**
     * 添加fragment
     */
    public abstract void addFragment();

    public void onPageSelect(int position) {
    }

    /**
     * 用adapter 关联起来
     */
    private void setAdapter() {

        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),
                mFragmentList, null);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size() > 3 ? 3 : mFragmentList.size());
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                selectTab();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == ViewPager.SCROLL_STATE_IDLE) {

                    selectTab();    //  这句才是关键，滚动结束后调用
                    canClick = true;    //  canClick 仅仅是为了防止多处点击
                }
            }
        });
    }

    private void selectTab() {

        int position = mViewPager.getCurrentItem();
        if (position >= mRadioButton.size()) {
            return;
        }

        for (RadioButton tmp : mRadioButton) {
            tmp.setChecked(false);
        }
        RadioButton tmp = mRadioButton.get(position);
        tmp.setChecked(true);
        label.setText(tmp.getText());

        onPageSelect(position);
    }

    public final void addTab(int layoutId, int radioButtonId) {

        if (mRadioButton == null) {
            ILog.e("===mTitleList or mRadioButton is null ...return...");
            return;
        }

        final int tabId = mRadioButton.isEmpty() ? 0 : mRadioButton.size();

        final TabLayout.Tab mTab = mTabLayout.getTabAt(tabId);
        if (mTab == null) {
            return;
        }

        View tab = LayoutInflater.from(this).inflate(layoutId, null);
        RadioButton button = (RadioButton) tab.findViewById(radioButtonId);
        button.setChecked(mRadioButton.isEmpty());

        if (mRadioButton.isEmpty()) {
            label.setText(button.getText());
        }

        //  必须要在button 上添加监听,view上不起作用
        button.setId(tabId);
        button.setOnClickListener(onClick);
        mTab.setCustomView(tab);
        mRadioButton.add(button);
    }

    private Runnable enAbleClick = new Runnable() {
        @Override
        public void run() {
            canClick = true;
        }
    };

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            ILog.i("===tab click===");
            if (view == null || !canClick) {
                ILog.e("===view is null or click not finish===");
                selectTab();    //  及时修复页面显示，而不是等到滚动结束
                return;
            }
            canClick = false;
            mViewPager.setCurrentItem(view.getId(), true);
            handler.postDelayed(enAbleClick, SHORT_DELAY);  //  为防止万一，1秒后允许再次点击
        }
    };

    public final void setScrollMode() {

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}