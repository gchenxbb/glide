package com.glide.app;

import android.graphics.Bitmap;
import android.os.Bundle;
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
 * AppCompatActivity 兼容性
 */
public class GlideCompatActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "GlideCompatActivity";

    private Button btnImgNormal;
    private Button btnImgRes;
    private Button btnImgDown;
    private Button btnImgCache;
    private Button btnImgDisplay;
    private Button btnImgGif;

    private ImageView imgNormal;
    private ImageView imgResNormal;
    private ImageView imgDisplay;
    private ImageView imgGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compat_glide_image);
        initView();
        initListener();
    }

    protected void initView() {
        btnImgNormal = findViewById(R.id.btn_img_compat_url);
        btnImgRes = findViewById(R.id.btn_img_compat_res);
        btnImgDown = findViewById(R.id.btn_img_compat_onlydown);
        btnImgCache = findViewById(R.id.btn_img_compat_cache);
        btnImgDisplay = findViewById(R.id.btn_img_compat_display);
        btnImgGif = findViewById(R.id.btn_img_compat_gif);

        imgNormal = findViewById(R.id.glide_image_compat_url);
        imgResNormal = findViewById(R.id.glide_image_compat_res);
        imgDisplay = findViewById(R.id.glide_image_compat_display);
        imgGif = findViewById(R.id.glide_image_compat_gif);
    }

    protected void initListener() {
        btnImgNormal.setOnClickListener(this);
        btnImgRes.setOnClickListener(this);
        btnImgDown.setOnClickListener(this);
        btnImgCache.setOnClickListener(this);
        btnImgDisplay.setOnClickListener(this);
        btnImgGif.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_img_compat_url:
                GlideUtil.displayImage(Constant.url1, imgNormal);
                break;
            case R.id.btn_img_compat_res:
                GlideUtil.displayImage(R.drawable.img1, imgResNormal);
                break;
            case R.id.btn_img_compat_display:
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
            case R.id.btn_img_compat_onlydown:
                GlideUtil.downloadOnlyImage(this, Constant.url2);
                break;
            case R.id.btn_img_compat_cache:
                GlideUtil.preloadImage(this, Constant.url3);
                break;
            case R.id.btn_img_compat_gif:
                GlideUtil.displayImageGif(Constant.urlGif, imgGif);
                break;
        }
    }
}







