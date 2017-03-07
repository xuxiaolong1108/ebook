package com.xxl.ebook.com.xxl.ebook.activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xxl.ebook.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

public class ReadActivity extends AppCompatActivity {
    private static final String TAG = ReadActivity.class.getSimpleName();
    String sdCardPath = Environment.getExternalStorageDirectory().getPath();
    private RandomAccessFile readFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        File file = new File(sdCardPath + "//" + "long.txt");
        try {
            readFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /*try {
            while (readFile.readLine()!=null){
                Log.i(TAG,readFile.readLine()+"");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        byte[] bytes = new byte[100];

        try {
            for (int i = 0; i <bytes.length; i++)
                bytes[i] = readFile.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String stringValue = new String(bytes, Charset.forName("utf-8"));
        try {
            Log.i(TAG, readFile.length()+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, sdCardPath);
        Log.i(TAG, stringValue);


    }
}
