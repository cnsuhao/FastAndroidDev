package com.ijustyce.fastandroiddev.irecyclerview;

import android.support.annotation.LayoutRes;
import android.util.SparseArray;

import com.ijustyce.fastandroiddev.R;

/**
 * Created by yangchun on 16/7/19.
 */

public class BindingInfo {

    public SparseArray<Object> info;
    public int size = 1;
    public int layoutId;

    @Deprecated
    public BindingInfo(@LayoutRes int layoutId, int key){
        this.layoutId = layoutId;
        info = new SparseArray<>();
        info.put(key, null);
    }

    public static BindingInfo createByLayoutIdAndBindName(@LayoutRes int layoutId, int key){
        return new BindingInfo(layoutId, key);
    }

    public BindingInfo add(int key, Object value){
        info.put(key, value);
        size = info.size();
        return this;
    }
}
