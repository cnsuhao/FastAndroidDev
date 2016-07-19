package com.ijustyce.fastandroiddev.irecyclerview;

import android.support.annotation.LayoutRes;
import android.util.SparseArray;

/**
 * Created by yangchun on 16/7/19.
 */

public class BindingInfo {

    public SparseArray<Object> info;
    public int size = 1;
    public int layoutId;

    public BindingInfo(@LayoutRes int layoutId, int key, Object value){
        this.layoutId = layoutId;
        info = new SparseArray<>();
        info.put(key, value);
    }

    public BindingInfo add(int key, Object value){
        info.put(key, value);
        size = info.size();
        return this;
    }
}
