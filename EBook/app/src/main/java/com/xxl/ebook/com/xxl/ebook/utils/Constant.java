package com.xxl.ebook.com.xxl.ebook.utils;

/**
 * Created by xuxiaolong on 2017/3/9.
 */

public class Constant {
    //http://ycpackages.haowanyou.com/ycsdk/yclib/YCSDK.zip
    //public static final String url ="http://10.132.47.172:8080/ECity/";
    //public static final String url ="http://127.0.0.1:8080/ECity/";
    public static final String url ="http://10.0.2.2:8080/ECity/";
    public static final String lianjie =url+"getallbooksbycate?cateid=1";
    public static final String rankListUrl  = url +"getrankinglist";
    public static final String imgUrl =url;
    public static final String downloadurl = url+"getdownloadbooks"+"?bookid=";
    public static final String bookurl = url ;
    //http://192.168.1.100:8080/ECity/getallbooksbycate?cateid=1

    public static final String booklisturl = url+"getallbooksbycate?cateid=";
    //http://192.168.1.100:8080/ECity/getsearch?content=5
    public static final String searchurl =url+"getsearch";




    public static final int BOOKSIZE = 500;
}
