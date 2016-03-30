package com.ijustzxh.bishe.activity;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.ijustyce.fastandroiddev.ui.SlidingViewPager;
import com.ijustzxh.bishe.R;
import com.ijustzxh.bishe.adapter.MainPagerViewAdapter;
import com.ijustzxh.bishe.data.Constant;
import com.ijustzxh.bishe.fragment.MyOrderFragment;
import com.ijustzxh.bishe.fragment.FirstFragment;
import com.ijustzxh.bishe.fragment.PersonFragment;

import java.util.ArrayList;


public  class MainActivity extends AppBaseFragmentActivity implements ViewPager.OnPageChangeListener,
        View.OnClickListener {

    private int offset = 0;
    private int currIndex = 0;
    private int bmpW;
    private ImageView cursor;

    private SlidingViewPager mMainViewPager;
    private RadioButton order, near, person;

    private MainPagerViewAdapter mViewPagerAdapter;
    private ArrayList<Fragment> mListFragment;
    private ArrayList<RadioButton> radioButtonList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitImageView();
        initViewPager();
        initRadioButton();
    }

    private void initViewPager() {

        mListFragment = new ArrayList<>();

        mListFragment.add(new FirstFragment());
        mListFragment.add(new MyOrderFragment());
        mListFragment.add(new PersonFragment());

        mMainViewPager = (SlidingViewPager) findViewById(R.id.msg_pager);
        mMainViewPager.setCanScroll(true);
        mViewPagerAdapter = new MainPagerViewAdapter(
                getSupportFragmentManager(), mListFragment);
        mMainViewPager.setAdapter(mViewPagerAdapter);
        mMainViewPager.setOnPageChangeListener(this);
    }

    private void initRadioButton() {

        order = (RadioButton) findViewById(R.id.activity_main_one);
        near = (RadioButton) findViewById(R.id.activity_main_two);
        person = (RadioButton) findViewById(R.id.activity_main_three);

        radioButtonList = new ArrayList<>();
        radioButtonList.add(order);
        radioButtonList.add(near);
        radioButtonList.add(person);

        order.setOnClickListener(this);
        near.setOnClickListener(this);
        person.setOnClickListener(this);
    }

    private void InitImageView() {
        cursor = (ImageView) findViewById(R.id.cursor);

        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.header_bg)
                .getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;

        RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) cursor.getLayoutParams();
        linearParams.width = screenW / 3;
        cursor.setLayoutParams(linearParams);

        offset = (screenW / 3 - bmpW) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_one:
                goToTab(Constant.FIRST_TAB);
                break;

            case R.id.activity_main_two:
                goToTab(Constant.SECOND_TAB);
                break;

            case R.id.activity_main_three:
                goToTab(Constant.THIRD_TAB);
                break;

            default:
                break;
        }
    }

    private void goToTab(int which) {

        switch (which) {

            case Constant.FIRST_TAB:
                order.setSelected(true);
                mMainViewPager.setCurrentItem(Constant.FIRST_TAB);
                break;

            case Constant.SECOND_TAB:
                near.setSelected(true);
                mMainViewPager.setCurrentItem(Constant.SECOND_TAB);
                break;

            case Constant.THIRD_TAB:
                person.setSelected(true);
                mMainViewPager.setCurrentItem(Constant.THIRD_TAB);
                break;

            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {


    }

    @Override
    public void onPageSelected(int arg0) {

        int one = offset * 2 + bmpW;
        int two = one * 2;

        Animation animation = null;
        switch (arg0) {
            case 0:
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                }
                break;
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                break;
            case 2:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, two, 0, 0);
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                }
                break;
        }

        currIndex = arg0;
        if(animation!=null){
            animation.setFillAfter(true);
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

        if (radioButtonList != null && radioButtonList.size() > 0) {

            radioButtonList.get(arg0).setChecked(true);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {

        super.finish();
        overridePendingTransition(0, R.anim.right_to_left);
    }
}


