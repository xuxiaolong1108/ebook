package com.xxl.ebook.com.xxl.ebook.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxl.ebook.R;

/**
 * Created by xuxiaolong on 2017/3/7.
 */

public class BookFragment extends Fragment {
    View view =null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bookcity,null,false);
        return view;
    }
}
