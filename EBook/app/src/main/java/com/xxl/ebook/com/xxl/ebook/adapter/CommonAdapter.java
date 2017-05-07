package com.xxl.ebook.com.xxl.ebook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by xuxiaolong on 2016/10/9.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    Context context;

    List<T> lists;
    int layoutId;

    public CommonAdapter(Context context, List<T> lists, int layoutId) {


        this.context = context;


        this.lists = lists;
        this.layoutId = layoutId;
    }


    @Override
    public int getCount() {
        return (lists != null) ? lists.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);//获取每一个Item的数据源
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.get(context, layoutId, convertView, parent);


        convert(viewHolder, lists.get(position), position);

        return viewHolder.getConvertView();
    }

    //取出控价 然后赋值

    public abstract void convert(ViewHolder viewHolder, T t, int position);
}
