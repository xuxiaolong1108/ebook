package com.xxl.ebook.com.xxl.ebook.common;

import com.xxl.ebook.com.xxl.ebook.utils.Constant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2017/4/23.
 */

public enum BookProduct {
    INSTANCE;
    private int pages;//总页数
    private final int SIZE = Constant.BOOKSIZE;//每一页的字节数 字节数固定
    private long bytescount;//字节总数
    private long currentpage;//当前页面
    private RandomAccessFile readFile;
    private File file;
    private String content;
    BufferedReader br = null;
    StringBuffer sb = null;
    private BookProduct(){

    }
    public void  init(File file){
        this.file =file;
        try {
            this.file =file;
            readFile = new RandomAccessFile(file, "r");
            bytescount = readFile.length();//获得字节总数
            pages = (int) bytescount / SIZE;//计算得出文本的页数
            file2String();
            content =new String(sb.toString().getBytes(),"UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public int getBookTotalPages(){
        return pages;
    }
    public String getBookContent(long current){
        //seek(current);
        //String text = read();
        //return text.trim().substring(2, text.length() - 2);
        return content.substring((int)current,(int)current+Constant.BOOKSIZE);
    }
    //定位字节位置 根据页数定位文本的位置
    private void seek(long current) {
        try {
            readFile.seek(current);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String read() {
        //内容重叠防止 末尾字节乱码
        byte[] chs = new byte[SIZE + 8];
        try {

            readFile.read(chs);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(chs, Charset.forName("utf-8"));
    }
    private void file2String(){

        try {
            //br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath() + "/wmsj.txt"));
            br = new BufferedReader(new FileReader(file));
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
    }
}
