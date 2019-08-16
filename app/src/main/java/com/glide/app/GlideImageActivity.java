package com.glide.app;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.glide.app.image.GlideApp;
import com.glide.app.interceptor.ProgressIntercepor;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * Glide分析
 */
public class GlideImageActivity extends AppCompatActivity {
    String url1 = "https://img-my.csdn.net/uploads/201407/26/1406382924_8955.jpg";
    String urlGif = "http://p1.pstatp.com/large/166200019850062839d3";
    String url2 = "https://img-my.csdn.net/uploads/201407/26/1406382923_2141.jpg";
    String url3 = "https://www.baidu.com/img/bd_logo1.png";
    String url_progress = "http://t2.hddhhn.com/uploads/tu/201610/198/scx30045vxd.jpg";
    Activity activity;
    Binder binder;
    ImageView mGlideImageView;
    @BindView(R.id.btn_img_normal)
    Button btnImgNormal;
    @BindView(R.id.btn_res_normal)
    Button btnResNormal;
    @BindView(R.id.btn_img_down)
    Button btnImgDown;
    @BindView(R.id.btn_img_cache)
    Button btnImgCache;
    @BindView(R.id.btn_img_dis)
    Button btnImgDis;
    @BindView(R.id.btn_img_progress)
    Button btnImgProgress;
    @BindView(R.id.glide_image)
    ImageView glideImage;
        ListView listView;

    Object ob;
    HashMap hash;
    String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_image);
        ButterKnife.bind(this);
        initView();
    }

    protected void initView() {
        mGlideImageView = findViewById(R.id.glide_image);
    }

    @OnClick({R.id.btn_img_normal, R.id.btn_res_normal, R.id.btn_img_down, R.id.btn_img_cache, R.id.btn_img_dis,
            R.id.btn_img_progress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_img_normal://普通展示图片
                GlideApp.with(this)
                        .load(url1)
                        .into(mGlideImageView);
                break;
            case R.id.btn_res_normal:// 加载资源图片
                GlideApp.with(this)
                        .load(R.mipmap.img1)
                        .into(mGlideImageView);
                break;
            case R.id.btn_img_down://表示只会下载图片，而不会对图片进行加载
                GlideApp.with(this)
                        .downloadOnly()
                        .load(url2);
                break;
            case R.id.btn_img_cache:
                GlideApp.with(this)
                        .load(url1)
                        .preload();//预加载,只缓存，不显示
                break;
            case R.id.btn_img_dis:
                GlideApp.with(this)
                        .asBitmap()//只允许静态图片,对于gif，只展示第一帧
                        //.asGif()//只允许动态图片，静态url会error
                        //.load(urlGif)
                        //.dontTransform()//加载图片的过程中不进行图片变换
                        .load(url2)
                        .placeholder(R.color.glide_image_bg_color_place)//占位符,
                        .error(R.color.glide_image_bg_color_error)//错误，若ImageView未设置高度，无法显示错误color,设置了宽高，则可以显示出错误color
                        .diskCacheStrategy(DiskCacheStrategy.NONE)//不使用缓存功能
                        //.diskCacheStrategy(DiskCacheStrategy.ALL)
                        //.override(300, 215)//只加载100*75像素大小到内存，不管ImageView是多大
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                //showToast("success_onReady");
                                return false;
                            }
                        })
                        .into(mGlideImageView);
                break;
            case R.id.btn_img_progress:
                ProgressIntercepor.addListener(url_progress, new ProgressListener() {
                    @Override
                    public void onProgress(int progress) {
                        //下载弹框
                    }
                });
                GlideApp.with(this)
                        .load(url_progress)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                ProgressIntercepor.removeListener(url_progress);
                            }

                            @Override
                            public void onLoadStarted(@Nullable Drawable placeholder) {

                            }
                        });
                break;
        }
    }


    // 加载本地图片
//    File file = new File(getExternalCacheDir() + "/image.jpg");
//Glide.with(this).load(file).into(imageView);
//    // 加载二进制流
//    byte[] image = getImageBytes();
//Glide.with(this).load(image).into(imageView);
//    // 加载Uri对象
//    Uri imageUri = getImageUri();
//Glide.with(this).load(imageUri).into(imageView);
    //自动判断ImageView的大小,根据大小加载图片像素到内存






}







