package com.xxl.ebook.com.xxl.ebook.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xxl.ebook.R;
import com.xxl.ebook.com.xxl.ebook.activity.BookNameListActivity;
import com.xxl.ebook.com.xxl.ebook.activity.ListActivity;
import com.xxl.ebook.com.xxl.ebook.activity.SearchActivity;
import com.xxl.ebook.com.xxl.ebook.adapter.CommonAdapter;
import com.xxl.ebook.com.xxl.ebook.adapter.ViewHolder;
import com.xxl.ebook.com.xxl.ebook.common.ESqLite;
import com.xxl.ebook.com.xxl.ebook.pojo.BookListBean;
import com.xxl.ebook.com.xxl.ebook.pojo.BooksBean;
import com.xxl.ebook.com.xxl.ebook.utils.Constant;
import com.xxl.ebook.com.xxl.ebook.utils.ELog;
import com.xxl.ebook.com.xxl.ebook.utils.EUtils;
import com.xxl.ebook.com.xxl.ebook.weight.NoScrollListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuxiaolong on 2017/3/7.
 */

public class BookFragment extends Fragment {
    private static final String TAG = BookFragment.class.getSimpleName();
    Dialog dialog =null;
    String bookId ="";
     String bookName ="";
    String bookAuthor ="";
    String bookCate ="";
    String bookAddress="";
     String picAddress ="";
    String bookPath  ="";
    String picPath ="";

    View view = null;
    private Button btn_city_xuanhuan;
    private Button btn_city_chuanyue;
    private List<BooksBean> rankList = new ArrayList<BooksBean>();
    private NoScrollListView lv_city_paihang;
    private CommonAdapter<BooksBean> commonAdapter;
    //Environment.getExternalStorageDirectory()
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(0==msg.what){
                //下载完成之后存入sqlite
                final ProgressDialog progressDialog =new ProgressDialog(getActivity());
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
                final ProgressDialog picProgress =new ProgressDialog(getActivity());
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
                        Toast.makeText(getActivity(),"下载完成",Toast.LENGTH_LONG).show();
                        //下载完成插入sqlite
                        ESqLite eSqLite = new ESqLite(getActivity());
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
    private Button btn_city_yanqing;
    private Button btn_city_wuxia;
    private Button btn_city_dushi;
    private Button btn_city_yijie;
    private Button btn_city_xiaoyuan;
    private Button btn_city_rexue;
    private EditText et_city_search;
    private Button btn_search;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_bookcity, null, false);
        initView();
        initData();
        initEvent();
        return view;
    }


    private void initView() {
        btn_city_xuanhuan = ((Button) view.findViewById(R.id.btn_city_xuanhuan));
        btn_city_chuanyue = ((Button) view.findViewById(R.id.btn_city_chuanyue));
        btn_city_yanqing = ((Button) view.findViewById(R.id.btn_city_yanqing));
        btn_city_wuxia = ((Button) view.findViewById(R.id.btn_city_wuxia));
        btn_city_dushi = ((Button) view.findViewById(R.id.btn_city_dushi));
        btn_city_yijie = ((Button) view.findViewById(R.id.btn_city_yijie));
        btn_city_xiaoyuan = ((Button) view.findViewById(R.id.btn_city_xiaoyuan));
        btn_city_rexue = ((Button) view.findViewById(R.id.btn_city_rexue));


        lv_city_paihang = ((NoScrollListView) view.findViewById(R.id.lv_city_paihang));


        et_city_search = ((EditText) view.findViewById(R.id.et_city_search));
        btn_search = ((Button) view.findViewById(R.id.btn_search));
    }

    private void initData() {
        commonAdapter = new CommonAdapter<BooksBean>(getActivity(), rankList, R.layout.ranklist_item_layout) {
            @Override
            public void convert(ViewHolder viewHolder, BooksBean booksBean, int position) {
                TextView tv_rank_list = viewHolder.getViewById(R.id.tv_rank_list);
                ImageView iv_rank_img = viewHolder.getViewById(R.id.iv_rank_img);
                TextView tv_rank_name = viewHolder.getViewById(R.id.tv_rank_name);
                TextView tv_rank_author = viewHolder.getViewById(R.id.tv_rank_author);
                if(0==position){
                    tv_rank_list.setTextColor(Color.RED);
                }
                if(1==position){
                    tv_rank_list.setTextColor(Color.BLUE);
                }
                if(2==position){
                    tv_rank_list.setTextColor(Color.YELLOW);
                }
                tv_rank_list.setText((position+1)+"");
                x.image().bind(iv_rank_img,Constant.imgUrl+booksBean.picAddress);
                tv_rank_name.setText(booksBean.bookName);
                tv_rank_author.setText(booksBean.bookAuthor);

            }
        };

        getNetData();
    }

    private void initEvent() {
        lv_city_paihang.setAdapter(commonAdapter);

        lv_city_paihang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog(position);
            }
        });
        btn_city_xuanhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookNameListActivity.class);
                intent.putExtra("cateId","1");
                startActivity(intent);
            }
        });
        btn_city_chuanyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookNameListActivity.class);
                intent.putExtra("cateId","2");
                startActivity(intent);

            }
        });
        btn_city_yanqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookNameListActivity.class);
                intent.putExtra("cateId","3");
                startActivity(intent);

            }
        });
        btn_city_wuxia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookNameListActivity.class);
                intent.putExtra("cateId","4");
                startActivity(intent);

            }
        });
        btn_city_dushi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookNameListActivity.class);
                intent.putExtra("cateId","5");
                startActivity(intent);

            }
        });
        btn_city_yijie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookNameListActivity.class);
                intent.putExtra("cateId","6");
                startActivity(intent);

            }
        });
        btn_city_xiaoyuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookNameListActivity.class);
                intent.putExtra("cateId","7");
                startActivity(intent);

            }
        });
        btn_city_rexue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookNameListActivity.class);
                intent.putExtra("cateId","8");
                startActivity(intent);

            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content  = et_city_search.getText().toString();
                if(content.length()<=0){
                    Toast.makeText(getActivity(),"请输入内容",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent =new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra("content",content+"");
                    startActivity(intent);
                }

            }
        });

    }
    private void showDialog(final int bookid){
        dialog =new Dialog(getActivity(), R.style.dialogStyle);
        View view =View.inflate(getActivity(),R.layout.download_dialog_layout,null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        TextView tv_download_name= (TextView) view.findViewById(R.id.tv_download_name);
        TextView tv_download_download= (TextView) view.findViewById(R.id.tv_download_download);
        TextView tv_download_cancel= (TextView) view.findViewById(R.id.tv_download_cancel);
        tv_download_name.setText(rankList.get(bookid).bookName);
        tv_download_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colseDialog();
                EUtils.showProgressBar(getActivity());
                RequestParams requestParams =new RequestParams(Constant.downloadurl+rankList.get(bookid).bookId);
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

    private void getNetData() {
        EUtils.showProgressBar(getActivity());
        RequestParams requestParams = new RequestParams(Constant.rankListUrl);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                EUtils.closeProgressBar();
                rankList.clear();
                ELog.i(TAG, "onsuccess");
                Gson gson = new Gson();
                BookListBean bookListBean = gson.fromJson(result, BookListBean.class);
                rankList.addAll(bookListBean.list);
                commonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ELog.i(TAG, "onError" + ex.toString());

            }

            @Override
            public void onCancelled(CancelledException cex) {
                ELog.i(TAG, "onCancelled" + cex.toString());

            }

            @Override
            public void onFinished() {
                ELog.i(TAG, "onFinished");

            }
        });

    }


}
