package com.xxl.ebook.com.xxl.ebook.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xxl.ebook.R;
import com.xxl.ebook.com.xxl.ebook.activity.ReadActivity;
import com.xxl.ebook.com.xxl.ebook.adapter.ListAdapter;
import com.xxl.ebook.com.xxl.ebook.common.ESqLite;
import com.xxl.ebook.com.xxl.ebook.pojo.BookNameBean;
import com.xxl.ebook.com.xxl.ebook.utils.ELog;

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
    Dialog dialog;
    private int currentBook;
    private ListAdapter listAdapter;

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
        //第一次打开app检索sd卡中txt文件
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("FirstStart", Context.MODE_PRIVATE);
        boolean isFirst = sharedPreferences.getBoolean("IsFirst", true);
        ESqLite eSqLite = new ESqLite(getActivity());
        SQLiteDatabase db = eSqLite.getWritableDatabase();
        if (isFirst) {
            ELog.i(TAG, "第一次打开检索sd卡");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("IsFirst", false);
            editor.commit();
            //电子书写入sqlite

            File[] files = sdCardFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (!files[i].isDirectory()) {
                    if (files[i].getName().endsWith(".txt")) {
                        files[i].getName();
                        files[i].getAbsolutePath();
                        files[i].length();
                        ELog.i(TAG, "name:" + files[i].getName() + "--path:" + files[i].getAbsolutePath());
                        String sql = "insert into bookshelf (bookname,bookauthor,bookaddress,imgaddress,booksize) " + "values("
                                + "'" + files[i].getName() + "'" + ","
                                + "'" + "" + "'" + ","
                                + "'" + files[i].getAbsolutePath() + "'" + ","
                                + "'" + "" + "'" + ","
                                + "'" + files[i].length() + "'" + ")";
                        db.execSQL(sql);
                        ELog.i(TAG, sql);
                    }
                }
            }
            BookNameBean bookNameBean = null;

            Cursor cursor = db.rawQuery("select * from bookshelf", null);
            while (cursor.moveToNext()) {
                bookNameBean = new BookNameBean();
                bookNameBean.id =cursor.getInt(0);
                bookNameBean.bookName = cursor.getString(1);
                bookNameBean.bookAuthor = cursor.getString(2);
                bookNameBean.bookPath = cursor.getString(3);
                bookNameBean.bookPic = cursor.getString(4);
                bookNameBean.bookSize = cursor.getString(5);
                filePathList.add(bookNameBean);
            }

        } else {
            BookNameBean bookNameBean = null;
            ELog.i(TAG, "不是第一次打开");
            Cursor cursor = db.rawQuery("select * from bookshelf", null);
            while (cursor.moveToNext()) {
                bookNameBean = new BookNameBean();
                bookNameBean.id =cursor.getInt(0);
                bookNameBean.bookName = cursor.getString(1);
                bookNameBean.bookAuthor = cursor.getString(2);
                bookNameBean.bookPath = cursor.getString(3);
                bookNameBean.bookPic = cursor.getString(4);
                bookNameBean.bookSize = cursor.getString(5);
                filePathList.add(bookNameBean);
            }
        }
        db.close();
        eSqLite.close();

    }

    private void initView() {
        lv_shelf = ((ListView) view.findViewById(R.id.lv_shelf));
    }

    private void initData() {
        listAdapter =new ListAdapter(getActivity(), filePathList);
        lv_shelf.setAdapter(listAdapter);

    }

    private void initEvent() {
        lv_shelf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentBook = i;
                toShowDialog();
            }
        });

    }


    private void toShowDialog() {
        dialog = new Dialog(getActivity(), R.style.dialogStyle);
        View view = View.inflate(getActivity(), R.layout.read_delete_layout, null);
        initDialogEvent(view);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialoginoutStyle);
        dialog.show();
    }

    private void toCloseDialog() {
        if (null != dialog) {
            dialog.dismiss();
        }

    }

    private void initDialogEvent(View view) {
        RelativeLayout rl_dialog_one = ((RelativeLayout) view.findViewById(R.id.rl_dialog_one));
        RelativeLayout rl_dialog_two = ((RelativeLayout) view.findViewById(R.id.rl_dialog_two));
        RelativeLayout rl_dialog_three = ((RelativeLayout) view.findViewById(R.id.rl_dialog_three));
        rl_dialog_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCloseDialog();
                Bundle bundle = new Bundle();
                bundle.putString("bookPath", filePathList.get(currentBook).bookPath);
                Intent intent = new Intent(getActivity(), ReadActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        rl_dialog_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除数据库对应书籍
                ESqLite eSqLite = new ESqLite(getActivity());
                SQLiteDatabase db = eSqLite.getWritableDatabase();
                db.execSQL("delete from bookshelf where id= "+filePathList.get(currentBook).id);
                filePathList.clear();
                BookNameBean bookNameBean = null;
                Cursor cursor = db.rawQuery("select * from bookshelf", null);
                while (cursor.moveToNext()) {
                    bookNameBean = new BookNameBean();
                    bookNameBean.id =cursor.getInt(0);
                    bookNameBean.bookName = cursor.getString(1);
                    bookNameBean.bookAuthor = cursor.getString(2);
                    bookNameBean.bookPath = cursor.getString(3);
                    bookNameBean.bookPic = cursor.getString(4);
                    bookNameBean.bookSize = cursor.getString(5);
                    filePathList.add(bookNameBean);
                }
                db.close();
                eSqLite.close();
                toCloseDialog();
                listAdapter.notifyDataSetChanged();
            }
        });
        rl_dialog_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCloseDialog();
            }
        });
    }
}
