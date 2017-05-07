package com.xxl.ebook.com.xxl.ebook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.xxl.ebook.R;
import com.xxl.ebook.com.xxl.ebook.adapter.BookListAdapter;
import com.xxl.ebook.com.xxl.ebook.common.BookList;
import com.xxl.ebook.com.xxl.ebook.pojo.TitleBean;
import com.xxl.ebook.com.xxl.ebook.utils.BookPostion;
import com.xxl.ebook.com.xxl.ebook.utils.Constant;
import com.xxl.ebook.com.xxl.ebook.utils.ELog;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final  String TAG =ListActivity.class.getSimpleName();

    private ListView lv_list_list;
    private List<TitleBean> list = new ArrayList<TitleBean>();
    private ImageView iv_list_close;
    private String bookPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent =getIntent();
        bookPath = intent.getStringExtra("bookPath");
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        lv_list_list = ((ListView) findViewById(R.id.lv_list_list));
        iv_list_close = ((ImageView) findViewById(R.id.iv_list_close));
    }

    private void initData() {
        BookList bookList = new BookList();
        list = bookList.getBookList(bookPath);
        BookListAdapter bookListAdapter = new BookListAdapter(getApplicationContext(), list);
        lv_list_list.setAdapter(bookListAdapter);
    }

    private void initEvent() {
        lv_list_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent();
                int pos =(list.get(position).titlePosition)/(Constant.BOOKSIZE);
                ELog.i(TAG,pos+"");
                intent.putExtra("selectposition",pos);
                setResult(2,intent);
                finish();
            }
        });
        iv_list_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.putExtra("test","你是都比");
                setResult(1,intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent =new Intent();
        intent.putExtra("test","你是都比2");
        setResult(1,intent);
        super.onBackPressed();
    }
}
