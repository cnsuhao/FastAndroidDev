package com.ijustyce.fastandroiddev.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.ijustyce.fastandroiddev.IApplication;
import com.ijustyce.fastandroiddev.R;
import com.ijustyce.fastandroiddev.baseLib.utils.CommonTool;
import com.ijustyce.fastandroiddev.baseLib.utils.ILog;
import com.ijustyce.fastandroiddev.baseLib.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yangchun on 16/6/14.
 */
public class ImageLoader {

    private static int maxWidth;
    private static double wBili, hBili;
    private static NetworkDisablingLoader networkDisablingLoader;

    static {
        int target_width = StringUtils.getInt(CommonTool.getMetaData(IApplication.getInstance(),
                "design_width"));
        int target_height = StringUtils.getInt(CommonTool.getMetaData(IApplication.getInstance(),
                "design_height"));
        int mWidth = CommonTool.getScreenWidth(IApplication.getInstance());
        int mHeight = CommonTool.getScreenHeight(IApplication.getInstance());
        maxWidth = mWidth / 2;
        wBili = ((double) mWidth) / ((double) target_width);
        hBili = ((double) mHeight / ((double) target_height));
    }

    /**
     * @param img
     * @param url
     * @param type
     */
    public static final int TYPE_TOP = 1;
    public static final int TYPE_BOTTOM = 2;
    public static final int TYPE_LEFT = 3;
    public static final int TYPE_RIGHT = 4;
    public static final int TYPE_ALL = 5;

    public static void loadWidthCorner(ImageView img, String url, int width, int height,
                                       int radius, int type) {
        RoundedCornersTransformation transformation = null;
        if (type < TYPE_TOP || type > TYPE_RIGHT) {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.ALL);
        } else if (type == 1) {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.TOP);
        } else if (type == 2) {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.BOTTOM);
        } else if (type == 3) {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.LEFT);
        } else if (type == 4) {
            transformation = new RoundedCornersTransformation(img.getContext(), radius, 0,
                    RoundedCornersTransformation.CornerType.RIGHT);
        }
        load(img, url, width, height, transformation);
    }

    public static void loadWidthCorner(ImageView img, String url, int radius, int type) {
        loadWidthCorner(img, url, -1, -1, radius, type);
    }

    public static void loadCircle(ImageView img, String url) {
        loadCircle(img, url, -1, -1);
    }

    public static void loadCircle(ImageView img, String url, int width, int height) {
        Transformation transformation = new CropCircleTransformation(img.getContext());
        load(img, url, width, height, transformation);
    }

    public static ImageLoadType type;

    public abstract static class ImageLoadType {
        public abstract boolean isAliYunServer(String url);

        public abstract boolean isSlowNetWork();
    }

    private static class NetworkDisablingLoader implements StreamModelLoader<String> {
        @Override
        public DataFetcher<InputStream> getResourceFetcher(final String model, int width, int height) {
            return new DataFetcher<InputStream>() {
                @Override public InputStream loadData(Priority priority) throws Exception {
                    throw new IOException("Forces Glide network failure");
                }
                @Override
                public void cleanup() {
                }
                @Override
                public String getId() {
                    return model;
                }
                @Override
                public void cancel() {
                }
            };
        }
    }

    private static NetworkDisablingLoader getNetWorkDisablingLoader(){
        NetworkDisablingLoader loader = networkDisablingLoader;
        if (loader == null){
            synchronized (ImageLoader.class){
                loader = networkDisablingLoader;
                if (loader == null){
                    networkDisablingLoader = loader = new NetworkDisablingLoader();
                }
            }
        }
        return loader;
    }

    private static void doAliYunLoad(ImageView img, String defaultUrl, String wifiUrl, int width, int height, boolean slowNetWork
            , Transformation transformation){
        if (slowNetWork){
            BitmapRequestBuilder<String, Bitmap> offlineBuild = getBitmapBuilder(img, wifiUrl, width, height, transformation, getNetWorkDisablingLoader());
            BitmapRequestBuilder<String, Bitmap> netBuild = getBitmapBuilder(img, defaultUrl, width, height, transformation, null);
            if (netBuild == null || offlineBuild == null) return;
            netBuild.thumbnail(offlineBuild.diskCacheStrategy(DiskCacheStrategy.SOURCE)).into(img);
        }else{
            BitmapRequestBuilder wifi = getBitmapBuilder(img, wifiUrl, width, height, transformation, null);
            if (wifi != null) {
                wifi.diskCacheStrategy(DiskCacheStrategy.ALL).into(img);
            }
        }
    }

    private static boolean preLoad(ImageView img, String url, int width, int height, Transformation transformation) {
        if (type == null || !type.isAliYunServer(url)) return false;
        String defaultUrl = "@80q_";
        String wifiUrl = "@";
        if (width > 1 && height > 1) {
            String sizeUrl = height + "h_" + width + "w";
            defaultUrl += sizeUrl;
            wifiUrl += sizeUrl;
        }
        if (url.contains("?")) {
            String[] array = url.split("\\?");
            defaultUrl = array[0] + defaultUrl;
            wifiUrl = array[0] + wifiUrl;
            if (array.length > 1) {
                defaultUrl = defaultUrl + "?" + array[1];
                wifiUrl = wifiUrl + "?" + array[1];
            }
        }else{
            defaultUrl = url + defaultUrl;
            wifiUrl = url + wifiUrl;
        }
        doAliYunLoad(img, defaultUrl, wifiUrl, width, height, type.isSlowNetWork(), transformation);
        return true;
    }

    public static void load(ImageView img, String url, int width, int height, Transformation transformation) {
        if (StringUtils.isEmpty(url) || img == null) {
            ILog.e("===ImageLoader===", "url or img is null ...");
            return;
        }
        height *= hBili;
        width *= wBili;
        if (width > maxWidth) {
            height = height * maxWidth / width;
            width = maxWidth;
            ILog.i("===resize===", " width is " + width + " height is " + height);
        }

        String[] urls = url.split("\\?");

        //  this is gif
        if (urls[0].endsWith(".gif")) {
            DrawableRequestBuilder builder = getDrawableBuilder(img, url, width, height);
            builder.into(img);
            return;
        }

        if (preLoad(img, url, width, height, transformation)) return;  //  preload 用于加载其他aliyun等图片资源,以下是正常逻辑!aliyun不支持gif

        BitmapRequestBuilder builder = getBitmapBuilder(img, url, width, height, transformation, null);
        if (builder != null) {
            builder.into(img);
        }
    }

    private static DrawableRequestBuilder getDrawableBuilder(ImageView img, String url, int width, int height){
        if (img == null || !canLoad(img.getContext())) return null;
        Object placeholder = img.getTag(R.string.glide_tag_placeholder);
        DrawableRequestBuilder<String> builder = Glide.with(img.getContext()).load(url);
        if (width > 0 && height > 0) builder.override(width, height);
        if (placeholder != null) {
            builder.placeholder((int) placeholder);
            Object error = img.getTag(R.string.glide_tag_failed);
            builder.error(error == null ? (int) placeholder : (int) error);
            Object nullImg = img.getTag(R.string.glide_tag_null);
            builder.fallback(nullImg == null ? (int) placeholder : (int) nullImg);
        }
        return builder;
    }

    private static BitmapRequestBuilder<String, Bitmap> getBitmapBuilder(ImageView img, String url, int width, int height,
                                                                         Transformation transformation, StreamModelLoader loader){

        if (img == null || !canLoad(img.getContext())) return null;
        BitmapRequestBuilder<String, Bitmap> builder;
        try {
            RequestManager manager = Glide.with(img.getContext());
            if (loader != null) manager.using(loader);
            builder = manager.load(url).asBitmap();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (width > 1 && height > 1) {
            builder.override(width, height);
        }
        if (transformation != null) {
            builder.format(DecodeFormat.PREFER_ARGB_8888).transform(transformation);
        }
        Object placeholder = img.getTag(R.string.glide_tag_placeholder);
        if (placeholder != null) {
            builder.placeholder((int) placeholder);
            Object error = img.getTag(R.string.glide_tag_failed);
            builder.error(error == null ? (int) placeholder : (int) error);
            Object nullImg = img.getTag(R.string.glide_tag_null);
            builder.fallback(nullImg == null ? (int) placeholder : (int) nullImg);
        }
        return builder;
    }

    private static boolean canLoad(Context context) {
        if (context == null) return false;
        if (context instanceof Activity && ((Activity) context).isFinishing()) return false;
        return true;
    }

    public static void load(ImageView img, String url, int width, int height) {
        load(img, url, width, height, null);
    }

    public static void load(ImageView img, String url) {
        load(img, url, -1, -1, null);
    }
}
