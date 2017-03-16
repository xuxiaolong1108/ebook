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

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by xuxiaolong on 2017/3/7.
 */

public class BookFragment extends Fragment {
    View view = null;
    private Button download_test;
    String filePath = Environment.getExternalStorageDirectory().getPath();
    String saveFilePath = filePath + "//" + "ycsdk.zip";

    private ProgressDialog progressDialog;

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
        download_test = ((Button) view.findViewById(R.id.download_test));
    }

    private void initData() {

    }

    private void initEvent() {
        download_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downLoadFileTest();
            }
        });

    }

    private void downLoadFileTest() {
        progressDialog = new ProgressDialog(getActivity());
        RequestParams requestParams = new RequestParams(Constant.url);
        requestParams.setSaveFilePath(saveFilePath);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                Toast.makeText(getActivity(), "下载成功", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(getActivity(), "下载失败，请检查网络和SD卡", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("亲，努力下载中。。。");
                progressDialog.show();
                progressDialog.setMax((int) total);
                progressDialog.setProgress((int) current);

            }
        });


    }
}
