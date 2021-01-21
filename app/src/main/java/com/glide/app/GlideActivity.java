package com.glide.app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * Glide 支持的各种 类型
 */
public class GlideActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "GlideActivity";

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static int REQUEST_PERMISSION_CODE = 1;

    private Button btnUrl;//String
    private Button btnResId;//Integer
    private Button btnUri;//Uri
    private Button btnURL;//URL
    private Button btnFile;//File
    private Button btnBitmap;//Bitmap
    private Button btnDrawable;//Drawable
    private Button btnBytes;//byte[]

    private ImageView imgUrl;
    private ImageView imgResId;
    private ImageView imgUri;
    private ImageView imgURL;
    private ImageView imgFile;
    private ImageView imgBitmap;
    private ImageView imgDrawable;
    private ImageView imgBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_image);

        //将加载的File文件图片放在Android/包名/路径下，注释以下代码
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
//            }
//        }

        initView();
    }

    void initView() {
        btnUrl = findViewById(R.id.btn_url);
        btnResId = findViewById(R.id.btn_resid);
        btnUri = findViewById(R.id.btn_uri);
        btnURL = findViewById(R.id.btn_URL);
        btnFile = findViewById(R.id.btn_file);
        btnBitmap = findViewById(R.id.btn_bitmap);
        btnDrawable = findViewById(R.id.btn_drawable);
        btnBytes = findViewById(R.id.btn_bytearray);

        imgUrl = findViewById(R.id.glide_image_url);
        imgResId = findViewById(R.id.glide_image_resid);
        imgUri = findViewById(R.id.glide_image_uri);
        imgURL = findViewById(R.id.glide_image_URL);
        imgFile = findViewById(R.id.glide_image_file);
        imgBitmap = findViewById(R.id.glide_image_bitmap);
        imgDrawable = findViewById(R.id.glide_image_drawable);
        imgBytes = findViewById(R.id.glide_image_bytearray);


        btnUrl.setOnClickListener(this);
        btnResId.setOnClickListener(this);
        btnUri.setOnClickListener(this);
        btnURL.setOnClickListener(this);
        btnFile.setOnClickListener(this);
        btnBitmap.setOnClickListener(this);
        btnDrawable.setOnClickListener(this);
        btnBytes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //String
            case R.id.btn_url:
                GlideUtil.displayImage(Constant.url, imgUrl);
                //GlideUtil.displayImage(Constant.imgasset, imgAssets);//归为String参数
                break;
            //Integer
            case R.id.btn_resid:
                //转换Uri
                GlideUtil.displayImage(Constant.imgresid, imgResId);
                //GlideUtil.displayImage(Constant.imgraw, imgRaw);//归为Integer参数
                break;
            //Uri
            case R.id.btn_uri:
                Uri uri = Uri.parse(Constant.imgfile);
                //权限，特定根目录文件
                GlideUtil.displayImage(uri, imgUri);
                break;
            //URL
            case R.id.btn_URL:
                try {
                    URL url = new URL(Constant.url1);
                    //URL类的常用方法getProtocol()getHost()getPort()
                    Log.d(TAG, "协议名：" + url.getProtocol());
                    Log.d(TAG, "主机号：" + url.getHost());//
                    Log.d(TAG, "端口号：" + url.getPort());//
                    GlideUtil.displayImage(url, imgURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            //File
            case R.id.btn_file:
//                String filePath = Environment.getExternalStorageDirectory().getPath() + "/IMG_20201011_143239.jpg";
                String filePath = getExternalCacheDir() + "/IMG_20201011_143239.jpg";

                File file = new File(filePath);
                if (file.exists()) {
                    Log.i(TAG, "文件存在：");
                    GlideUtil.displayImage(file, imgFile);
                } else {
                    Log.i(TAG, "文件不存在：");
                }
                break;
            //Bitmap
            case R.id.btn_bitmap:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
                GlideUtil.displayImage(bitmap, imgBitmap);
                break;
            //Drawable
            case R.id.btn_drawable:
                Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
                GlideUtil.displayImage(drawable, imgDrawable);
                break;
            //Byte Array
            case R.id.btn_bytearray:

                Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datas = baos.toByteArray();
                GlideUtil.displayImage(datas, imgBytes);
                break;
            default:
                //Object
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i(TAG, "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
        }
    }
}