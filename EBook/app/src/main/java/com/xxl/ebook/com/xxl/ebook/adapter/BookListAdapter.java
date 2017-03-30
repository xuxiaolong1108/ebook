package com.xxl.ebook.com.xxl.ebook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xxl.ebook.R;
import com.xxl.ebook.com.xxl.ebook.pojo.TitleBean;

import java.util.List;

/**
 * Created by xuxiaolong on 2017/3/27.
 */

public class BookListAdapter extends BaseAdapter {
    private Context context;
    private List<TitleBean> list;

    private BookListAdapter(){

    }
    public BookListAdapter(Context context ,List list){
        this.context =context;
        this.list =list;
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
        View view1 =View.inflate(context, R.layout.book_list_item_layout,null);
        TextView  tv_list_item = ((TextView) view1.findViewById(R.id.tv_list_item));
        tv_list_item.setText(list.get(i).titleName);
        return view1;
    }
}
