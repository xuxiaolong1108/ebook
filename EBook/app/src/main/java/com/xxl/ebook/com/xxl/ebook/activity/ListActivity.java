package com.xxl.ebook.com.xxl.ebook.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.xxl.ebook.R;
import com.xxl.ebook.com.xxl.ebook.adapter.BookListAdapter;
import com.xxl.ebook.com.xxl.ebook.common.BookList;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListView lv_list_list;
    private List<String> list =new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        lv_list_list = ((ListView) findViewById(R.id.lv_list_list));
    }

    private void initData() {
        BookList bookList =new BookList();
        list = bookList.getBookList();
        BookListAdapter bookListAdapter =new BookListAdapter(getApplicationContext(),list);
        lv_list_list.setAdapter(bookListAdapter);
    }

    private void initEvent() {

    }
}
