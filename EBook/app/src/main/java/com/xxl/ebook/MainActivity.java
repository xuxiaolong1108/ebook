package com.xxl.ebook;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.xxl.ebook.com.xxl.ebook.fragment.BookFragment;
import com.xxl.ebook.com.xxl.ebook.fragment.ShelfFragment;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rg_main;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initViews();
        initEvent();
    }

    private void init() {
        rg_main = ((RadioGroup) findViewById(R.id.rg_mian));
    }

    private void initViews() {
        fragmentManager =getFragmentManager();
        showFragment(new ShelfFragment());
    }

    private void initEvent() {
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i){
                        case R.id.rb_main_left:
                            showFragment(new ShelfFragment());
                            break;
                        case R.id.rb_main_right:
                            showFragment(new BookFragment());
                            break;
                        default:
                            break;

                    }
            }
        });
    }

    public void showFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_main,fragment).commit();
    }
}
