package com.ijustyce.fastandroiddev.base;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev.baseLib.utils.StringUtils;
import com.ijustyce.fastandroiddev.glide.ImageLoader;

import java.util.HashMap;

/**
 * Created by yc on 2016/9/4 0004.
 */

public class BaseViewModel extends BaseObservable {

    /**
     * 加载图片
     */
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        ImageLoader.load(view, imageUrl);
    }

    /**
     * 加载图片，指定宽高
     */
    @BindingAdapter({"imageUrl", "width", "height"})
    public static void loadImageWithSize(ImageView view, String imageUrl, int width, int height){
        ImageLoader.load(view, imageUrl, width, height);
    }

    /**
     * 加载圆形图片
     */
    @BindingAdapter({"imageUrl", "circle"})
    public static void loadCircle(ImageView view, String imageUrl, int circle){
        if (circle == 1) {
            ImageLoader.loadCircle(view, imageUrl);
        }else{
            ImageLoader.load(view, imageUrl);
        }
    }

    /**
     * 加载圆形图片，指定宽高
     */
    @BindingAdapter({"imageUrl", "width", "height", "circle"})
    public static void loadCircle(ImageView view, String imageUrl, int width, int height, int circle){
        if (circle == 1) {
            ImageLoader.loadCircle(view, imageUrl, width, height);
        }else{
            ImageLoader.load(view, imageUrl, width, height);
        }
    }

    /**
     * 加载圆形图片
     */
    @BindingAdapter({"imageUrl", "corner", "type"})
    public static void loadWithCorner(ImageView view, String imageUrl, int corner, int type){
        if (corner > 0) {
            ImageLoader.loadWidthCorner(view, imageUrl, corner, type);
        }else{
            ImageLoader.load(view, imageUrl);
        }
    }

    /**
     * 加载圆形图片，指定宽高
     */
    @BindingAdapter({"imageUrl", "width", "height", "corner", "type"})
    public static void loadWithCorner(ImageView view, String imageUrl, int width, int height, int corner, int type){
        if (corner > 0) {
            ImageLoader.loadWidthCorner(view, imageUrl, width, height, corner, type);
        }else{
            ImageLoader.load(view, imageUrl, width, height);
        }
    }
}
