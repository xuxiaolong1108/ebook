package com.xxl.ebook.com.xxl.ebook.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.xxl.ebook.R;
import com.xxl.ebook.com.xxl.ebook.utils.Constant;
import com.xxl.ebook.com.xxl.ebook.utils.EUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by xuxiaolong on 2017/3/7.
 */

public class BookFragment extends Fragment {
    View view = null;
    private Button btn_city_xuanhuan;
    private Button btn_city_chuanyue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_bookcity, null, false);
        initView();
        initData();
        initEvent();
        return view;
    }

    private void initView() {
        btn_city_xuanhuan = ((Button) view.findViewById(R.id.btn_city_xuanhuan));
        btn_city_chuanyue = ((Button) view.findViewById(R.id.btn_city_chuanyue));
    }

    private void initData() {
    }

    private void initEvent() {
        btn_city_xuanhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EUtils.showProgressBar(getActivity());
            }
        });
        btn_city_chuanyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EUtils.closeProgressBar();
            }
        });

    }

}
