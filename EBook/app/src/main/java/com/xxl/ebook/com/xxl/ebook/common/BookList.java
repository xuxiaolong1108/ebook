package com.xxl.ebook.com.xxl.ebook.common;

import android.os.Environment;

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

public class BookList  {
    private static final String TAG =BookList.class.getSimpleName();
    public List getBookList() {
        BufferedReader br=null;
        StringBuffer sb =null;
        try {
             br =new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath()+"/sanguo.txt"));
             sb=new StringBuffer();
            String s ;
            while ((s = br.readLine()) != null)
            {
                sb.append(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String bookContent = sb.toString();
        ELog.i("长度",bookContent.length()+"");
        List<String> list = new ArrayList<String>();

        //Pattern p = Pattern.compile("(^\\s*第)(.{1,9})[章节卷集部篇回](\\s*)(.*)(\n|\r|\r\n)");
        String rex = "[第](.{1,9})[章节卷集篇回](.{1,9})";
        Pattern p = Pattern.compile(rex);
        Matcher matcher =null;
            matcher= p.matcher(bookContent);
            while (matcher.find()){
                ELog.i(TAG,matcher.group()+""+"matcher.group()");
                list.add(matcher.group());
            }

        ELog.i("第字数",list.size()+"");

        return list;
    }
}
