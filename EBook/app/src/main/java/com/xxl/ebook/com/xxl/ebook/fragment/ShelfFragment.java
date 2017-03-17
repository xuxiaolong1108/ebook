package com.xxl.ebook.com.xxl.ebook.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.xxl.ebook.R;
import com.xxl.ebook.com.xxl.ebook.activity.ReadActivity;
import com.xxl.ebook.com.xxl.ebook.adapter.ListAdapter;
import com.xxl.ebook.com.xxl.ebook.pojo.BookNameBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuxiaolong on 2017/3/7.
 */

public class ShelfFragment extends Fragment {
    private static final String TAG = ShelfFragment.class.getSimpleName();
    View view = null;
    File sdCardFile = Environment.getExternalStorageDirectory();
    List<BookNameBean> filePathList = new ArrayList<BookNameBean>();
    private ListView lv_shelf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bookshelf, null, false);
        getTxtFiles();
        initView();
        initData();
        initEvent();
        return view;

    }

    private void getTxtFiles() {
        File[] files = sdCardFile.listFiles();
        BookNameBean bookNameBean;
        for (int i = 0; i < files.length; i++) {
            bookNameBean = new BookNameBean();
            if (!files[i].isDirectory()) {
                if (files[i].getName().endsWith(".txt")) {
                    bookNameBean.bookName = files[i].getName();
                    bookNameBean.bookPath = files[i].getAbsolutePath();
                    Log.i(TAG, "name:" + files[i].getName() + "--path:" + files[i].getAbsolutePath());
                    filePathList.add(bookNameBean);
                }
            }
        }
    }

    private void initView() {
        lv_shelf = ((ListView) view.findViewById(R.id.lv_shelf));
    }

    private void initData() {
        lv_shelf.setAdapter(new ListAdapter(getActivity(), filePathList));
    }

    private void initEvent() {
        lv_shelf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle =new Bundle();
                bundle.putString("bookPath",filePathList.get(i).bookPath);
                Intent intent =new Intent(getActivity(),ReadActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

    }
}
