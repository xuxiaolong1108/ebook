package com.xxl.ebook.com.xxl.ebook.activity;

import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xxl.ebook.R;
import com.xxl.ebook.com.xxl.ebook.adapter.BookPageAdapter;
import com.xxl.ebook.com.xxl.ebook.utils.AnimUtils;
import com.xxl.ebook.com.xxl.ebook.utils.ELog;

import java.io.File;

public class ReadActivity extends AppCompatActivity {
    private static final String TAG = ReadActivity.class.getSimpleName();
    private ViewPager vp_read;
    File file;
    private Button test_show;
    private Button test_hide;
    private RelativeLayout rl_city_titlebar;
    private RelativeLayout rl_city_bottombar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
       // Bundle bundle = getIntent().getExtras();
      //  String bookPath = bundle.getString("bookPath");
        String bookPath = Environment.getExternalStorageDirectory().getPath()+"/sanguo.txt";
        file = new File(bookPath);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        vp_read = ((ViewPager) findViewById(R.id.vp_read));

        test_show = ((Button) findViewById(R.id.test_show));
        test_hide = ((Button) findViewById(R.id.test_hide));
        rl_city_titlebar = ((RelativeLayout) findViewById(R.id.rl_city_titlebar));
        rl_city_bottombar = ((RelativeLayout) findViewById(R.id.rl_city_bottombar));
    }

    private void initData() {
        BookPageAdapter bookPageAdapter = new BookPageAdapter(file, getApplicationContext());
        vp_read.setAdapter(bookPageAdapter);
        rl_city_titlebar.setVisibility(View.GONE);
        rl_city_bottombar.setVisibility(View.GONE);
    }

    private void initEvent() {
        test_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_city_titlebar.setVisibility(View.VISIBLE);
                rl_city_titlebar.setAnimation(AnimUtils.MoveToBottomForShow());
                rl_city_bottombar.setVisibility(View.VISIBLE);
                rl_city_bottombar.setAnimation(AnimUtils.MoveToTopForShow());


            }
        });
        test_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_city_titlebar.setVisibility(View.GONE);
                rl_city_titlebar.setAnimation(AnimUtils.MoveToTopForHide());
                rl_city_bottombar.setVisibility(View.GONE);
                rl_city_bottombar.setAnimation(AnimUtils.MoveToBottomForHide());
            }
        });
        

    }

}
