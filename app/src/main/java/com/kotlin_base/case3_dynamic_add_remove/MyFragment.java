package com.kotlin_base.case3_dynamic_add_remove;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kotlin_base.R;

/**
 * Created by asus on 2018/1/3.
 */

@SuppressLint("ValidFragment")
public class MyFragment extends Fragment {

    private String text;

    public MyFragment(String text){
        this.text=text;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView view = (TextView) inflater.inflate(R.layout.frgament_my, container, false);
       view.setText(text);
        return view;
    }
}
