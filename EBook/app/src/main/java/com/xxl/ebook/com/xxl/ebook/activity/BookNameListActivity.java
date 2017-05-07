package com.xxl.ebook.com.xxl.ebook.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xxl.ebook.R;
import com.xxl.ebook.com.xxl.ebook.adapter.CommonAdapter;
import com.xxl.ebook.com.xxl.ebook.adapter.ViewHolder;
import com.xxl.ebook.com.xxl.ebook.common.ESqLite;
import com.xxl.ebook.com.xxl.ebook.pojo.BookListBean;
import com.xxl.ebook.com.xxl.ebook.pojo.BooksBean;
import com.xxl.ebook.com.xxl.ebook.utils.Constant;
import com.xxl.ebook.com.xxl.ebook.utils.ELog;
import com.xxl.ebook.com.xxl.ebook.utils.EUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BookNameListActivity extends AppCompatActivity {
    private static final String  TAG =  BookNameListActivity.class.getSimpleName();
    private TextView tv_booklist_close;
    private ListView lv_booklist_list;
    CommonAdapter<BooksBean>  commonAdapter;
    List<BooksBean> list =new ArrayList<BooksBean>();
    private String cateId ="1";
    Dialog dialog =null;



    String bookId ="";
    String bookName ="";
    String bookAuthor ="";
    String bookCate ="";
    String bookAddress="";
    String picAddress ="";
    String bookPath  ="";
    String picPath ="";




    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(0==msg.what){
                //下载完成之后存入sqlite
                final ProgressDialog progressDialog =new ProgressDialog(BookNameListActivity.this);
                String str = (String)msg.obj;
                String[] strs =str.split("`");
                bookId = strs[0];
                bookName =strs[1];
                bookAuthor=strs[2];
                bookCate =strs[3];
                bookAddress = strs[4];
                picAddress = strs[5];
                String booksAddressSdCard =bookAddress.substring(7);
                ELog.i(TAG,booksAddressSdCard);
                RequestParams requestParams = new RequestParams(Constant.bookurl+bookAddress);
                requestParams.setAutoRename(true);//断点下载
                bookPath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+booksAddressSdCard;
                requestParams.setSaveFilePath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+booksAddressSdCard);
                x.http().get(requestParams, new org.xutils.common.Callback.ProgressCallback<File>() {
                    @Override
                    public void onWaiting() {

                    }

                    @Override
                    public void onStarted() {
                        if(null!=progressDialog) {
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置为水平进行条
                            progressDialog.setMessage(bookName+" 正在下载中...");
                            progressDialog.setProgress(0);
                            progressDialog.show();
                        }


                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {
                        if(null!=progressDialog){
                            progressDialog.setMax((int)total);
                            progressDialog.setProgress((int)current);

                        }


                    }

                    @Override
                    public void onSuccess(File result) {

                        if(null != progressDialog){
                            progressDialog.dismiss();
                        }
                        //书籍下载完成之后下载图片
                        Message picMsg=new Message();
                        picMsg.what =1;
                        picMsg.obj = (Object) picAddress;
                        handler.sendMessage(picMsg);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        if(null != progressDialog){
                            progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
            if(1== msg.what){
                final ProgressDialog picProgress =new ProgressDialog(BookNameListActivity.this);
                String pic = ((String)msg.obj).substring(6);
                RequestParams requestParams = new RequestParams(Constant.imgUrl+(String)msg.obj);
                requestParams.setAutoRename(true);//断点下载
                picPath =Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+pic;
                requestParams.setSaveFilePath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+pic);
                x.http().get(requestParams, new org.xutils.common.Callback.ProgressCallback<File>() {
                    @Override
                    public void onWaiting() {

                    }

                    @Override
                    public void onStarted() {
                        if(null!=picProgress) {
                            picProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置为水平进行条
                            picProgress.setMessage("书籍封面 正在下载中...");
                            picProgress.setProgress(0);
                            picProgress.show();
                        }

                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {
                        if(null!=picProgress){
                            picProgress.setMax((int)total);
                            picProgress.setProgress((int)current);
                        }

                    }

                    @Override
                    public void onSuccess(File result) {
                        if(null != picProgress){
                            picProgress.dismiss();
                        }
                        Toast.makeText(BookNameListActivity.this,"下载完成",Toast.LENGTH_LONG).show();
                        //下载完成插入sqlite
                        ESqLite eSqLite = new ESqLite(BookNameListActivity.this);
                        SQLiteDatabase db = eSqLite.getWritableDatabase();
                        String sql = "insert into bookshelf (bookname,bookauthor,bookaddress,imgaddress,booksize) " + "values("
                                + "'" + bookName + "'" + ","
                                + "'" + bookAuthor + "'" + ","
                                + "'" + bookPath + "'" + ","
                                + "'" + picPath + "'" + ","
                                + "'" + "" + "'" + ")";
                        db.execSQL(sql);
                        db.close();

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        if(null != picProgress){
                            picProgress.dismiss();
                        }

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_name_list);
        Intent intent =getIntent();
        cateId = intent.getStringExtra("cateId");
        initView();
        initData();
        initEvent();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EUtils.showProgressBar(BookNameListActivity.this);
        RequestParams requestParams = new RequestParams(Constant.booklisturl+cateId);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson =new Gson();
                list.clear();
                BookListBean bookListBean =  gson.fromJson(result,BookListBean.class);
                list.addAll(bookListBean.list);
                commonAdapter.notifyDataSetChanged();
                EUtils.closeProgressBar();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void initView() {
        tv_booklist_close = ((TextView) findViewById(R.id.tv_booklist_close));
        lv_booklist_list = ((ListView) findViewById(R.id.lv_booklist_list));

    }

    private void initData() {
        commonAdapter =new CommonAdapter<BooksBean>(BookNameListActivity.this,list,R.layout.ranklist_item_layout) {
            @Override
            public void convert(ViewHolder viewHolder, BooksBean booksBean, int position) {
               TextView tv_rank_list  =(TextView)viewHolder.getViewById(R.id.tv_rank_list);
               ImageView iv_rank_img =  (ImageView)viewHolder.getViewById(R.id.iv_rank_img);
                TextView tv_rank_name = (TextView)viewHolder.getViewById(R.id.tv_rank_name);
               TextView tv_rank_author =  (TextView)viewHolder.getViewById(R.id.tv_rank_author);
                tv_rank_list.setText((position+1)+"");
                x.image().bind(iv_rank_img,Constant.imgUrl+booksBean.picAddress);
                tv_rank_name.setText(booksBean.bookName+"");
                tv_rank_author.setText(booksBean.bookAuthor+"");
                tv_rank_list.setTextColor(Color.BLACK);
                tv_rank_name.setTextColor(Color.BLACK);
                tv_rank_author.setTextColor(Color.BLACK);

            }
        };
        lv_booklist_list.setAdapter(commonAdapter);
    }

    private void initEvent() {
        tv_booklist_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_booklist_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _showDialog(position);
            }
        });

    }

    private void _showDialog(final int bookid){
        dialog =new Dialog(BookNameListActivity.this, R.style.dialogStyle);
        View view =View.inflate(BookNameListActivity.this,R.layout.download_dialog_layout,null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        TextView tv_download_name= (TextView) view.findViewById(R.id.tv_download_name);
        TextView tv_download_download= (TextView) view.findViewById(R.id.tv_download_download);
        TextView tv_download_cancel= (TextView) view.findViewById(R.id.tv_download_cancel);
        tv_download_name.setText(list.get(bookid).bookName);
        tv_download_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colseDialog();
                EUtils.showProgressBar(BookNameListActivity.this);
                RequestParams requestParams =new RequestParams(Constant.downloadurl+list.get(bookid).bookId);
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        EUtils.closeProgressBar();
                        Gson gson =new Gson();
                        BooksBean booksBean = gson.fromJson(result,BooksBean.class);
                        Message msg =new Message();
                        msg.what =0 ;
                        msg.obj = (Object) (""+booksBean.bookId+"`"
                                +booksBean.bookName+"`"
                                +booksBean.bookAuthor+"`"
                                +booksBean.bookCate+"`"
                                +booksBean.bookAddress+"`"
                                +booksBean.picAddress);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ELog.i(TAG,"onError "+ex.toString());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
        tv_download_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colseDialog();
            }
        });

        dialog.show();

    }
    private void colseDialog(){
        if(null!=dialog){
            dialog.dismiss();
        }

    }
}
