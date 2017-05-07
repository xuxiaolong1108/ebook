package com.xxl.ebook.com.xxl.ebook.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xxl.ebook.R;
import com.xxl.ebook.com.xxl.ebook.adapter.BookPageAdapter;
import com.xxl.ebook.com.xxl.ebook.utils.AnimUtils;
import com.xxl.ebook.com.xxl.ebook.utils.BookPostion;
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
    private ImageView iv_read_close;
    String bookPath =null;
    String bookName ="";
    private  int current =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
         Bundle bundle = getIntent().getExtras();
         bookPath = bundle.getString("bookPath");
        bookName = bundle.getString("bookName");
        file = new File(bookPath);
        SharedPreferences sharedPreferences =getSharedPreferences(bookName,MODE_PRIVATE);

        current = sharedPreferences.getInt("current",0);//应该从sharedperfrecnce取出来
        initView();
        //initData();
        initEvent();
    }

    @Override
    protected void onStart() {
        ELog.i(TAG,"onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        ELog.i(TAG,"onResume");
        super.onResume();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //shared 存贮当前页数
        SharedPreferences sharedPreferences =getSharedPreferences(bookName,MODE_PRIVATE);
        SharedPreferences.Editor editor  = sharedPreferences.edit();
        editor.putInt("current",BookPostion.currentPosition);
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ELog.i(TAG,"onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        ELog.i(TAG,"requestCode"+"------"+requestCode);
        ELog.i(TAG,"resultCode"+"------"+resultCode);
        if(0==requestCode&&1==resultCode){
            //resultCode =1  未选择章节
            current = BookPostion.currentPosition;
        }
        if(0==requestCode&&2==resultCode){
            // 选择了章节
            current= data.getIntExtra("selectposition",0);

        }
    }

    private void initView() {
        vp_read = ((ViewPager) findViewById(R.id.vp_read));

        rl_city_titlebar = ((RelativeLayout) findViewById(R.id.rl_city_titlebar));
        rl_city_bottombar = ((RelativeLayout) findViewById(R.id.rl_city_bottombar));
        rl_city_touch = ((RelativeLayout) findViewById(R.id.rl_city_touch));
        tv_read_list = ((TextView) findViewById(R.id.tv_read_list));
        iv_read_close = ((ImageView) findViewById(R.id.iv_read_close));
    }

    private void initData() {
        BookPageAdapter bookPageAdapter = new BookPageAdapter(file, getApplicationContext());
        vp_read.setAdapter(bookPageAdapter);
        vp_read.setCurrentItem(current);
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
                intent.putExtra("bookPath",bookPath+"");
                startActivityForResult(intent,0);
            }
        });
        iv_read_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
