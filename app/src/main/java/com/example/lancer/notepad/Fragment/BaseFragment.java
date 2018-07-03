package com.example.lancer.notepad.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lancer.notepad.R;





public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(initLayout(), container, false);
        initView(rootView);
        initTitle();
        initData();
        return rootView;
    }

    public abstract void initData();

    public abstract void initTitle();

    public abstract void initView(View view);

    public abstract int initLayout();
}
