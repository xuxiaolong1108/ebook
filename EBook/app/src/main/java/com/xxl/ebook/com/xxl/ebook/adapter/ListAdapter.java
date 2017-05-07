package com.xxl.ebook.com.xxl.ebook.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxl.ebook.R;
import com.xxl.ebook.com.xxl.ebook.pojo.BookNameBean;
import com.xxl.ebook.com.xxl.ebook.utils.ELog;

import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * Created by xuxiaolong on 2017/3/17.
 */

public class ListAdapter extends BaseAdapter {
    private static final String TAG = ListAdapter.class.getSimpleName();
    private Context context;
    private List<BookNameBean> list;

    private ListAdapter(){

    }
    public ListAdapter(Context context,List list){
        this.context =context;
        this.list =list;
        ELog.i(TAG,"长度"+list.size());
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 =View.inflate(context, R.layout.bookshelf_list_item_layout,null);
        ImageView iv_item_pic = (ImageView) view1.findViewById(R.id.iv_item_pic);
        TextView tv_item_title =  (TextView) view1.findViewById(R.id.tv_item_title);
        File file = new File(list.get(i).bookPic);
        ELog.i(TAG,"图片"+list.get(i).bookPic);
        if(file.exists()){
            Bitmap bm = BitmapFactory.decodeFile(list.get(i).bookPic);
            iv_item_pic.setImageBitmap(bm);
            ELog.i(TAG,"书籍有图片"+list.get(i).bookPic);
        }else{
            iv_item_pic.setImageResource(R.drawable.test);
            ELog.i(TAG,"书籍无图片");

        }
        tv_item_title.setText(list.get(i).bookName+"");
        return view1;
    }
}
