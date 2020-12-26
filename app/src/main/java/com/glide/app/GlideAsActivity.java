package com.glide.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.glide.app.config.GlideApp;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/*
 * Glide demo
 */
public class GlideAsActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "GlideAsActivity";

    private Button btnImgasdrawable;
    private Button btnImgasgif;
    private Button btnImgasbitmap;
    private Button btnImgasfile;
    private ImageView imgasdrawable;
    private ImageView imgasbitmap;
    private ImageView imgasgif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_as);
        initView();
        initListener();
    }

    protected void initView() {
        btnImgasbitmap = findViewById(R.id.btn_img_asbitmap);
        btnImgasdrawable = findViewById(R.id.btn_img_asdrawable);
        btnImgasgif = findViewById(R.id.btn_img_asgif);
        btnImgasfile = findViewById(R.id.btn_img_asfile);

        imgasbitmap = findViewById(R.id.glide_image_asbitmap);
        imgasdrawable = findViewById(R.id.glide_image_asdrawable);
        imgasgif = findViewById(R.id.glide_image_asgif);
    }

    protected void initListener() {
        btnImgasbitmap.setOnClickListener(this);
        btnImgasfile.setOnClickListener(this);
        btnImgasgif.setOnClickListener(this);
        btnImgasdrawable.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_img_asbitmap:
                GlideApp.with(this).asBitmap().load(Constant.url1).into(imgasbitmap);
                break;
            case R.id.btn_img_asdrawable:
                GlideApp.with(this).asDrawable().load(Constant.url2).into(imgasdrawable);
                break;
            case R.id.btn_img_asgif:
                GlideApp.with(this).asGif().load(Constant.urlGif).into(imgasgif);
                break;
            case R.id.btn_img_asfile:
                //权限，特定根目录文件

                new Thread() {
                    @Override
                    public void run() {
                        Log.d(TAG, "thread start download file..");
                        FutureTarget<File> target = null;
                        try {
                            //downloadOnly替代asFile()
                            //仅下载保存文件并获取
                            target = GlideApp.with(GlideAsActivity.this).downloadOnly().load(Constant.url3).submit();
                            File downloadedFile = target.get();

                            Log.d(TAG, "thread get file:" + downloadedFile.getAbsolutePath());
                        } catch (ExecutionException | InterruptedException e) {
                            Log.d(TAG, "exception :" + e.getMessage());
                        } finally {
                            if (target != null) {
                                target.cancel(true); // mayInterruptIfRunning
                            }
                        }
                    }
                }.start();

                break;

        }
    }
}







