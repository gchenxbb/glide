package com.glide.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * 查询本地Glide下载的文件列表
 */
public class LocalFileActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "LocalFileActivity";

    Button btnLocal;
    RecyclerView recyclerView;

    List<String> fileNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_flies);
        initView();
//        File fileTest = getExternalCacheDir();
//        File fileTest = getCacheDir();

        String imageDir = getExternalCacheDir().getAbsolutePath() + File.separator + "Images";
        File fileTest = new File(imageDir);

        if (fileTest.isDirectory() && fileTest.exists()) {
            File file1[] = fileTest.listFiles();
            for (File file2 : file1) {
                Log.d(TAG, file2.getAbsolutePath());

                fileNames.add(file2.getName());
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LocalAdapter adapter = new LocalAdapter(fileNames);
        recyclerView.setAdapter(adapter);
    }

    protected void initView() {

        btnLocal = findViewById(R.id.btn_get_local);
        btnLocal.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_view);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:


        }
    }
}







