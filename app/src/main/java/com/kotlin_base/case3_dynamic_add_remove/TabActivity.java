package com.kotlin_base.case3_dynamic_add_remove;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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
绑定TabLayout的ViewPager如何动态增删Fragment？
 */
public class TabActivity extends AppCompatActivity{

    private ViewPager viewPager;
    private List<MyFragment> list = new ArrayList<>();
    private MyAdapter myAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        viewPager = findViewById(R.id.viewpager);
        tabLayout =findViewById(R.id.tablayout);

        for (int i = 0; i < 5; i++) {
            list.add(new MyFragment("Fragment"+(i+1)));
        }
        myAdapter = new MyAdapter(getSupportFragmentManager(), list);

        viewPager.setAdapter(myAdapter);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);//设置滑动Tab模式
        for (MyFragment frag : list) {//添加Tab选项卡
            tabLayout.addTab(tabLayout.newTab());
        }
        //将TabLayout和ViewPager关联起来，并设置自动刷新
        tabLayout.setupWithViewPager(viewPager, true);
        //Tab属性必须在关联ViewPager之后设置
        for (int i = 0; i < list.size(); i++) {
            tabLayout.getTabAt(i).setText("Fragment"+(i+1));//设置Tab文本
        }

    }

    public void add(View view) {
        list.add(new MyFragment("新的Fragment"));
        myAdapter.notifyDataSetChanged();
        //在ViewPager适配器刷新后tablayout必须重新设置tab信息
        for (int i = 0; i < list.size(); i++) {
            tabLayout.getTabAt(i).setText("Fragment"+(i+1));
        }
    }

    public void remove(View view) {
        list.remove(0);
        myAdapter.notifyDataSetChanged();
        //在ViewPager适配器刷新后tablayout必须重新设置tab信息
        for (int i = 0; i < list.size(); i++) {
            tabLayout.getTabAt(i).setText("Fragment"+(i+1));
        }
    }
}
