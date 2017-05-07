package com.xxl.ebook.com.xxl.ebook.common;

import android.os.Environment;

import com.xxl.ebook.com.xxl.ebook.pojo.TitleBean;
import com.xxl.ebook.com.xxl.ebook.utils.ELog;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuxiaolong on 2017/3/27.
 */

public class BookList {
    private static final String TAG = BookList.class.getSimpleName();

    public List getBookList(String bookPath) {
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            //br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath() + "/wmsj.txt"));
            br = new BufferedReader(new FileReader(bookPath));
            sb = new StringBuffer();
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String bookContent = sb.toString();
        ELog.i("长度", bookContent.length() + "");
        List<TitleBean> list = new ArrayList<TitleBean>();

        //Pattern p = Pattern.compile("(^\\s*第)(.{1,9})[章节卷集部篇回](\\s*)(.*)(\n|\r|\r\n)");
        String rex = "[第](.{1,9})[章节卷集篇回](.{1,9})";
        Pattern p = Pattern.compile(rex);
        Matcher matcher = null;
        matcher = p.matcher(bookContent);
        TitleBean titleBean =null;
        while (matcher.find()) {
            titleBean =new TitleBean();
            titleBean.titleName =matcher.group();
            titleBean.titlePosition=matcher.start();
            ELog.i(TAG, matcher.group() +matcher.start() );

            list.add(titleBean);
        }

        ELog.i("第字数", list.size() + "");

        String rex2 = "[\\n]";
        Pattern p2 = Pattern.compile(rex2);
        Matcher matcher2 = null;
        matcher2 = p2.matcher(bookContent);
        ELog.i("换行1","换行1");
        while(matcher2.find()){
            ELog.i("换行","换行");
        }
        return list;
    }
}
