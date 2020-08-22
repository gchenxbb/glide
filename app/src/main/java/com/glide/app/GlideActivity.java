package com.glide.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/*
 * Glide demo
 */
public class GlideActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "GlideActivity";

    private Button btnImgUrl;
    private Button btnImgDrawable;
    private Button btnImgAssets;
    private Button btnImgRaw;
    private Button btnImgFile;
    private Button btnImgDown;
    private Button btnImgCache;
    private Button btnImgDisplay;
    private Button btnImgGif;
    private ImageView imgUrl;
    private ImageView imgDrawable;
    private ImageView imgAssets;
    private ImageView imgRaw;
    private ImageView imgFile;
    private ImageView imgDisplay;
    private ImageView imgGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_image);
        initView();
        initListener();
    }

    protected void initView() {
        btnImgUrl = findViewById(R.id.btn_img_url);
        btnImgDrawable = findViewById(R.id.btn_img_drawable);
        btnImgAssets = findViewById(R.id.btn_img_assets);
        btnImgRaw = findViewById(R.id.btn_img_raw);
        btnImgFile = findViewById(R.id.btn_img_file);
        btnImgDisplay = findViewById(R.id.btn_img_display);
        btnImgGif = findViewById(R.id.btn_img_gif);
        btnImgDown = findViewById(R.id.btn_img_onlydown);
        btnImgCache = findViewById(R.id.btn_img_cache);

        imgUrl = findViewById(R.id.glide_image_url);
        imgDrawable = findViewById(R.id.glide_image_drawable);
        imgAssets = findViewById(R.id.glide_image_assets);
        imgRaw = findViewById(R.id.glide_image_raw);
        imgFile = findViewById(R.id.glide_image_file);
        imgDisplay = findViewById(R.id.glide_image_display);
        imgGif = findViewById(R.id.glide_image_gif);
    }

    protected void initListener() {
        btnImgUrl.setOnClickListener(this);
        btnImgDrawable.setOnClickListener(this);
        btnImgAssets.setOnClickListener(this);
        btnImgRaw.setOnClickListener(this);
        btnImgFile.setOnClickListener(this);
        btnImgDisplay.setOnClickListener(this);
        btnImgGif.setOnClickListener(this);
        btnImgDown.setOnClickListener(this);
        btnImgCache.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_img_url:
                GlideUtil.displayImage(Constant.url1, imgUrl);
                break;
            case R.id.btn_img_assets:
                GlideUtil.displayImage(Constant.imgasset, imgAssets);
                break;
            case R.id.btn_img_raw:
                GlideUtil.displayImage(Constant.imgraw, imgRaw);
                break;
            case R.id.btn_img_drawable:
                GlideUtil.displayImage(Constant.imgdrawable, imgDrawable);
                break;
            case R.id.btn_img_file:
                //权限，特定根目录文件
                GlideUtil.displayImage(Constant.imgfile, imgFile);
                break;
            case R.id.btn_img_onlydown:
                GlideUtil.downloadOnlyImage(this, Constant.url2);
                break;
            case R.id.btn_img_cache:
                GlideUtil.preloadImage(this, Constant.url3);
                break;
            case R.id.btn_img_gif:
                GlideUtil.displayImageGif(Constant.urlGif, imgGif);
                break;
            case R.id.btn_img_display:
                GlideUtil.displayImageOptions(Constant.url4, imgDisplay, new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d(TAG, "onResourceReady success");
                        return false;
                    }
                });
                break;
        }
    }
}







