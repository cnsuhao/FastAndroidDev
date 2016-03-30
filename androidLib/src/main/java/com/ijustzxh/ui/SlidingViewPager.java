package com.ijustzxh.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class SlidingViewPager extends ViewPager{

    private boolean isCanScroll = true ;
    public SlidingViewPager(Context context) {
        super(context);
    }

    public SlidingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll ;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
       // return super.onTouchEvent(ev);
        if(!isCanScroll){
            return true ;
        }else{
            return super.onTouchEvent(ev);
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }
}
