package com.xxl.ebook.com.xxl.ebook.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xxl.ebook.R;
import com.xxl.ebook.com.xxl.ebook.activity.ReadActivity;

/**
 * Created by xuxiaolong on 2017/3/7.
 */

public class ShelfFragment extends Fragment {
    View view =null;
    private Button btn_test;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bookshelf,null,false);
        init();
        initView();
        initEvent();
        return view;

    }

    private void init() {
        btn_test = ((Button) view.findViewById(R.id.btn_test));
    }

    private void initView() {

    }

    private void initEvent() {
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), ReadActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }
}
