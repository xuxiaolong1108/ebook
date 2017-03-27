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
        List<String> list = new ArrayList<String>();

        String  aa ="dad,dadad,dadad,dadasd,dads,aa";
        String[] aas= aa.split(",");
        ELog.i(TAG,aas.length+""+"长度");
        String[] lists = bookContent.split("\n|\r|\r\n");

        //Pattern p = Pattern.compile("(^\\s*第)(.{1,9})[章节卷集部篇回](\\s*)(.*)(\n|\r|\r\n)");
        Pattern p = Pattern.compile("(^\\s*第)(.{1,9})[章节卷集部篇回](\\s*)(.*)(\n|\r|\r\n)");
        Matcher matcher =null;

        for(int i=0;i<lists.length;i++){
            ELog.i(TAG,lists.length+"");
            ELog.i(TAG ,lists[i]+"");
            matcher= p.matcher(lists[i]);
            while (matcher.find()){
                ELog.i(TAG,matcher.group()+""+"matcher.group()");
                list.add(matcher.group());
            }
        }
        return list;
    }
}
