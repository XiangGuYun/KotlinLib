package com.kotlin_base.case3_dynamic_add_remove;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kotlin_base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2018/1/3.
 */
/*
如何在ViewPager中动态添加或删除Fragment
1.使用自定义的FragmentStatePagerAdapter
2.getItemPosition重写getItemPosition方法并返回PagerAdapter.POSITION_NONE
3.添加或删除数据源，再调用notifyDataSetChanged方法
 */
public class Case3Activity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<MyFragment> list = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case3);
        viewPager = findViewById(R.id.viewpager);

        for (int i = 0; i < 5; i++) {
            list.add(new MyFragment("Fragment"+(i+1)));
        }

        myAdapter = new MyAdapter(getSupportFragmentManager(), list);

        viewPager.setAdapter(myAdapter);
    }

    public void add(View view) {
        list.add(new MyFragment("新的Fragment"));
        myAdapter.notifyDataSetChanged();
    }

    public void remove(View view) {

    }
}
