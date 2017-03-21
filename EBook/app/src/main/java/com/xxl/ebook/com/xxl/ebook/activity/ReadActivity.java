package com.xxl.ebook.com.xxl.ebook.activity;

import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xxl.ebook.R;
import com.xxl.ebook.com.xxl.ebook.adapter.BookPageAdapter;

import java.io.File;

public class ReadActivity extends AppCompatActivity {
    private static final String TAG = ReadActivity.class.getSimpleName();
    private ViewPager vp_read;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Bundle bundle = getIntent().getExtras();
        String bookPath = bundle.getString("bookPath");
        file = new File(bookPath);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        vp_read = ((ViewPager) findViewById(R.id.vp_read));
    }

    private void initData() {
        BookPageAdapter bookPageAdapter = new BookPageAdapter(file, getApplicationContext());
        vp_read.setAdapter(bookPageAdapter);
    }

    private void initEvent() {

    }
}
