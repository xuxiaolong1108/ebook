package com.xxl.ebook.com.xxl.ebook.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

    private RelativeLayout rl_city_titlebar;
    private RelativeLayout rl_city_bottombar;
    private boolean isShow = false;
    private RelativeLayout rl_city_touch;
    private TextView tv_read_list;

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

        rl_city_titlebar = ((RelativeLayout) findViewById(R.id.rl_city_titlebar));
        rl_city_bottombar = ((RelativeLayout) findViewById(R.id.rl_city_bottombar));
        rl_city_touch = ((RelativeLayout) findViewById(R.id.rl_city_touch));
        tv_read_list = ((TextView) findViewById(R.id.tv_read_list));
    }

    private void initData() {
        BookPageAdapter bookPageAdapter = new BookPageAdapter(file, getApplicationContext());
        vp_read.setAdapter(bookPageAdapter);
        rl_city_titlebar.setVisibility(View.GONE);
        rl_city_bottombar.setVisibility(View.GONE);
        isShow = false;
    }

    private void initEvent() {
        rl_city_touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShow) {
                    rl_city_titlebar.setVisibility(View.GONE);
                    rl_city_titlebar.setAnimation(AnimUtils.MoveToTopForHide());
                    rl_city_bottombar.setVisibility(View.GONE);
                    rl_city_bottombar.setAnimation(AnimUtils.MoveToBottomForHide());
                    isShow = false;
                } else {
                    rl_city_titlebar.setVisibility(View.VISIBLE);
                    rl_city_titlebar.setAnimation(AnimUtils.MoveToBottomForShow());
                    rl_city_bottombar.setVisibility(View.VISIBLE);
                    rl_city_bottombar.setAnimation(AnimUtils.MoveToTopForShow());
                    isShow = true;
                }
            }
        });

        tv_read_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),ListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ELog.i(TAG, "点击");
                break;
            case MotionEvent.ACTION_UP:
                ELog.i(TAG, "释放");
                break;
            case MotionEvent.ACTION_MOVE:
                ELog.i(TAG, "滑动");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
