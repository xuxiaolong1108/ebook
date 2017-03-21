package com.xxl.ebook;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import com.xxl.ebook.com.xxl.ebook.fragment.BookFragment;
import com.xxl.ebook.com.xxl.ebook.fragment.ShelfFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rg_main;
    FragmentManager fragmentManager;
    private RadioButton rb_main_left;
    private DrawerLayout id_main;
    private ImageView iv_main_cehua;
    private ListView lv_main_cehua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        ininData();
        initEvent();
    }


    private void initViews() {
        rg_main = ((RadioGroup) findViewById(R.id.rg_mian));
        rb_main_left = ((RadioButton) findViewById(R.id.rb_main_left));
        id_main = ((DrawerLayout) findViewById(R.id.id_main));
        iv_main_cehua = ((ImageView) findViewById(R.id.iv_main_cehua));
        lv_main_cehua = ((ListView) findViewById(R.id.lv_main_cehua));
    }

    private void ininData() {
        rb_main_left.setChecked(true);
        fragmentManager = getFragmentManager();
        showFragment(new ShelfFragment());
        String[] titles ={"我的","设置"};
        int[] titleImg = {R.drawable.home,R.drawable.settings};
        List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
        for(int i=0;i<titles.length;i++){
            Map<String,Object> map =new HashMap<String,Object>();
            map.put("title",titles[i]);
            map.put("titleImg",titleImg[i]);
            list.add(map);
        }
        SimpleAdapter simpleAdapter =new SimpleAdapter(getApplicationContext(),list,R.layout.cehua_item_layout,new String[] {"titleImg","title"},new int[]{R.id.iv_cehua_pic,R.id.tv_cehua_title});
        lv_main_cehua.setAdapter(simpleAdapter);
    }


    private void initEvent() {
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
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

        iv_main_cehua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_main.openDrawer(Gravity.LEFT);
            }
        });
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_main, fragment).commit();
    }
}
