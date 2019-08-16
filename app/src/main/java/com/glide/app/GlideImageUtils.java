package com.glide.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.glide.app.image.GlideApp;
import com.glide.app.image.GlideRequest;

/*
 * 描述：图片框架的封装
 */
public class GlideImageUtils {
    private static RequestOptions options;

    public static RequestOptions getDisplayImageOptionsInstance(int loadingDrawable, int emptyDrawable, int errorDrawable) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(loadingDrawable).fallback(emptyDrawable).error(errorDrawable);
        return requestOptions;
    }

    public static RequestOptions getOptionsInstance() {
        if (options == null) {
            options = getDisplayImageOptionsInstance(R.color.glide_image_bg_color_place, R.color.glide_image_bg_color_error, R.color.glide_image_bg_color_error);
        }
        return options;
    }

    public static void displayImage(String url, ImageView imageView) {
        GlideApp.with(imageView).load(url).into(imageView);
    }

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
