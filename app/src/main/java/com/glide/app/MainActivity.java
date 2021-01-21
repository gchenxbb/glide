package com.glide.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
 *
 */
public class MainActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "GlideActivity";
    private Button btntype;
    private Button btnflies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
    }

    protected void initView() {

        btnflies = findViewById(R.id.btn_logfile);
        btntype = findViewById(R.id.btn_type);
        btnflies.setOnClickListener(this);
        btntype.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_type:
                startActivity(new Intent(this, GlideActivity.class));
                break;
            case R.id.btn_logfile:
                startActivity(new Intent(this, LocalFileActivity.class));
                break;
            default:
                break;

        }
    }

}