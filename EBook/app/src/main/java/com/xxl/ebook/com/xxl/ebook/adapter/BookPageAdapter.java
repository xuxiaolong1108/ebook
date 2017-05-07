package com.xxl.ebook.com.xxl.ebook.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xxl.ebook.R;
import com.xxl.ebook.com.xxl.ebook.common.BookFactory;
import com.xxl.ebook.com.xxl.ebook.common.BookProduct;
import com.xxl.ebook.com.xxl.ebook.utils.BookPostion;
import com.xxl.ebook.com.xxl.ebook.utils.Constant;

import java.io.File;

/**
 * Created by xuxiaolong on 2017/3/8.
 */

public class BookPageAdapter extends PagerAdapter {
    private static final String TAG = BookPageAdapter.class.getSimpleName();
    private File file;
    private int totalPages;
    private Context context;


    private BookPageAdapter() {
    }

    public BookPageAdapter(File file, Context context) {
        this.context = context;
        this.file = file;
        initBook();
    }

    private void initBook() {
        BookProduct.INSTANCE.init(file);

        this.totalPages = BookProduct.INSTANCE.getBookTotalPages();
    }

    @Override
    public int getCount() {
        return totalPages;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BookPostion.currentPosition = position-1;
        View view = View.inflate(context, R.layout.viewpager_book, null);
        String str = BookProduct.INSTANCE.getBookContent((Constant.BOOKSIZE)*position);
        TextView tv_viewPager = ((TextView) view.findViewById(R.id.tv_viewPager));
        tv_viewPager.setText(str);
        container.addView(view);
        return view;
    }
}
