package com.xxl.ebook.com.xxl.ebook.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.xxl.ebook.R;

/**
 * Created by xuxiaolong on 2017/3/21.
 */

public class EUtils {
    private  static  Dialog mDialog;
    public static  void showProgressBar(Context context){
        Dialog dialog =new Dialog(context, R.style.dialogStyle);
        View view =View.inflate(context,R.layout.progress_dialog_layout,null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        mDialog = dialog;
        dialog.show();
    }
    public static void closeProgressBar(){
        if(null!=mDialog){
            mDialog.dismiss();
        }
    }
}
