package com.glide.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.glide.app.config.GlideApp;
import com.glide.app.config.GlideRequest;
import com.glide.app.config.GlideRequests;

import java.io.File;
import java.net.URL;

/*
 */
public class GlideUtil {
    private static RequestOptions options;

    public static RequestOptions getDisplayImageOptionsInstance(int loadingDrawable, int emptyDrawable, int errorDrawable) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(loadingDrawable).fallback(emptyDrawable).error(errorDrawable);
        return requestOptions;
    }

    /**
     * 占位符，错误，若ImageView未设置高度，无法显示错误color,设置了宽高，则可以显示出错误color
     *
     * @return
     */
    public static RequestOptions getOptionsInstance() {
        if (options == null) {
            options = getDisplayImageOptionsInstance(R.color.glide_image_bg_color_place, R.color.glide_image_bg_color_error, R.color.glide_image_bg_color_error);
        }
        return options;
    }

    /**
     * String 类型
     *
     * @param url
     * @param imageView
     */
    public static void displayImage(String url, ImageView imageView) {
        GlideApp.with(imageView).load(url).into(imageView);
    }

    /**
     * 资源图片
     *
     * @param resId
     * @param imageView
     */
    public static void displayImage(Integer resId, ImageView imageView) {
        GlideApp.with(imageView).load(resId).into(imageView);
    }

    /**
     * Uri类型
     *
     * @param uri
     * @param imageView
     */
    public static void displayImage(Uri uri, ImageView imageView) {
        GlideApp.with(imageView).load(uri).into(imageView);
    }

    /**
     * URL类型
     *
     * @param url
     * @param imageView
     */
    public static void displayImage(URL url, ImageView imageView) {
        GlideApp.with(imageView).load(url).into(imageView);
    }

    /**
     * File类型
     *
     * @param file
     * @param imageView
     */
    public static void displayImage(File file, ImageView imageView) {
        GlideApp.with(imageView).load(file).into(imageView);
    }

    /**
     * Bitmap
     *
     * @param bitmap
     * @param imageView
     */
    public static void displayImage(Bitmap bitmap, ImageView imageView) {
        GlideApp.with(imageView).load(bitmap).into(imageView);
    }

    /**
     * Drawable
     *
     * @param drawable
     * @param imageView
     */
    public static void displayImage(Drawable drawable, ImageView imageView) {
        GlideApp.with(imageView).load(drawable).into(imageView);
    }

    /**
     * byte 数组
     *
     * @param bytes
     * @param imageView
     */
    public static void displayImage(byte[] bytes, ImageView imageView) {
        GlideApp.with(imageView).load(bytes).into(imageView);
    }


    /**
     * asBitmap
     * asGif
     * asDrawable
     * asFile
     *
     * @param url
     * @param imageView
     */
    public static void displayImageAsX(String url, ImageView imageView) {
        GlideApp.with(imageView).asBitmap().load(url).into(imageView);
    }

    /**
     * 表示只会下载图片，而不会对图片进行加载
     *
     * @param context
     * @param url
     */
    @SuppressLint("CheckResult")
    public static void downloadOnlyImage(Context context, String url) {
        GlideApp.with(context).downloadOnly().load(url);
    }

    /**
     * 预加载,只缓存，不显示
     *
     * @param context
     * @param url
     */
    public static void preloadImage(Context context, String url) {
        GlideApp.with(context).load(url).preload();
    }

    /**
     * 带options
     *
     * @param url
     * @param imageView
     */
    @SuppressLint("CheckResult")
    public static void displayImageOptions(String url, final ImageView imageView, RequestListener<Bitmap> requestListener) {
        valide(imageView);
        GlideApp.with(imageView)
                .asBitmap()//只允许静态图片,对于gif，只展示第一帧;
                .dontTransform()//加载图片的过程中不进行图片变换
                .load(url)
                .listener(requestListener)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//DiskCacheStrategy.NONE是不使用缓存功能
                .apply(getOptionsInstance())
                .into(imageView);
    }

    @SuppressLint("CheckResult")
    public static void displayImageGif(String url, final ImageView imageView) {
        valide(imageView);
        GlideApp.with(imageView)
                .asGif()//只允许动态图片，静态url会error
                .load(url)
                .into(imageView);
    }

    /**
     * 只加载100*75像素大小到内存，不管ImageView是多大
     *
     * @param url
     * @param imageView
     */
    public static void displayImageOverrideSize(String url, final ImageView imageView) {
        valide(imageView);
        GlideApp.with(imageView)
                .load(url)
                .override(300, 215)
                .into(imageView);
    }


    /*************************************** 未整理 ************************************************/
//    public static void displayImage(String url, ImageView imageView) {
//        //透明：transition：new DrawableTransitionOptions().transition(R.anim.enter_alpha)
//        GlideApp.with(imageView).load(url)
//                // .transition(DrawableTransitionOptions.withCrossFade())
//                .into(imageView);
//    }

    //渐变式加载图片, 可以有圆角
    public static void displayImage(String url, final ImageView imageView, int cornerRadius) {
        valide(imageView);
        GlideRequest<Drawable> request = GlideApp.with(imageView).load(url).apply(getOptionsInstance()).transition(DrawableTransitionOptions.withCrossFade());
        if (cornerRadius > 0) {
            request.transform(new MultiTransformation<>(//必须使用MultiTransformation来让CenterCrop和RoundedCorners共存
                    new CenterCrop(),
                    new RoundedCorners(cornerRadius)
            )).into(imageView);
        } else {
            request.into(imageView);
        }
        //ScreenUtils.dip2px(cornerRadius)
    }

    //展示纯圆图片
    public static void displayRoundImage(String url, final ImageView imageView) {
        valide(imageView);
        //有边界transform:new GlideCircleTransform()
        //.placeholder(defaultDrawableId).error(defaultDrawableId)
        GlideApp.with(imageView).asBitmap().load(url).transition(BitmapTransitionOptions.withCrossFade())
                .transform(new CircleCrop())
                .into(imageView);
    }

    // 本地drawable图片展示
    public static void displayDrawableImage(Integer integer, final ImageView imageView) {
        valide(imageView);
        GlideApp.with(imageView).load(integer).apply(getOptionsInstance()).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
    }

    /*********************bitmap*****************************/
    //Bitmap格式，能设置options和listener回调
    public static void displayImageAsBitmap(String url, ImageView imageView, RequestOptions options, RequestListener<Bitmap> requestListener) {
        valide(imageView);
        GlideApp.with(imageView).asBitmap().load(url).apply(options).listener(requestListener).transition(BitmapTransitionOptions.withCrossFade()).into(imageView);
    }

    /*****************download***********************/
    public static void downloadImageAsBitmapWithTarget(Context context, String url, SimpleTarget<Bitmap> simpleTarget) {
        GlideApp.with(context).asBitmap().load(url).apply(getOptionsInstance()).into(simpleTarget);
    }

    static boolean valide(ImageView imageView) {
        if (imageView == null)
            return false;
        if (Build.VERSION.SDK_INT > 17) {
            if ((imageView.getContext() instanceof Activity && ((Activity) imageView.getContext()).isDestroyed())) {
                return false;
            }
        } else {
            if ((imageView.getContext() instanceof Activity && ((Activity) imageView.getContext()).isFinishing())) {
                return false;
            }
        }

        return true;
    }
}
