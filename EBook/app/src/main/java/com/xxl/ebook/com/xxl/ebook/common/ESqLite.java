package com.xxl.ebook.com.xxl.ebook.common;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xxl.ebook.com.xxl.ebook.utils.ELog;

/**
 * Created by xuxiaolong on 2017/3/23.
 */

public class ESqLite extends SQLiteOpenHelper {
    private static final String TAG = ESqLite.class.getSimpleName();
    private static final int VERSION = 1;
    private static final String NAME = "bookshelf";

    public ESqLite(Context context) {
        super(context, NAME, null, VERSION);
    }

    private ESqLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql =
                "create table if not exists " + NAME + "  (id INTEGER PRIMARY KEY autoincrement,bookname varchar,bookauthor varchar,bookaddress varchar,imgaddress varchar,booksize varchar)";
        sqLiteDatabase.execSQL(sql);
        ELog.i(TAG,"数据库创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        ELog.i(TAG,"数据库更新");
    }
}
