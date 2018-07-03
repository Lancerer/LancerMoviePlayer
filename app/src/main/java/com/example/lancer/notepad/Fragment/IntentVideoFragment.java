package com.example.lancer.notepad.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lancer.notepad.R;




public class IntentVideoFragment extends BaseFragment {

    private ImageView topBack;
    private TextView topTitle;
    private ImageView topSetting;

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        topTitle.setText(R.string.intent_video);
    }

    @Override
    public void initView(View view) {

        topBack = view.findViewById(R.id.top_back);
        topTitle =view. findViewById(R.id.top_title);
        topSetting = view.findViewById(R.id.top_setting);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_my;
    }
}
